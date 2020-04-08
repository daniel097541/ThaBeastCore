package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.WorldBorderPearlFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class WorldBorderPearlFeature extends AbstractFeature {


    @Inject
    public WorldBorderPearlFeature(@MainConfig IConfig config) {
        super(config,new WorldBorderPearlFeatureListener(config), FeatureType.WORLD_BORDER_PEARL);
    }
}
