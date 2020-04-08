package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.VoidSpawnCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.VoidFallFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class VoidFallFeature extends AbstractFeature {


    @Inject
    public VoidFallFeature(@MainConfig IConfig config,
                           BeastCore plugin) {
        super(config,new VoidFallFeatureListener(config), new VoidSpawnCommand(plugin,config), FeatureType.VOID_FALL);

    }
}
