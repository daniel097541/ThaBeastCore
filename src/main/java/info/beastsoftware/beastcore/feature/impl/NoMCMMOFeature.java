package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.NoMCMMOFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class NoMCMMOFeature extends AbstractFeature {


    @Inject
    public NoMCMMOFeature(@MainConfig IConfig config) {
        super(config,new NoMCMMOFeatureListener(config),FeatureType.NO_MCMMO);
    }
}
