package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.LootProtect;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.LootProtectionFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class LootProtectFeature extends AbstractFeature {


    @Inject
    public LootProtectFeature(@LootProtect IConfig config) {
        super(config, new LootProtectionFeatureListener(config), FeatureType.LOOT_PROTECTION);
    }


}
