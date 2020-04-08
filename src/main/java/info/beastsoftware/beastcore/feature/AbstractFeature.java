package info.beastsoftware.beastcore.feature;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.command.IBeastCommand;
import info.beastsoftware.beastcore.struct.FeatureType;

public abstract class AbstractFeature implements IBeastFeature {


    protected final IConfig config;
    protected final IFeatureListener listener;
    protected final IBeastCommand command;
    protected final IDataConfig dataConfig;
    private final FeatureType featureType;


    public AbstractFeature(IConfig config, FeatureType featureType) {
        this.config = config;
        this.featureType = featureType;
        listener = null;
        command = null;
        dataConfig = null;
    }

    public AbstractFeature(IConfig config, IBeastCommand command, FeatureType featureType) {
        this.config = config;
        this.command = command;
        this.featureType = featureType;
        if(isOn())
            this.enableAll();
        else
            broadcastUnRegistered();
        listener = null;
        dataConfig = null;
    }

    protected AbstractFeature(IConfig config, IFeatureListener listener, FeatureType featureType) {
        this.config = config;
        this.listener = listener;
        this.featureType = featureType;
        if(isOn())
            this.enableAll();
        else
            broadcastUnRegistered();
        command = null;
        dataConfig = null;
    }

    public AbstractFeature(IConfig config, IFeatureListener listener, IBeastCommand command, FeatureType featureType) {
        this.config = config;
        this.listener = listener;
        this.command = command;
        this.featureType = featureType;
        if(isOn())
            this.enableAll();
        else
            broadcastUnRegistered();
        dataConfig = null;
    }

    public AbstractFeature(IConfig config, IFeatureListener listener, IBeastCommand command, IDataConfig dataConfig, FeatureType featureType) {
        this.config = config;
        this.listener = listener;
        this.command = command;
        this.dataConfig = dataConfig;
        this.featureType = featureType;
        if(isOn())
            this.enableAll();
        else
            broadcastUnRegistered();
    }


    @Override
    public IDataConfig getDataConfig() {
        return this.dataConfig;
    }

    @Override
    public IConfig getConfig() {
        return this.config;
    }

    @Override
    public FeatureType getFeatureType() {
        return this.featureType;
    }


    @Override
    public IFeatureListener getListener() {
        return listener;
    }

    @Override
    public IBeastCommand getCommand() {
        return command;
    }

}
