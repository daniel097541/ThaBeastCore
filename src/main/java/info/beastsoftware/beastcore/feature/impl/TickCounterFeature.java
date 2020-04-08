package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.TickCounter;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.TickCounterCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.TickCounterFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class TickCounterFeature extends AbstractFeature {


    @Inject
    public TickCounterFeature(@TickCounter IConfig config,
                              BeastCore plugin) {
        super(config,new TickCounterFeatureListener(config), new TickCounterCommand(plugin,config), FeatureType.TICK_COUNTER);
    }
}
