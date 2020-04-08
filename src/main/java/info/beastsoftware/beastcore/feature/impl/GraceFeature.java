package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Grace;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.GraceCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.GraceFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;


@Singleton
public class GraceFeature extends AbstractFeature {

    @Inject
    public GraceFeature(@Grace IConfig config,
                        IHookManager hookManager,
                        BeastCore plugin) {
        super(config,new GraceFeatureListener(config),new GraceCommand(plugin,config),FeatureType.GRACE);

        if(!hookManager.isFactionsHooked()){
            this.disableAll();
        }
    }




    public boolean isOnGrace(){
        return ((GraceFeatureListener) this.getListener()).isOnGrace();
    }


    public int getGrace(){
        return ((GraceFeatureListener) this.getListener()).getTimeLeft();
    }

}
