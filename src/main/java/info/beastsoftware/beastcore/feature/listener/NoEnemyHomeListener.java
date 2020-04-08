package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.manager.HomesManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class NoEnemyHomeListener extends AbstractFeatureListener {

    private final HomesManager homesManager;


    public NoEnemyHomeListener(IConfig config, HomesManager homesManager) {
        super(config, FeatureType.NO_ENEMY_HOMES);
        this.homesManager = homesManager;
    }


    private void checkHomes(Player player){
        if(isOn()) {
            boolean allowAlly = this.config.getConfig().getBoolean("allow-homes-in-ally-land");
            homesManager.checkHomes(player, allowAlly);
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();

        String permission = config.getConfig().getString("bypass-permission");
        if(player.hasPermission(permission)){
            return;
        }


        this.checkHomes(player);
    }

    @EventHandler
    public void onLogOut(PlayerQuitEvent event){
        Player player = event.getPlayer();

        String permission = config.getConfig().getString("bypass-permission");
        if(player.hasPermission(permission)){
            return;
        }

        this.checkHomes(player);
    }

}
