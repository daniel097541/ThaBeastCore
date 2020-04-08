package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Potion;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.PotionCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class PotionFeature extends AbstractFeature {

    @Inject
    public PotionFeature(@Potion IConfig config,
                         BeastCore plugin) {
        super(config, new PotionCommand(plugin,config), FeatureType.POTION);
    }
}
