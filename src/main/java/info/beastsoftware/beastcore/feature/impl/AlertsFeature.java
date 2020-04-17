package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Alerts;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.command.AlertsCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.AlertsFeatureListener;
import info.beastsoftware.beastcore.gui.AlertsGui;
import info.beastsoftware.beastcore.gui.IComplexGui;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class AlertsFeature extends AbstractFeature {

    private IComplexGui gui;


    @Inject
    public AlertsFeature(@Alerts IConfig config,
                         @Alerts IDataConfig dataConfig,
                         BeastCore plugin,
                         IHookManager hookManager) {
        super(config,
                new AlertsFeatureListener(config, dataConfig, FeatureType.ALERTS),
                new AlertsCommand(plugin, config),
                dataConfig, FeatureType.ALERTS);


        if(!hookManager.isFactionsHooked()){
            this.disableAll();
        }

        else{
            this.gui = new AlertsGui(config,dataConfig);
        }
    }
}
