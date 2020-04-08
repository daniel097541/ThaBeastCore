package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class NoMCMMOFeatureListener extends AbstractFeatureListener {

    public NoMCMMOFeatureListener(IConfig config) {
        super(config, FeatureType.NO_MCMMO);
    }


    @EventHandler
    public void onPistoMove(BlockPistonExtendEvent e) {
        if (!isOn()) return;
        for (Block block : e.getBlocks()) {
            if (!listedBlock(block))
                continue;
            e.setCancelled(true);
            break;
        }
    }


    private boolean listedBlock(Block block) {
        return config.getConfig().getStringList("No-Piston-McMMO.listed-blocks").contains(block.getType().toString());
    }

}
