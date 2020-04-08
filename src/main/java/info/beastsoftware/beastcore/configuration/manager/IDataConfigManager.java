package info.beastsoftware.beastcore.configuration.manager;

import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.struct.ConfigType;

import java.util.HashMap;

public interface IDataConfigManager {

    void addConfig(String name, IDataConfig dataConfig);

    IDataConfig getConfig(String name);

    IDataConfig getByType(ConfigType type);

    HashMap<String, IDataConfig> getConfigs();

    void init();

}
