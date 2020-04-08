package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.NoSpawnerGuardFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class NoSpawnerGuardFeature extends AbstractFeature {


    @Inject
    public NoSpawnerGuardFeature(@MainConfig IConfig config) {
        super(config,new NoSpawnerGuardFeatureListener(config), FeatureType.NO_SPAWNER_GUARD);
    }
}
