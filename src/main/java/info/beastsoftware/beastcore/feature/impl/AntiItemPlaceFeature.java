package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.AntiItemPlaceFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class AntiItemPlaceFeature extends AbstractFeature {

    @Inject
    protected AntiItemPlaceFeature(@MainConfig IConfig config,
                                   IHookManager hookManager) {
        super(config, new AntiItemPlaceFeatureListener(config), FeatureType.ANTI_ITEM_PLACE);
    }
}
