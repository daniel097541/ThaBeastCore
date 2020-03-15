package info.beastsoftware.core.features.listener.impl;

import info.beastsoftware.core.features.listener.FeatureListener;

public class FeatureListenerImpl implements FeatureListener {

    private boolean enabled;

    public FeatureListenerImpl(boolean enabled) {
        this.enabled = enabled;
        if(this.enabled) {
            this.register();
        }
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
