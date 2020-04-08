package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.LapisFiller;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.LapisFillerFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class LapisFillerFeature extends AbstractFeature {

    @Inject
    public LapisFillerFeature(@LapisFiller IConfig config) {
        super(config,new LapisFillerFeatureListener(config),FeatureType.LAPIS_FILLER);
    }
}
