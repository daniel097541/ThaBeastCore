package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.HopperFilter;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.HopperFilterCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.HopperFilterFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.inventory.ItemStack;


@Singleton
public class HopperFilterFeature extends AbstractFeature {


    @Inject
    public HopperFilterFeature(@HopperFilter IConfig config,
                               BeastCore plugin) {
        super(config, new HopperFilterFeatureListener(config),new HopperFilterCommand(plugin,config),FeatureType.HOPPER_FILTER);
    }

    public boolean isItemFiltered(ItemStack itemStack){
        return ((HopperFilterFeatureListener) this.getListener()).isItemFiltered(itemStack);
    }


}
