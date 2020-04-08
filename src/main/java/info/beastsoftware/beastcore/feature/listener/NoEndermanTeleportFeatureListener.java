package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityTeleportEvent;

public class NoEndermanTeleportFeatureListener extends AbstractFeatureListener {
    public NoEndermanTeleportFeatureListener(IConfig config) {
        super(config, FeatureType.NO_ENDERMAN_TELEPORT);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEndermanTeleport(EntityTeleportEvent event) {
        if (this.isOn()) {
            EntityType type = event.getEntityType();
            if (type.equals(EntityType.ENDERMAN)) {
                event.setCancelled(true);
            }
        }
    }
}
