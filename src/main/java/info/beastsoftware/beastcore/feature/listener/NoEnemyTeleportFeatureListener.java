package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;

public class NoEnemyTeleportFeatureListener extends AbstractFeatureListener {

    public NoEnemyTeleportFeatureListener(IConfig config) {
        super(config, FeatureType.NO_ENEMY_TELEPORT);
    }


    private boolean isNotAllowedLocation(Location location, Player player) {
        try {
            return this.getAtLocation(location).isEnemy(this.getPlayer(player).getMyFaction());
        } catch (Throwable ignored) {
            return false;
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent event) {

        if (!isOn()) {
            return;
        }

        Player player = event.getPlayer();
        String permission = config.getConfig().getString("bypass-permission");


        if (player.hasPermission(permission)) {
            return;
        }

        PlayerTeleportEvent.TeleportCause cause = event.getCause();

        if (cause.equals(PlayerTeleportEvent.TeleportCause.PLUGIN) || cause.equals(PlayerTeleportEvent.TeleportCause.COMMAND)) {

            Location to = event.getTo();

            //cannot teleport there
            if (isNotAllowedLocation(to, player)) {
                event.setCancelled(true);
                String message = config.getConfig().getString("message");
                this.getBeastCore().sms(player, message);
            }
        }

    }


}
