package info.beastsoftware.beastcore.configuration.manager;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.struct.ConfigType;

import java.util.HashMap;

public interface IConfigManager {

    IConfig getConfig(String name);

    void init();

    void addConfig(String name, IConfig config);

    void reloadConfigs();

    void reloadConfig(String name);

    HashMap<String, IConfig> getConfigs();

    IConfig getByType(ConfigType configType);

}
