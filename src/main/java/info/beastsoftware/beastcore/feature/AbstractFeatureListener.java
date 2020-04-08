package info.beastsoftware.beastcore.feature;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.StringUtils;

public abstract class AbstractFeatureListener implements IFeatureListener {


    protected final IConfig config;
    protected final IDataConfig dataConfig;
    protected final FeatureType featureType;
    protected final StringUtils stringUtil = new StringUtils();

    public AbstractFeatureListener(IConfig config, IDataConfig dataConfig, FeatureType featureType) {
        this.config = config;
        this.dataConfig = dataConfig;
        this.featureType = featureType;
    }


    public AbstractFeatureListener(IConfig config, FeatureType featureType) {
        this.config = config;
        this.featureType = featureType;
        this.dataConfig = null;
    }


    @Override
    public IConfig getConfig() {
        return this.config;
    }

    @Override
    public FeatureType getFeatureType() {
        return this.featureType;
    }

}
