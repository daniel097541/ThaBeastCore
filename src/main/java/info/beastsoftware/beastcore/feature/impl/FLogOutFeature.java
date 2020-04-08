package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.FLogOutFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.service.FactionsService;


@Singleton
public class FLogOutFeature extends AbstractFeature {


    @Inject
    public FLogOutFeature(@MainConfig IConfig config, FactionsService factionsService) {
        super(config, new FLogOutFeatureListener(config), FeatureType.FACTIONS_LOGOUT);

        if(!factionsService.isHooked())
            disableAll();
    }
}
