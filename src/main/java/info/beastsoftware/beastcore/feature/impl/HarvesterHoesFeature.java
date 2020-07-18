package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Debuff;
import info.beastsoftware.beastcore.annotations.features.HarvesterHoes;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.HarvesterHoesCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.HarvesterHoesFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class HarvesterHoesFeature extends AbstractFeature {


    @Inject
    public HarvesterHoesFeature(@HarvesterHoes IConfig config, BeastCore plugin) {
        super(config, new HarvesterHoesFeatureListener(config), new HarvesterHoesCommand(plugin, config), FeatureType.HARVESTER_HOES);
    }
}
