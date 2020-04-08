package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.Blaze;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class WaterBlazesFeaturesListener extends AbstractFeatureListener {


    public WaterBlazesFeaturesListener(IConfig config ) {
        super(config, FeatureType.WATER_BLAZES);
    }


    @EventHandler
    public void onBlazeDamage(EntityDamageEvent e) {
        if (e.isCancelled()) return;
        if (!isOn()) return;
        if ((e.getEntity() instanceof Blaze) && e.getCause().equals(EntityDamageEvent.DamageCause.DROWNING))
            e.setCancelled(true);
    }


}
