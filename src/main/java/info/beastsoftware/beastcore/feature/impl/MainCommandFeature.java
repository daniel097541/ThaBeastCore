package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.BtfCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class MainCommandFeature extends AbstractFeature {


    @Inject
    public MainCommandFeature(@MainConfig IConfig config,
                              BeastCore plugin) {
        super(config, new BtfCommand(plugin,config), FeatureType.MAIN_COMMAND);
    }
}
