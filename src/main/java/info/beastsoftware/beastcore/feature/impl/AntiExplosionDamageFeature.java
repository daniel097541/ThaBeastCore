package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.AntiExplosionDamageListener;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class AntiExplosionDamageFeature extends AbstractFeature {


    @Inject
    protected AntiExplosionDamageFeature(
            @MainConfig IConfig config) {
        super(config, new AntiExplosionDamageListener(config, FeatureType.ANTI_EXPLOSION_DAMAGE), FeatureType.ANTI_EXPLOSION_DAMAGE);
    }




}
