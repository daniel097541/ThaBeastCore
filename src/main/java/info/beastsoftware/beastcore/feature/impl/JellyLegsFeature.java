package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.JellyLegsCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.JellyLegsFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;


@Singleton
public class JellyLegsFeature extends AbstractFeature {

    @Inject
    public JellyLegsFeature(@MainConfig IConfig config,
                            BeastCore plugin) {
        super(config,new JellyLegsFeatureListener(config), new JellyLegsCommand(plugin,config),FeatureType.JELLY_LEGS);
    }


    public boolean hasJellyLegsEnabled(BeastPlayer player){
        return ((JellyLegsFeatureListener) this.getListener()).hasJellyLegsEnabled(player);
    }


}
