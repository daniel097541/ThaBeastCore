package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.CreeperCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class CreeperFeature extends AbstractFeature {

    @Inject
    public CreeperFeature(@MainConfig IConfig config, BeastCore plugin) {
        super(config, new CreeperCommand(config, plugin), FeatureType.CREEPER);
    }
}
