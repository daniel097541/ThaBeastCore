package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonExtendEvent;

import java.util.ArrayList;
import java.util.List;

public class CannonsLimiterFeatureListener extends AbstractFeatureListener {

    private List<String> materials;


    public CannonsLimiterFeatureListener(IConfig config) {
        super(config, FeatureType.AUTO_CANNONS_LIMITER);
        initMaterials();
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent e) {

        if (isOn()) {
            if (!checkMovement(e.getBlocks()))
                e.setCancelled(true);
        }
    }

    private boolean checkMovement(List<Block> blocks) {
        int maxHigh = config.getConfig().getInt("Auto-Cannons-Limiter.max-high");
        for (Block block : blocks) {
            if (materials.contains(block.getType().name())) {
                for (int i = 1; i <= maxHigh + 1; i++) {
                    if (materials.contains(block.getWorld().getBlockAt(block.getX(), block.getY() + i, block.getZ()).getType().name())) {
                        if (i == maxHigh) {
                            return false;
                        }
                        continue;
                    }
                    break;
                }
            }
        }
        return true;
    }

    private void initMaterials() {
        materials = new ArrayList<>();
        materials.add(Material.SAND.name());
        materials.add(Material.GRAVEL.name());
        materials.add(Material.SOUL_SAND.name());
    }


}
