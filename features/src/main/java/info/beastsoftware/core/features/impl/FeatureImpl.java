package info.beastsoftware.core.features.impl;

import info.beastsoftware.core.features.Feature;
import info.beastsoftware.core.features.listener.FeatureListener;

public class FeatureImpl implements Feature {

    private FeatureListener listener;
    private boolean enabled;

    public FeatureImpl(FeatureListener listener, boolean enabled) {
        this.listener = listener;
        this.enabled = enabled;
        if(this.enabled){
            this.register();
        }
    }

    @Override
    public FeatureListener getListener() {
        return this.listener;
    }

    @Override
    public void enable() {
        this.enabled = true;
        this.register();
    }

    @Override
    public void disable() {
        this.enabled = false;
        this.unRegister();
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
