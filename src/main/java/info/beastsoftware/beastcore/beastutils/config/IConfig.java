package info.beastsoftware.beastcore.beastutils.config;

import org.bukkit.configuration.file.YamlConfiguration;

public interface IConfig {

    void save();

    void createConfig();

    void loadConfig();

    void reload();

    void update();

    YamlConfiguration getConfig();
}
