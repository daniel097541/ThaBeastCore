package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.JellyLegsToogleEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class JellyLegsFeatureListener extends AbstractFeatureListener {

    private final List<String> jellyPlayers = new ArrayList<>();


    public JellyLegsFeatureListener(IConfig config) {
        super(config, FeatureType.JELLY_LEGS);
    }



    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerFall(EntityDamageEvent e) {
        if (isOn() && e.getCause().equals(EntityDamageEvent.DamageCause.FALL) && e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (jellyPlayers.contains(p.getName())) {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onJellyLegsEvent(JellyLegsToogleEvent e) {
        if (e.isOn())
            jellyPlayers.add(e.getPlayer().getName());
        else jellyPlayers.remove(e.getPlayer().getName());
    }

    public boolean hasJellyLegsEnabled(BeastPlayer player) {
        return jellyPlayers.contains(player.getName());
    }
}
