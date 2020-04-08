package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.MainMenu;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.MainGuiOpenEvent;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.gui.controller.MainMenuController;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class MainMenuFeature extends AbstractFeature {

    private final MainMenuController mainMenuController;

    @Inject
    public MainMenuFeature(@MainMenu IConfig config, BeastCore plugin) {
        super(config, FeatureType.MAIN_MENU);
        this.mainMenuController = new MainMenuController(config, MainGuiOpenEvent.class, plugin);
    }
}
