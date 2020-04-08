package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplodeLavaFeatureListener extends AbstractFeatureListener {


    public ExplodeLavaFeatureListener(IConfig config) {
        super(config, FeatureType.EXPLODE_LAVA);
    }



    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLavaExplosion(EntityExplodeEvent e) {
        if (isOn()) {
            explodeLava(e.getLocation());
        }
    }


    private void explodeLava(Location loc) {

        int radius = config.getConfig().getInt("Explode-Lava.radius");


        for (int x = radius * -1; x <= radius; x++) {
            for (int y = radius * -1; y <= radius; y++) {
                for (int z = radius * -1; z <= radius; z++) {
                    Block block = loc.getWorld().getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
                    if (isLava(block.getType())) {
                        block.setType(Material.AIR);
                    }

                }
            }
        }


    }

    private boolean isLava(Material material) {
        //////////// 1.13 ////////////////
        Material material1;
        try {
            material1 = Material.valueOf("STATIONARY_LAVA");
        } catch (IllegalArgumentException ignored) {
            material1 = Material.LAVA;
        }
        //////////// 1.13 ////////////////

        return material.equals(Material.LAVA) || material.equals(material1);
    }

}
