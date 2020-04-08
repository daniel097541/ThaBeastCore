package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.MobMerger;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.KillMobsCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.MobMergerFeatureListener;
import info.beastsoftware.beastcore.manager.MobMergerManager;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class MobMergerFeature extends AbstractFeature {


    @Inject
    public MobMergerFeature(@MobMerger IConfig config, BeastCore plugin, MobMergerManager mobMergerManager) {
        super(config, new MobMergerFeatureListener(config, mobMergerManager), new KillMobsCommand(plugin, config), FeatureType.MOB_MERGER);
    }


    public MobMergerManager getStackedMobsManager() {
        return ((MobMergerFeatureListener) listener).getMobMergerManager();
    }


}
