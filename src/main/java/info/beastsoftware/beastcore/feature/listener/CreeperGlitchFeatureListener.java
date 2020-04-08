package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class CreeperGlitchFeatureListener extends AbstractFeatureListener {


    public CreeperGlitchFeatureListener(IConfig config) {
        super(config, FeatureType.NO_CREEPER_GLITCH);
    }

    @EventHandler
    public void onCreeperDrown(EntityDamageEvent e) {
        if (isOn()) {
            if (e.getEntity().getType().equals(EntityType.CREEPER) && (e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING) || e.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION))) {
                e.getEntity().remove();
            }
        }
    }
}
