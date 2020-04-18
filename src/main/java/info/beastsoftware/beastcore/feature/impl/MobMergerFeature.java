package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.MobMerger;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.KillMobsCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.MobMergerFeatureListener;
import info.beastsoftware.beastcore.service.StackedMobsService;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class MobMergerFeature extends AbstractFeature {


    @Inject
    public MobMergerFeature(@MobMerger IConfig config, BeastCore plugin) {
        super(config, new MobMergerFeatureListener(config), new KillMobsCommand(plugin, config), FeatureType.MOB_MERGER);
    }


    public StackedMobsService getService() {
        return ((MobMergerFeatureListener) listener).getService();
    }


}
