package info.beastsoftware.beastcore.feature;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.struct.FeatureType;

public interface Checkable {
    IConfig getConfig();
    FeatureType getFeatureType();
    default boolean isOn(){
        return this.getConfig()
                .getConfig()
                .getBoolean(this.getFeatureType().getPath());
    }
}
