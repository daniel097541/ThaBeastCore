package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.NoRespawnScreen;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.DeathScreenToggleCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.NoRespawnScreenFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class NoRespawnScreenFeature extends AbstractFeature {


    @Inject
    public NoRespawnScreenFeature(@NoRespawnScreen IConfig config,
                                  BeastCore plugin) {
        super(config, new NoRespawnScreenFeatureListener(config),  new DeathScreenToggleCommand(plugin,config), FeatureType.NO_RESPAWN_SCREEN);
    }
}
