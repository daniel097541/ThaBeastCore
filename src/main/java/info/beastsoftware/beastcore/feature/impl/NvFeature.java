package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.NV;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.NightVisionCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class NvFeature extends AbstractFeature {


    @Inject
    public NvFeature(@NV IConfig config) {
        super(config, new NightVisionCommand(config), FeatureType.NV);
    }
}
