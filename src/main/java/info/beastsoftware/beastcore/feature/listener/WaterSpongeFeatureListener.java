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

public class WaterSpongeFeatureListener extends AbstractFeatureListener {


    public WaterSpongeFeatureListener(IConfig config) {
        super(config, FeatureType.WATER_SPONGE);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSpongePlace(BlockPlaceEvent e) {
        if (isOn() && !e.isCancelled() && isSponge(e.getBlockPlaced().getType())) {
            int radius = config.getConfig().getInt("Water-Sponge.radius");
            Block block = e.getBlock();
            World world = block.getWorld();
            int blockX = block.getX();
            int blockY = block.getY();
            int blockZ = block.getZ();
            for (int posX = -radius; posX <= radius; posX++) {
                for (int posY = -radius; posY <= radius; posY++) {
                    for (int posZ = -radius; posZ <= radius; posZ++) {
                        Block replacedBlock = world.getBlockAt(blockX + posX, blockY + posY, blockZ + posZ);
                        if (isWater(replacedBlock.getType())) {
                            replacedBlock.setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }

//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void onWaterFlowOverSponge(BlockFromToEvent e) {
//        if (isOn() && !e.isCancelled()) {
//            int radius = config.getConfig().getInt("Water-Sponge.radius");
//            Block block = e.getBlock();
//            World world = block.getWorld();
//            int blockX = block.getX();
//            int blockY = block.getY();
//            int blockZ = block.getZ();
//            for (int fromX = -(radius + 1); fromX <= radius + 1; fromX++) {
//                for (int fromY = -(radius + 1); fromY <= radius + 1; fromY++) {
//                    for (int fromZ = -(radius + 1); fromZ <= radius + 1; fromZ++) {
//                        Block b = world.getBlockAt(blockX + fromX, blockY + fromY, blockZ + fromZ);
//                        if (isSponge(b.getType())) {
//                            e.setCancelled(true);
//                        }
//                    }
//                }
//            }
//        }
//    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWaterBucket(PlayerInteractEvent e) {

        if (isOn() && !e.isCancelled() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            int radius = config.getConfig().getInt("Water-Sponge.radius");

            Block block = e.getClickedBlock();
            World w = block.getWorld();
            Player p = e.getPlayer();

            if (p.getItemInHand().getType().equals(Material.WATER_BUCKET)) {
                int bx = block.getX();
                int by = block.getY();
                int bz = block.getZ();
                for (int fx = -radius; fx <= radius; fx++) {
                    for (int fy = -radius; fy <= radius; fy++) {
                        for (int fz = -radius; fz <= radius; fz++) {
                            Block b = w.getBlockAt(bx + fx, by + fy, bz + fz);
                            if (isSponge(b.getType())) {
                                e.setCancelled(true);
                            }
                        }
                    }
                }

            }
        }
    }

    private boolean isSponge(Material material) {
        return material.equals(Material.SPONGE);
    }

    private boolean isWater(Material material) {
        ///////////// 1.13
        Material material1;
        try {
            material1 = Material.valueOf("STATIONARY_WATER");
        } catch (IllegalArgumentException ignored) {
            material1 = Material.WATER;
        }
        ///////////// 1.13
        return material.equals(Material.WATER) || material.equals(material1);
    }


}
