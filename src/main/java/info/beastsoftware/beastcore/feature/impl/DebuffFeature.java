package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Debuff;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.DebuffCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class DebuffFeature extends AbstractFeature {


    @Inject
    public DebuffFeature(@Debuff IConfig config, BeastCore plugin) {
        super(config, new DebuffCommand(plugin,config), FeatureType.DEBUFF);
    }
}
