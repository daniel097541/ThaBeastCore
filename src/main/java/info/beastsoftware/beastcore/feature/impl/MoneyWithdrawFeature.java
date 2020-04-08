package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.MoneyWithdraw;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.MoneyWithdrawCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.MoneyWithdrawFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class MoneyWithdrawFeature extends AbstractFeature {


    @Inject
    public MoneyWithdrawFeature(@MoneyWithdraw IConfig config,
                                BeastCore plugin,
                                IHookManager hookManager) {
        super(config,new MoneyWithdrawFeatureListener(config,hookManager),new MoneyWithdrawCommand(plugin,config,hookManager.getEconomyHook()),FeatureType.MONEY_WITHDRAW);


        if(!hookManager.isEconomyHooked()){
            this.disableAll();
        }

    }
}
