package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.NoEndermanTeleport;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.NoEndermanTeleportFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class NoEndermanTeleportFeature extends AbstractFeature {


    @Inject
    public NoEndermanTeleportFeature(@NoEndermanTeleport IConfig config) {
        super(config, new NoEndermanTeleportFeatureListener(config), FeatureType.NO_ENDERMAN_TELEPORT);
    }
}
