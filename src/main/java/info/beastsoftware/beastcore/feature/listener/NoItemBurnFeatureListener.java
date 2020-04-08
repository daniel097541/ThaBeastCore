package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class NoItemBurnFeatureListener extends AbstractFeatureListener {


    private final List<EntityDamageEvent.DamageCause> causes = new ArrayList<>();


    public NoItemBurnFeatureListener(IConfig config) {
        super(config, FeatureType.NO_ITEM_BURN);
        causes.add(EntityDamageEvent.DamageCause.LAVA);
        causes.add(EntityDamageEvent.DamageCause.FIRE);
        causes.add(EntityDamageEvent.DamageCause.FIRE_TICK);
        causes.add(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);
        causes.add(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION);
    }

    private boolean isDamageCause(EntityDamageEvent.DamageCause cause) {
        return causes.contains(cause);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemBurn(EntityDamageEvent e) {
        if (isOn() && !e.isCancelled()) {
            if (e.getEntity() instanceof Item && isDamageCause(e.getCause())) {
                e.setCancelled(true);
            }
        }
    }


}
