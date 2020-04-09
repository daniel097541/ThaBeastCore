package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Alerts;
import info.beastsoftware.beastcore.annotations.features.ThrowableCegss;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.command.AlertsCommand;
import info.beastsoftware.beastcore.command.ThrowableEggsCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.AlertsFeatureListener;
import info.beastsoftware.beastcore.feature.listener.ThrowableCeggsFeatureListener;
import info.beastsoftware.beastcore.gui.AlertsGui;
import info.beastsoftware.beastcore.gui.IComplexGui;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class ThrowableCeggsFeature extends AbstractFeature {


    @Inject
    public ThrowableCeggsFeature(@ThrowableCegss IConfig config, BeastCore plugin) {
        super(config, new ThrowableCeggsFeatureListener(config), new ThrowableEggsCommand(config, plugin), FeatureType.THROWABLE_CEGGS);
    }
}
