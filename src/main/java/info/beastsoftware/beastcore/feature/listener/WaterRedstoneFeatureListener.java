package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.ArrayList;
import java.util.List;

public class WaterRedstoneFeatureListener extends AbstractFeatureListener {


    private final List<String> items = new ArrayList<>();

    public WaterRedstoneFeatureListener(IConfig config ) {
        super(config, FeatureType.WATER_REDSTONE);
        items.add("REDSTONE");
        items.add("DIODE_BLOCK_ON");
        items.add("DIODE_BLOCK_OFF");
        items.add("DIODE");
        items.add("REDSTONE_WIRE");
        items.add("REDSTONE_TORCH_ON");
        items.add("REDSTONE_TORCH_OFF");
        items.add("REDSTONE_COMPARATOR");
        items.add("REDSTONE_COMPARATOR_OFF");
        items.add("REDSTONE_COMPARATOR_ON");
    }

    @EventHandler
    public void onWaterFlow(BlockFromToEvent e) {
        if (isOn() && items.contains(e.getToBlock().getType().toString()))
            e.setCancelled(true);
    }
}
