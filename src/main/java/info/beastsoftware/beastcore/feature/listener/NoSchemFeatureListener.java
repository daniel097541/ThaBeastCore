package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

public class NoSchemFeatureListener extends AbstractFeatureListener {

    public NoSchemFeatureListener(IConfig config) {
        super(config, FeatureType.NO_SCHEM);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEnderPearl(BlockPlaceEvent e) {
        if ((e.getPlayer().getWorld().getWorldBorder() == null)) {
            return;
        }
        if (isOn()) {
            double worldborder = e.getPlayer().getWorld().getWorldBorder().getSize() / 2.0D;
            String message = config.getConfig().getString("Anti-Schem-Bug.message");
            if (e.getPlayer().getWorld().getWorldBorder().getCenter().getX() + worldborder < e.getBlock().getX()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                return;
            }

            if (e.getPlayer().getWorld().getWorldBorder().getCenter().getX() - worldborder > e.getBlock().getX()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                return;
            }

            if (e.getPlayer().getWorld().getWorldBorder().getCenter().getZ() + worldborder < e.getBlock().getZ()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                return;
            }

            if (e.getPlayer().getWorld().getWorldBorder().getCenter().getZ() - worldborder > e.getBlock().getZ()) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }
    }



}
