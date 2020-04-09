package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class NoBabyZombiesFeatureListener extends AbstractFeatureListener {
    public NoBabyZombiesFeatureListener(IConfig config) {
        super(config, FeatureType.NO_BABY_ZOMBIES);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onBabyZombieSpawn(CreatureSpawnEvent event) {
        if (this.isOn()) {
            Entity entity = event.getEntity();
            if (entity instanceof Zombie) {
                Zombie zombie = (Zombie) entity;
                if (zombie.isBaby()) {
                    zombie.setBaby(false);
                }
            }
        }
    }


}
