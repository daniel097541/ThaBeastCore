package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.ServerProtectorFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class ServerProtectorFeature extends AbstractFeature {


    @Inject
    public ServerProtectorFeature(@MainConfig IConfig config) {
        super(config,new ServerProtectorFeatureListener(config),FeatureType.SERVER_PROTECTOR);
    }
}
