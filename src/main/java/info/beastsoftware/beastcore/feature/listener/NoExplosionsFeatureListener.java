package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

public class NoExplosionsFeatureListener extends AbstractFeatureListener {


    public NoExplosionsFeatureListener(IConfig config) {
        super(config, FeatureType.NO_EXPLOSIONS);
    }


    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        if (isOn())
            e.setCancelled(true);
    }
}
