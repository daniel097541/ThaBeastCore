package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PearlGlitchFeatureListener extends AbstractFeatureListener {

    public PearlGlitchFeatureListener(IConfig config) {
        super(config, FeatureType.PEARL_GLITCH);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (isOn() && event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
            Location location = event.getTo();
            location.setY(location.getBlockY() + (double) 2);
            if (!location.getBlock().getType().equals(Material.AIR)) {
                BeastCore.getInstance().sms(event.getPlayer(), config.getConfig().getString("Pearl-Glitch-Fix.message"));
                event.setCancelled(true);
            }

            location.setX(location.getBlockX() + 0.5D);
            location.setY(location.getBlockY());
            location.setZ(location.getBlockZ() + 0.5D);
            event.setTo(location);
        }
    }
}
