package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;

public class FPSBoosterFeatureListener extends AbstractFeatureListener {


    private final IHookManager hookManager;
    private IPacketsListener packetsListener;

    public FPSBoosterFeatureListener(IConfig config,
                                     IDataConfig dataConfig,
                                     IHookManager hookManager) {
        super(config, dataConfig, FeatureType.FPS);
        this.hookManager = hookManager;

        if(hookManager.isProtocolLibHooked()) {
            this.packetsListener = new PacketsListener(hookManager,dataConfig,config);
        }

    }



}
