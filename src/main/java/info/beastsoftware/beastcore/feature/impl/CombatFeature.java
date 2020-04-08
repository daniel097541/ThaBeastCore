package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Combat;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.CombatCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.CombatFeatureListener;
import info.beastsoftware.beastcore.manager.ICombatCooldownManager;
import info.beastsoftware.beastcore.manager.ICombatNPCManager;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.manager.IPvPManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

@Singleton
public class CombatFeature extends AbstractFeature {


    @Inject
    public CombatFeature(@Combat IConfig config, IHookManager hookManager, BeastCore plugin, ICombatNPCManager combatNPCManager, IPvPManager pvPManager, ICombatCooldownManager combatCooldownManager) {
        super(config, new CombatFeatureListener(config, combatNPCManager, pvPManager, combatCooldownManager), new CombatCommand(plugin,config), FeatureType.COMBAT);
    }


    public int getCombatTimeOfPlayer(BeastPlayer player){
        return ((CombatFeatureListener)this.getListener()).getTimeInCOmbat(player);
    }

    public boolean isPlayerInCombat(BeastPlayer player){
        return ((CombatFeatureListener)this.getListener()).isOnCombat(player);
    }

    public String getCombatFormatted(BeastPlayer player){
        return ((CombatFeatureListener)this.getListener()).getCombatFormatted(player);
    }

    public void setPlayerInCombat(BeastPlayer player, int time){
        ((CombatFeatureListener)this.getListener()).setPlayerInCombat(player,time);
    }

    public void removePlayerInCombat(BeastPlayer player){
        ((CombatFeatureListener)this.getListener()).endCombatForPlayer(player);
    }

}
