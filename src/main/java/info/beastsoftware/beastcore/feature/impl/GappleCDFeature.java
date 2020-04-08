package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.GappleCDFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;


@Singleton
public class GappleCDFeature extends AbstractFeature {



    @Inject
    public GappleCDFeature(@MainConfig IConfig config) {
        super(config,new GappleCDFeatureListener(config),FeatureType.GAPPLE_CD);
    }


    public int getCooldown(BeastPlayer player){
        return ((GappleCDFeatureListener) this.getListener()).getCooldown(player);
    }


    public boolean isOnCooldown(BeastPlayer player){
        return ((GappleCDFeatureListener) this.getListener()).isPlayerOnCooldown(player);
    }
}
