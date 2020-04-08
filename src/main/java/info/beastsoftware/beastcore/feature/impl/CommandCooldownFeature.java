package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.CommandCooldown;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.CommandCooldownFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class CommandCooldownFeature extends AbstractFeature {

    @Inject
    protected CommandCooldownFeature(@CommandCooldown IConfig config) {
        super(config, new CommandCooldownFeatureListener(config), FeatureType.COMMAND_COOLWON);
    }
}
