package info.beastsoftware.beastcore.feature.listener;

import de.dustplanet.silkspawners.events.SilkSpawnersSpawnerBreakEvent;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class SpawnerMineFeatureListener extends AbstractFeatureListener {

    public SpawnerMineFeatureListener(IConfig config) {
        super(config, FeatureType.SPAWNER_MINE);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnerMine(SilkSpawnersSpawnerBreakEvent e) {

        String perm = config.getConfig().getString("Anti-Spawner-Mine.undetectable-permission");
        double radius = config.getConfig().getDouble("Anti-Spawner-Mine.radius");

        BeastPlayer player = this.getPlayer(e.getPlayer());

        if (perm == null) {
            perm = "btf.spawners.undetectable";
        }

        if (radius <= 0) {
            radius = 200.0;
        }

        boolean nearbyEnemies = false;

        if (isFactionsHooked()) {
            nearbyEnemies = player.thereAreNearbyEnemies(radius);
        }

        if (isOn() && nearbyEnemies && !e.getPlayer().hasPermission(perm))
            e.setCancelled(true);

        else return;

        String message = config.getConfig().getString("Anti-Spawner-Mine.message");

        if (message == null) {
            message = " ";
        }

        BeastCore.getInstance().sms(e.getPlayer(), stringUtil.replacePlaceholder(message, "{radius}",
                radius + ""));
    }
}
