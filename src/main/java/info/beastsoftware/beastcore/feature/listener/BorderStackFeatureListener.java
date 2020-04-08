package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class BorderStackFeatureListener  extends AbstractFeatureListener {
    public BorderStackFeatureListener(IConfig config) {
        super(config, FeatureType.BORDER_STACK);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldBorderStack(EntityChangeBlockEvent e) {
        if (isOn() && e.getEntity() instanceof FallingBlock) {
            Entity entity = e.getEntity();
            Location loc = e.getEntity().getLocation();
            if (inBorder(loc.getX(), loc.getZ(), entity.getWorld().getWorldBorder())) {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
            }
        }
    }

    private boolean inBorder(double x, double z, WorldBorder worldBorder) {
        double size = worldBorder.getSize() / 2;
        double centerX = worldBorder.getCenter().getX();
        double centerZ = worldBorder.getCenter().getZ();
        return centerX - size >= x - 1 || centerX + size <= x + 1 || centerZ - size >= z - 1 || centerZ + size <= z + 1;
    }
}
