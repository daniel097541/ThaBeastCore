package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Wilderness;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.WildCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.WildernessFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class WildernessFeature extends AbstractFeature {


    @Inject
    public WildernessFeature(@Wilderness IConfig config,
                             BeastCore plugin,
                             IHookManager hookManager) {
        super(config,new WildernessFeatureListener(config),
                new WildCommand(plugin,config), FeatureType.WILDERNESS);
    }
}
