package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WorldBorderPearlFeatureListener extends AbstractFeatureListener {

    public WorldBorderPearlFeatureListener(IConfig config) {
        super(config, FeatureType.WORLD_BORDER_PEARL);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEnderPearl(PlayerTeleportEvent e) {
        WorldBorder worldBorder = e.getPlayer().getWorld().getWorldBorder();

        if ((worldBorder == null) || !(e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL))) return;

        if (!isOn() || e.getPlayer().hasPermission(config.getConfig().getString("Deny-Enderpearl-Outside-Border.bypass-permission")))
            return;

        if (outsideBorder(e.getTo().getX(), e.getTo().getZ(), worldBorder)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Deny-Enderpearl-Outside-Border.message")));
        }
    }

    private boolean outsideBorder(double x, double z, WorldBorder worldBorder) {
        double size = worldBorder.getSize() / 2;
        double centerX = worldBorder.getCenter().getX();
        double centerZ = worldBorder.getCenter().getZ();
        return centerX - size > x || centerX + size < x || centerZ - size > z || centerZ + size < z;
    }




}
