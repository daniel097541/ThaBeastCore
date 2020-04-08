package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.SpawnerMineFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;


@Singleton
public class SpawnerMineFeature extends AbstractFeature {


    @Inject
    public SpawnerMineFeature(@MainConfig IConfig config, IHookManager hookManager) {
        super(config,new SpawnerMineFeatureListener(config),FeatureType.SPAWNER_MINE);

        if(!hookManager.isFactionsHooked() || Bukkit.getPluginManager().getPlugin("SilkSpawners") == null){
            this.disableAll();
        }

    }
}
