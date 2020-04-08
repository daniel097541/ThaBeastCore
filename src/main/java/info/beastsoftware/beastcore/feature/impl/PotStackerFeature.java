package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.PotStacker;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.PotionStackerCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class PotStackerFeature extends AbstractFeature {


    @Inject
    public PotStackerFeature(@PotStacker IConfig config,
                             BeastCore plugin) {
        super(config, new PotionStackerCommand(plugin,config), FeatureType.POTSTACKER);
    }
}
