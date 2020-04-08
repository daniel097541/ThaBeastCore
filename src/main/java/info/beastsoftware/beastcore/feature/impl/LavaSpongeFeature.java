package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.LavaSpongeFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class LavaSpongeFeature extends AbstractFeature {
    @Inject
    public LavaSpongeFeature(@MainConfig IConfig config) {
        super(config,new LavaSpongeFeatureListener(config),FeatureType.LAVA_SPONGE);
    }
}
