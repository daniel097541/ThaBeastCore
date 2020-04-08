package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;

public class WaterBorderFeatureListener extends AbstractFeatureListener {


    public WaterBorderFeatureListener(IConfig config ) {
        super(config, FeatureType.WATER_BORDER);
    }


    @EventHandler
    public void onWaterFlow(BlockFromToEvent e) {
        if (!isOn()) return;
        if (outsideBorder(e.getToBlock().getLocation().getX(), e.getToBlock().getLocation().getZ(), e.getToBlock().getWorld().getWorldBorder()))
            e.setCancelled(true);
    }


    private boolean outsideBorder(double x, double z, WorldBorder worldBorder) {
        double size = worldBorder.getSize() / 2;
        double centerX = worldBorder.getCenter().getX();
        double centerZ = worldBorder.getCenter().getZ();
        return centerX - size > x || centerX + size <= x || centerZ - size > z || centerZ + size <= z;
    }
}
