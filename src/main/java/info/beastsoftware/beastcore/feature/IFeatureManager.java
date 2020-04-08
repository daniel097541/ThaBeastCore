package info.beastsoftware.beastcore.feature;


import info.beastsoftware.beastcore.struct.FeatureType;

import java.util.List;

public interface IFeatureManager {

    List<IBeastFeature> getFeatures();

    default IBeastFeature getFeatureByType(FeatureType type){
        return getFeatures()
                .parallelStream()
                .filter(f -> f.getFeatureType().equals(type))
                .findAny()
                .orElse(null);
    }


    default void reloadAllFeatures(){
        getFeatures()
                .parallelStream()
                .forEach(IBeastFeature::reload);
    }


}
