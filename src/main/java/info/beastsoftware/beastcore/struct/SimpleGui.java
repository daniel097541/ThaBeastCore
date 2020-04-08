package info.beastsoftware.beastcore.struct;

public enum SimpleGui {

    ANTIITEMCRAFT(ConfigType.ANTIITEMCRAFT_CONFIG),
    HOPPER_FILTER(ConfigType.HOPPER_FILTER_CONFIG),
    MAIN_MENU(ConfigType.FEATURES_MENU),
    COMBAT(ConfigType.COMBAT_CONFIG),
    BLOCKS_GUI(ConfigType.COMBAT_CONFIG),
    CROP_HOPPERS(ConfigType.CROP_HOPPERS);

    private ConfigType configType;

    SimpleGui(ConfigType configType) {
        this.configType = configType;
    }

    public ConfigType getConfigType() {
        return configType;
    }
}
