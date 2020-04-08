package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.NoWitherFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class NoWitherFeature extends AbstractFeature {
    @Inject
    public NoWitherFeature(@MainConfig IConfig config) {
        super(config, new NoWitherFeatureListener(config), FeatureType.NO_WITHER);
    }
}
