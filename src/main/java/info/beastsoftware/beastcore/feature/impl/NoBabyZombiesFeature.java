package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.NoBabyZombies;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.NoBabyZombiesFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class NoBabyZombiesFeature extends AbstractFeature {


    @Inject
    protected NoBabyZombiesFeature(@NoBabyZombies IConfig config) {
        super(config, new NoBabyZombiesFeatureListener(config), FeatureType.NO_BABY_ZOMBIES);
    }
}
