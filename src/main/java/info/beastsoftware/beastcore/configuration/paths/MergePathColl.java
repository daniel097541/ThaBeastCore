package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class MergePathColl extends PathColl {
    public MergePathColl() {
        super();
    }

    @Override
    public void init() {

        addPath("enabled", true);


        List<String> entities = new ArrayList<>();
        entities.add(EntityType.VILLAGER.name());
        entities.add(EntityType.WOLF.name());
        List<String> damageSources = new ArrayList<>();
        List<String> worlds = new ArrayList<>();
        worlds.add("BLACKLISTED_WORLD_NAME");
        damageSources.add(EntityDamageEvent.DamageCause.FIRE.name());
        damageSources.add(EntityDamageEvent.DamageCause.FALL.name());
        addPath("Mob-Merger.world-blacklist", worlds);
        addPath("Mob-Merger.radius", 16);
        addPath("Mob-Merger.disable-egg-mobs-merge", false);
        addPath("Mob-Merger.custom-mob-name", "&e{number} &cX &d{mob_type}");
        addPath("Mob-Merger.whitelist-mode", false);
        addPath("Mob-Merger.blacklist-mode", true);
        addPath("Mob-Merger.whitelist", entities.toArray());
        addPath("Mob-Merger.blacklist", entities.toArray());
        addPath("Mob-Merger.max-stack-size", 20);


        addPath("Mob-Merger.Kill-Full-Stack.enabled", true);
        addPath("Mob-Merger.Kill-Full-Stack.multiply-drops", true);
        addPath("Mob-Merger.Kill-Full-Stack.damage-sources", damageSources.toArray());
        addPath("Mob-Merger.Block-Plugin-Spawned-Mobs-Being-Merged", true);
        addPath("Mob-Merger.Stack-Only-Spawner-Mobs", false);
        addPath("Mob-Merger.Spawn-Only-Mobs-From-Spawners", false);


        addPath("command.permission", "btf.killmerged.cmd");
        addPath("command.no-permission-msg", "&4(!) &cYou dont have permission!");
        addPath("command.killed-all-msg", "&dYou killed &7{number_killed} &dstacks of mobs!");


        addPath("Passive-Mobs.enabled", false);

        List<String> spawnmethods = new ArrayList<>();

        spawnmethods.add("SPAWNER_EGG");
        spawnmethods.add("SPAWNER");
        spawnmethods.add("NATURAL");

        addPath("Passive-Mobs.spawn-methods", spawnmethods);

        List<String> passiveMobs = new ArrayList<>();
        passiveMobs.add("BLAZE");
        passiveMobs.add("ZOMBIE");
        passiveMobs.add("SPIDER");
        passiveMobs.add("CREEPER");

        addPath("Passive-Mobs.list-of-pasive-mobs", passiveMobs);


        addPath("Mob-Merger.per-mob-max-stack-sizes.enabled", false);
        addPath("Mob-Merger.per-mob-max-stack-sizes.list.ZOMBIE", 15);
        addPath("Mob-Merger.per-mob-max-stack-sizes.list.SKELETON", 25);
        addPath("Mob-Merger.per-mob-max-stack-sizes.list.COW", 50);

        addPath("Mob-Merger.max-stacks-per-chunk", 16);
        addPath("Mob-Merger.kill-all-mobs-when-reached-max-total-stacks", true);
        addPath("Mob-Merger.max-total-stacks", 6000);


        String healthPath = "Mobs-Health.";

        addPath(healthPath + "enabled", false);
        addPath(healthPath + "per-mob-health.ZOMBIE", 5);
        addPath(healthPath + "per-mob-health.IRON_GOLEM", 10);
    }
}
