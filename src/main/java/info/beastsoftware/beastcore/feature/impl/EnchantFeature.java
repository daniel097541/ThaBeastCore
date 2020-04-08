package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.Enchant;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.EnchantmentsFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class EnchantFeature extends AbstractFeature {

    @Inject
    public EnchantFeature(@Enchant IConfig config) {
        super(config, new EnchantmentsFeatureListener(config), FeatureType.ENCHANTMENTS);
    }
}
