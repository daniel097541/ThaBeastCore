package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.TnTFill;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.TnTFillCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class TnTFillFeature extends AbstractFeature {


    @Inject
    public TnTFillFeature(@TnTFill IConfig config,
                          BeastCore plugin,
                          IHookManager hookManager) {
        super(config, new TnTFillCommand(plugin,config), FeatureType.TNTFILL);

        if(!hookManager.isFactionsHooked())
            disableAll();

    }
}
