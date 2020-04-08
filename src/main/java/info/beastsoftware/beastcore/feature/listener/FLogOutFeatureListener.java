package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

public class FLogOutFeatureListener extends AbstractFeatureListener {

    public FLogOutFeatureListener(IConfig config) {
        super(config, FeatureType.FACTIONS_LOGOUT);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogOut(PlayerQuitEvent e) {

        BeastPlayer player = this.getPlayer(e.getPlayer());


        try {
            if (isOn() && isFactionsHooked() && player.getMyFaction().isEnemy(this.getAtLocation(player.getBukkitLocation()))) {
                if (!e.getPlayer().hasPermission(config.getConfig().getString("Faction-Logout.bypass-permission")))
                    e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
            }
        }
        catch (Throwable ignored){}
    }

}
