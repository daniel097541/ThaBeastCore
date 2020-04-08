package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class HideStreamFeatureListener extends AbstractFeatureListener {

    public HideStreamFeatureListener(IConfig config) {
        super(config, FeatureType.HIDE_STREAM);
    }

    @EventHandler
    public void onLogOut(PlayerQuitEvent e) {
        if (!isOn()) return;
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onPlayerLogIn(PlayerJoinEvent e) {
        if (!isOn()) return;
        e.setJoinMessage(null);
    }



}
