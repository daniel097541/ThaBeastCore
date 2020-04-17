package info.beastsoftware.beastcore.beastutils.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public interface IDataConfig {
    void createConfig(File file, YamlConfiguration config);

    YamlConfiguration getConfigByName(String name);

    void save(String name);

    void loadConfig(String name);

    void reload();

    HashMap<String, YamlConfiguration> getConfigs();

    boolean exists(String key);

    void delete(String key);

    void deleteAll();

    void loadWithoutCreating(String key);
}
