package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class NoRespawnScreenFeatureListener extends AbstractFeatureListener {


    public NoRespawnScreenFeatureListener(IConfig config) {
        super(config,FeatureType.NO_RESPAWN_SCREEN);
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event){

        if(!isOn()) return;

        Player player = event.getEntity();


        String playerPath = "disabled-players." + player.getUniqueId().toString();


        //disabled autorespawn
        if(config.getConfig().getBoolean(playerPath)) return;

        Bukkit.getScheduler().runTaskLater(BeastCore.getInstance(), ()-> player.spigot().respawn(), 1L);

    }

}
