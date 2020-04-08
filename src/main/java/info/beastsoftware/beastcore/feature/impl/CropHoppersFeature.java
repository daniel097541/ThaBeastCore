package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.configs.CropHoppersData;
import info.beastsoftware.beastcore.annotations.features.CropHoppers;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.CropHopperCommand;
import info.beastsoftware.beastcore.command.HopperConvertCommand;
import info.beastsoftware.beastcore.command.IBeastCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.CropHoppersFeatureListener;
import info.beastsoftware.beastcore.gui.CropHoppersGui;
import info.beastsoftware.beastcore.gui.ISimpleGui;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class CropHoppersFeature extends AbstractFeature {

    private final ISimpleGui gui;

    private final IBeastCommand convertHopperCommand;

    @Inject
    protected CropHoppersFeature(@CropHoppers IConfig config,
                                 @CropHoppersData IConfig dataConfig,
                                 IHookManager hookManager,
                                 BeastCore plugin) {
        super(config, new CropHoppersFeatureListener(config, dataConfig), new CropHopperCommand(plugin,config), FeatureType.CROP_HOPPERS);
        this.gui = new CropHoppersGui(config);
        this.convertHopperCommand = new HopperConvertCommand(plugin, config, hookManager.getEconomyHook());
        if(this.isOn() && hookManager.isEconomyHooked()){
            convertHopperCommand.autoRegister();
        }
    }
}
