package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.VoidChests;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.command.VoidChestsCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.VoidChestsFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class VoidChestsFeature extends AbstractFeature {


    @Inject
    public VoidChestsFeature(@VoidChests IConfig config,
                             @VoidChests IDataConfig dataConfig,
                             BeastCore plugin,
                             IHookManager hookManager) {
        super(config,
                new VoidChestsFeatureListener(config,dataConfig,hookManager.getEconomyHook()),
                new VoidChestsCommand(plugin,config),
                FeatureType.VOID_CHESTS);


        if(!hookManager.isEconomyHooked() || !hookManager.isFactionsHooked()){
            this.disableAll();
        }
    }
}
