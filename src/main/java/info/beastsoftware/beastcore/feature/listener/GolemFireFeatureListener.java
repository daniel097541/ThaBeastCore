package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.IronGolem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

public class GolemFireFeatureListener extends AbstractFeatureListener {


    public GolemFireFeatureListener(IConfig config) {
        super(config, FeatureType.GOLEM_FIRE);
    }

    @EventHandler
    public void onIrongolemSpawn(EntitySpawnEvent e) {
        if (!isOn()) return;
        if (!(e.getEntity() instanceof IronGolem)) return;
        e.getEntity().setFireTicks(1000);

    }
}
