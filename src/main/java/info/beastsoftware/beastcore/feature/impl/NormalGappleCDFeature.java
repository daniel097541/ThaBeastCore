package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.NormalGappleCDFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class NormalGappleCDFeature extends AbstractFeature {


    @Inject
    public NormalGappleCDFeature(@MainConfig IConfig config) {
        super(config,new NormalGappleCDFeatureListener(config),FeatureType.NORMAL_GAPPLE_CD);
    }
}
