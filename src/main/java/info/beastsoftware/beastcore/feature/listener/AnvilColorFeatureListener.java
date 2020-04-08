package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;

public class AnvilColorFeatureListener extends AbstractFeatureListener {

    public AnvilColorFeatureListener(IConfig config) {
        super(config, FeatureType.ANVIL_COLOR);
    }
}
