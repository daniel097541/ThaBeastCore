package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.FPS;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.command.FpsBoosterCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.FPSBoosterFeatureListener;
import info.beastsoftware.beastcore.gui.FpsBoosterGUI;
import info.beastsoftware.beastcore.gui.IComplexGui;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;


@Singleton
public class FPSFeature extends AbstractFeature {

    private final IComplexGui gui;


    @Inject
    public FPSFeature(@FPS IConfig config,
                      @FPS IDataConfig dataConfig,
                      BeastCore plugin,
                      IHookManager hookManager) {
        super(config,new FPSBoosterFeatureListener(config,dataConfig,hookManager),new FpsBoosterCommand(plugin,config), FeatureType.FPS);
        this.gui = new FpsBoosterGUI(config,dataConfig);

        if(!hookManager.isProtocolLibHooked()){
            this.disableAll();
            Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &c PROTOCOLLIB IS NOT INSTALLED IN THE SERVER, FPS FEATURE WONT WORK!!!"));
        }

    }
}
