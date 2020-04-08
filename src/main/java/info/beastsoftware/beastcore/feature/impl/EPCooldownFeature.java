package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.EPCooldownFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;


@Singleton
public class EPCooldownFeature extends AbstractFeature {


    @Inject
    public EPCooldownFeature(@MainConfig IConfig config) {
        super(config,
                new EPCooldownFeatureListener(config), FeatureType.ENDER_PEARL_COOLDOWN);
    }

    public int getCooldown(BeastPlayer player) {
        return ((EPCooldownFeatureListener) this.getListener()).getCooldown(player);
    }
}
