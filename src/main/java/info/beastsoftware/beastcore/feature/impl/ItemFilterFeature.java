package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.ItemFilter;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.command.ItemFilterCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.ItemFilterFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class ItemFilterFeature extends AbstractFeature {


    @Inject
    public ItemFilterFeature(@ItemFilter IConfig config,
                             @ItemFilter IDataConfig dataConfig,
                             BeastCore plugin) {
        super(config,new ItemFilterFeatureListener(config,dataConfig), new ItemFilterCommand(plugin,config),FeatureType.ITEM_FILTER);
    }
}
