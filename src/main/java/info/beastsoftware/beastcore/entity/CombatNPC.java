package info.beastsoftware.beastcore.entity;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.event.combat.CombatPussyMustDieEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CombatNPC implements ICombatNPC {


    private final String nameFormat;
    private final UUID playerUUID;
    private final Location location;
    private final List<ItemStack> contents;
    private int timeLeftToDie;
    private boolean frozen;
    private Entity npc;

    public CombatNPC(String nameFormat, UUID playerUUID, Location location, List<ItemStack> contents, int timeLeftToDie) {
        this.nameFormat = nameFormat;
        this.playerUUID = playerUUID;
        this.location = location;
        this.contents = contents;
        this.timeLeftToDie = timeLeftToDie;
        this.spawn();
    }


    @Override
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public List<ItemStack> getInventoryContents() {
        return contents;
    }

    @Override
    public int getTimeLeftToDie() {
        return timeLeftToDie;
    }

    @Override
    public void decreaseTimeToDie() {

        if(isTimeFrozen()) return;

        if(this.timeLeftToDie > 0) {
            this.timeLeftToDie--;
            this.updateName();
        }

        else{
            Bukkit.getScheduler().runTask(BeastCore.getInstance(),() -> {
                Bukkit.getPluginManager().callEvent(new CombatPussyMustDieEvent(this));
            });
        }
    }

    @Override
    public void spawn() {
        this.npc = Objects.requireNonNull(getLocation().getWorld()).spawnEntity(location, EntityType.VILLAGER);
        LivingEntity livingEntity = (LivingEntity) this.npc;

        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SLOW, 999, 999);
        livingEntity.addPotionEffect(potionEffect);
        this.updateName();
    }

    @Override
    public void despawn() {
        this.npc.remove();
    }

    @Override
    public void updateName() {
        Bukkit.getScheduler().runTask(BeastCore.getInstance(),() -> {
            String name = StrUtils.replacePlaceholder(nameFormat, "{player}", getOfflinePlayer().getName());
            name = StrUtils.replacePlaceholder(name, "{time}", String.valueOf(timeLeftToDie));
            name = StrUtils.translate(name);

            npc.setCustomName(name);
            npc.setCustomNameVisible(true);
        });
    }

    @Override
    public void freezeTime() {
        this.frozen = true;
    }

    @Override
    public boolean isTimeFrozen() {
        return this.frozen;
    }

    @Override
    public Entity getNPC() {
        return this.npc;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CombatNPC combatNPC = (CombatNPC) o;
        return Objects.equals(playerUUID, combatNPC.playerUUID) &&
                Objects.equals(location, combatNPC.location) &&
                Objects.equals(contents, combatNPC.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerUUID, location, contents);
    }
}
