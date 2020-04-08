package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.NoEnemyTeleport;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.NoEnemyTeleportFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class NoEnemyTeleportFeature extends AbstractFeature {

    @Inject
    public NoEnemyTeleportFeature(@NoEnemyTeleport IConfig config, IHookManager hookManager) {
        super(config, new NoEnemyTeleportFeatureListener(config), FeatureType.NO_ENEMY_TELEPORT);
        if (!hookManager.isFactionsHooked()) {
            this.disableAll();
        }
    }
}
