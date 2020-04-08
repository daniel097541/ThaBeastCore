package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.AntiItemCraft;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.AntiItemCraftCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.AntiItemCraftFeatureListener;
import info.beastsoftware.beastcore.gui.AntiItemCraftGui;
import info.beastsoftware.beastcore.gui.ISimpleGui;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class AntiItemCraftFeature extends AbstractFeature {

    private final ISimpleGui gui;


    @Inject
    protected AntiItemCraftFeature(@AntiItemCraft IConfig config,
                                   BeastCore plugin) {
        super(config, new AntiItemCraftFeatureListener(config), new AntiItemCraftCommand(plugin,config, CommandType.ANTIITEMCRAFT), FeatureType.ANTI_ITEM_CRAFT);
        this.gui = new AntiItemCraftGui(config);
    }
}
