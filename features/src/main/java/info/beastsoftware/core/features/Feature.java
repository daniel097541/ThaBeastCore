package info.beastsoftware.core.features;

import info.beastsoftware.core.common.Registrable;
import info.beastsoftware.core.common.Toggable;
import info.beastsoftware.core.features.listener.FeatureListener;

import java.util.Objects;

public interface Feature extends Registrable, Toggable {


    FeatureListener getListener();


    @Override
    default void register(){
        FeatureListener listener = this.getListener();
        if(Objects.nonNull(listener)){
            listener.register();
        }
    }

    @Override
    default void unRegister(){
        FeatureListener listener = this.getListener();
        if(Objects.nonNull(listener)){
            listener.unRegister();
        }
    }
}
