package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

public class GolemDeathFeatureListener extends AbstractFeatureListener {

    public GolemDeathFeatureListener(IConfig config) {
        super(config, FeatureType.GOLEM_DEATH);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onIronGolemTouchLava(EntityDamageEvent e) {
        if (isOn()) {
            if (isAnIronGolem(e.getEntity())) {
                if (isOnFire(e.getCause())) {
                    e.setDamage(config.getConfig().getInt("Irongolem-Fast-Death.damage"));
                }
            }
        }
    }

    private boolean isAnIronGolem(Entity e) {
        return e.getType().equals(EntityType.IRON_GOLEM);
    }

    private boolean isOnFire(EntityDamageEvent.DamageCause d) {
        return d.equals(EntityDamageEvent.DamageCause.FIRE) || d.equals(EntityDamageEvent.DamageCause.LAVA) || d.equals(EntityDamageEvent.DamageCause.FIRE_TICK);
    }

}
