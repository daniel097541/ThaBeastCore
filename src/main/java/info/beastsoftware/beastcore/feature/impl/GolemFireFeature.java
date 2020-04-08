package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.GolemFireFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class GolemFireFeature extends AbstractFeature {



    @Inject
    public GolemFireFeature(@MainConfig IConfig config) {
        super(config,new GolemFireFeatureListener(config),FeatureType.GOLEM_FIRE);
    }
}
