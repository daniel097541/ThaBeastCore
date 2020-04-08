package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.NoEnemyHomes;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.NoEnemyHomeListener;
import info.beastsoftware.beastcore.manager.HomesManager;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class NoEnemyHomesFeature extends AbstractFeature {

    @Inject
    public NoEnemyHomesFeature(@NoEnemyHomes IConfig config, HomesManager homesManager, IHookManager hookManager) {
        super(config, new NoEnemyHomeListener(config, homesManager), FeatureType.NO_ENEMY_HOMES);

        if(!hookManager.isEssentialsHooked()){
            this.disableAll();
        }

    }




}
