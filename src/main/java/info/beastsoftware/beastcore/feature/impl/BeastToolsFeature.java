package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.BeastTools;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.BeastToolsCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.BeastToolsFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class BeastToolsFeature extends AbstractFeature {

    @Inject
    protected BeastToolsFeature(@BeastTools IConfig config,
                                BeastCore plugin) {
        super(config, new BeastToolsFeatureListener(config), new BeastToolsCommand(plugin,config), FeatureType.BEAST_TOOLS);
    }
}
