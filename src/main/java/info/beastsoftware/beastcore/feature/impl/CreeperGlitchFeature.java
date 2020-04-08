package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.CreeperGlitchFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class CreeperGlitchFeature extends AbstractFeature {

    @Inject
    protected CreeperGlitchFeature(@MainConfig IConfig config) {
        super(config, new CreeperGlitchFeatureListener(config), FeatureType.NO_CREEPER_GLITCH);
    }
}
