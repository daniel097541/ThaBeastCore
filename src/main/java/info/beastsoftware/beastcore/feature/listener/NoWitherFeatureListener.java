package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

public class NoWitherFeatureListener extends AbstractFeatureListener {

    public NoWitherFeatureListener(IConfig config ) {
        super(config, FeatureType.NO_WITHER);
    }


    @EventHandler
    public void onWItherSpawn(EntitySpawnEvent event){

        if(!isOn() || event.isCancelled()) return;


        EntityType entityType = event.getEntityType();

        if(entityType.toString().toUpperCase().contains("WITHER"))
            event.setCancelled(true);


    }


}
