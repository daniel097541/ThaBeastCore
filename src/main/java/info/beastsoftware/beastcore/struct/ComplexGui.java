package info.beastsoftware.beastcore.struct;

public enum ComplexGui {
    ALERTS(ConfigType.ALERTS_CONFIG),
    FPS_BOOSTER(ConfigType.FPS_BOOSTER),
    DROPS(ConfigType.DROPS_CONFIG);


    private ConfigType configType;

    ComplexGui(ConfigType configType) {
        this.configType = configType;
    }

    public ConfigType getConfigType() {
        return configType;
    }


}
