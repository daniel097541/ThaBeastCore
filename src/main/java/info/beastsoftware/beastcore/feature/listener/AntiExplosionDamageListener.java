package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.path.ExplosionsDamagePath;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class AntiExplosionDamageListener extends AbstractFeatureListener {
    public AntiExplosionDamageListener(IConfig config, FeatureType featureType) {
        super(config, featureType);
    }

    @EventHandler
    public void onExplosionDamage(EntityDamageEvent e) {
        if (this.isOn() && e.getEntity() instanceof Player) {
            if (isExplosion(e.getCause()) && e.getEntity().hasPermission(config.getConfig().getString(ExplosionsDamagePath.NO_EXPLOSIONS_DAMAGE_PERMISSION.getPath())))
                e.setCancelled(true);
        }
    }

    private boolean isExplosion(EntityDamageEvent.DamageCause cause) {
        return cause.equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) || cause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION);
    }


}
