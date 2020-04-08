package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.MobHoppers;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.command.MobHoppersCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.MobHoppersFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class MobHoppersFeature extends AbstractFeature {



    @Inject
    public MobHoppersFeature(@MobHoppers IConfig config,
                             @MobHoppers IDataConfig dataConfig,
                             BeastCore plugin) {
        super(config,new MobHoppersFeatureListener(config,dataConfig),new MobHoppersCommand(plugin,config),FeatureType.MOB_HOPPERS);

    }
}
