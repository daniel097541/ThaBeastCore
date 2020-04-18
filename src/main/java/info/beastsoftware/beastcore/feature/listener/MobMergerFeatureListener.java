package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.entity.StackedMobDiedResponse;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.service.StackedMobsService;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class MobMergerFeatureListener extends AbstractFeatureListener {

    private final String pasiveMeta = "Mob-Merger.pasive";
    private final String MOBSHEALTHPATH = "Mobs-Health.";

    private final StackedMobsService service;


    public MobMergerFeatureListener(IConfig config) {
        super(config, FeatureType.MOB_MERGER);
        this.service = new StackedMobsService(config);
    }

    public StackedMobsService getService() {
        return service;
    }

    private boolean checkIfStackeable(LivingEntity entity) {
        return config.getConfig().getBoolean("Mob-Merger.whitelist-mode") && config.getConfig().getStringList("Mob-Merger.whitelist").contains(entity.getType().name()) || config.getConfig().getBoolean("Mob-Merger.blacklist-mode") && !config.getConfig().getStringList("Mob-Merger.blacklist").contains(entity.getType().name());
    }

    private boolean blockPluginSpawn() {
        return config.getConfig().getBoolean("Mob-Merger.Block-Plugin-Spawned-Mobs-Being-Merged");
    }

    private int getCustomHealth(LivingEntity entity) {

        String type = entity.getType().name();
        Collection<String> sections;
        String path = MOBSHEALTHPATH + "per-mob-health";


        //error getting it
        try {
            sections = config.getConfig().getConfigurationSection(path).getKeys(false);
        } catch (NullPointerException ignored) {
            return -1;
        }


        //has not custom health
        if (!sections.contains(type)) {
            return -1;
        }

        //return value
        return config.getConfig().getInt(path + "." + type);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobHealthSpawn(CreatureSpawnEvent event) {

        if (event.isCancelled()) return;

        boolean enabled = config.getConfig().getBoolean(MOBSHEALTHPATH + "enabled");

        if (!enabled) return;

        LivingEntity entity = event.getEntity();

        int health = getCustomHealth(entity);

        if (health < 0)
            return;

        entity.setHealth(health);
    }

    public boolean spawnOnlySpawnerMobs() {
        return config.getConfig().getBoolean("Mob-Merger.Spawn-Only-Mobs-From-Spawners");
    }

    private boolean shouldBeStacked(CreatureSpawnEvent.SpawnReason spawnReason) {

        if (config.getConfig().getBoolean("Mob-Merger.Stack-Only-Spawner-Mobs")) {
            return spawnReason.equals(CreatureSpawnEvent.SpawnReason.SPAWNER);
        } else {
            return true;
        }

    }

    private boolean isWorldBlackListed(LivingEntity entity) {
        return config.getConfig().getStringList("Mob-Merger.world-blacklist") != null
                &&
                config.getConfig().getStringList("Mob-Merger.world-blacklist")
                        .contains(entity.getLocation().getWorld().getName());
    }

    private boolean blockEggSpawn(LivingEntity entity) {
        return config.getConfig().getBoolean("Mob-Merger.disable-egg-mobs-merge");
    }

    @EventHandler
    public void onPassiveSpawn(CreatureSpawnEvent event) {
        if (!config.getConfig().getBoolean("Passive-Mobs.enabled")) return;

        LivingEntity entity = event.getEntity();

        if (config.getConfig().getStringList("Passive-Mobs.list-of-pasive-mobs").contains(entity.getType().toString())) {
            entity.setMetadata(pasiveMeta, new FixedMetadataValue(BeastCore.getInstance(), "yes"));
            PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 9999, 999);
            entity.addPotionEffect(effect);
        }
    }

    @EventHandler
    public void onTargetAttack(EntityTargetLivingEntityEvent e) {
        if (!config.getConfig().getBoolean("Passive-Mobs.enabled")) return;
        Entity entity = e.getEntity();
        if (!isPassiveMob(entity))
            return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onVillagerTrade(PlayerInteractAtEntityEvent event) {

        if (!isOn()) return;

        Entity entity = event.getRightClicked();

        //not a villager
        if (!entity.getType().equals(EntityType.VILLAGER)) return;

        //not passive
        if (!isPassiveMob(entity)) return;

        //cancel trade
        event.setCancelled(true);

    }

    private boolean isPassiveMob(Entity entity) {
        return entity.hasMetadata(pasiveMeta) || config.getConfig().getStringList("Passive-Mobs.list-of-pasive-mobs").contains(entity.getType().toString());
    }

    private boolean hasMetadata(LivingEntity livingEntity) {
        return livingEntity.hasMetadata(info.beastsoftware.beastcore.entity.StackedMob.METADATA);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMobDeath(EntityDeathEvent event) {

        if (!this.isOn()) {
            return;
        }

        LivingEntity entity = event.getEntity();
        StackedMobDiedResponse response = this.service.killAndRetrieveDrops(entity, event.getDrops(), event.getDroppedExp());

        // set drops
        if (response.isStacked()) {
            Location location = entity.getLocation();
            for (ItemStack itemStack : response.getDrops()) {
                location.getWorld().dropItem(location, itemStack);
            }
            location.getWorld().spawn(location, ExperienceOrb.class).setExperience(response.getExp());
        }
    }

    private boolean canBeSpawned(CreatureSpawnEvent.SpawnReason spawnReason) {
        if (spawnOnlySpawnerMobs()) {
            return spawnReason.equals(CreatureSpawnEvent.SpawnReason.SPAWNER);
        }
        return true;
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {

        //turned off
        if (!this.isOn() || event.isCancelled()) {
            return;
        }

        LivingEntity entity = event.getEntity();

        //check if can spawn
        if (!this.canBeSpawned(event.getSpawnReason())) {
            event.setCancelled(true);
            return;
        }

        //stack only mobs from spawners
        if (!this.shouldBeStacked(event.getSpawnReason())) {
            return;
        }


        //armor stand ugh
        if (entity.getType().equals(EntityType.ARMOR_STAND)) return;

        //not stackable
        if (!checkIfStackeable(entity)) return;

        //world blacklisted
        if (isWorldBlackListed(entity)) return;

        //blocked cause of spawned as egg
        if (blockEggSpawn(entity) && event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER_EGG))
            return;

        //block mobs merging when spawned by plugins
        if (blockPluginSpawn() && event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM))
            return;


        //run 1 tick after to allow metadata being added
        Bukkit.getScheduler().runTaskLater(BeastCore.getInstance(), () -> {
            if (!this.hasMetadata(entity)) {
                boolean wasStacked = service.add(entity);
                //if was stacked, then we should remove the entity
                if (wasStacked) {
                    entity.remove();
                }
            }
        }, 2L);
    }
}
