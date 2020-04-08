package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class NoSpawnerGuardFeatureListener extends AbstractFeatureListener {
    public NoSpawnerGuardFeatureListener(IConfig config) {
        super(config, FeatureType.NO_SPAWNER_GUARD);
        try {
            material = Material.valueOf("MOB_SPAWNER");
        } catch (IllegalArgumentException ignored) {
            material = Material.SPAWNER;
        }
    }


    private Material material;


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWaterBucket(PlayerInteractEvent e) {


        int radius = config.getConfig().getInt("Anti-Spawner-Guard.radius");

        if (isOn() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            if (e.isCancelled())
                return;
            Block block = e.getClickedBlock();
            World w = block.getWorld();
            Player p = e.getPlayer();

            if (p.getItemInHand().getType().equals(Material.WATER_BUCKET) || p.getItemInHand().getType().equals(Material.ICE)) {

                int bx = block.getX();
                int by = block.getY();
                int bz = block.getZ();
                for (int fx = -radius; fx <= radius; fx++) {
                    for (int fy = -radius; fy <= radius; fy++) {
                        for (int fz = -radius; fz <= radius; fz++) {
                            Block b = w.getBlockAt(bx + fx, by + fy, bz + fz);
                            if ((b.getType().equals(material))) {
                                e.setCancelled(true);
                            }
                        }
                    }
                }

            }


        }


    }



    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpawnerPlace(BlockPlaceEvent e) {
        int radius = config.getConfig().getInt("Anti-Spawner-Guard.radius");
        Material lava;
        Material stationaryWater;
        ///////////// 1.13
        try {
            lava = Material.valueOf("STATIONARY_LAVA");
            stationaryWater = Material.valueOf("STATIONARY_WATER");
        } catch (IllegalArgumentException ignored) {
            lava = Material.LAVA;
            stationaryWater = Material.WATER;
        }
        ///////////// 1.13
        if (isOn() && e.getBlockPlaced().getType().equals(material)) {

            if (e.isCancelled()) {
                return;
            }
            Block block = e.getBlock();
            World w = block.getWorld();
            if (block.getType().equals(material)) {

                int bx = block.getX();
                int by = block.getY();
                int bz = block.getZ();
                for (int fx = -radius; fx <= radius; fx++) {
                    for (int fy = -radius; fy <= radius; fy++) {
                        for (int fz = -radius; fz <= radius; fz++) {
                            Block b = w.getBlockAt(bx + fx, by + fy, bz + fz);
                            if ((b.getType().equals(lava)) || (b.getType().equals(Material.WATER) || b.getType().equals(stationaryWater))) {
                                b.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWaterFlow(BlockFromToEvent e) {
        int radius = config.getConfig().getInt("Anti-Spawner-Guard.radius");

        if (isOn() && !e.isCancelled()) {
            if (e.isCancelled()) {
                return;
            }

            Block block = e.getBlock();
            World world = block.getWorld();
            int blockX = block.getX();
            int blockY = block.getY();
            int blockZ = block.getZ();

            for (int fromX = -(radius + 1); fromX <= radius + 1; fromX++) {
                for (int fromY = -(radius + 1); fromY <= radius + 1; fromY++) {
                    for (int fromZ = -(radius + 1); fromZ <= radius + 1; fromZ++) {
                        Block b = world.getBlockAt(blockX + fromX, blockY + fromY, blockZ + fromZ);
                        if (b.getType().equals(material)) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

}
