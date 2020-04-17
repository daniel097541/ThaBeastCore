package info.beastsoftware.beastcore.beastutils.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public abstract class BeastDataConfig implements IDataConfig {

    private HashMap<String, YamlConfiguration> configs;
    private HashMap<String, File> files;
    private String folder;

    public BeastDataConfig(String folder) {
        this.folder = folder;
        configs = new HashMap<>();
        files = new HashMap<>();
    }

    @Override
    public void loadWithoutCreating(String name) {
        File file = new File(folder, name + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists())
            return;
        configs.put(name, config);
        files.put(name, file);
    }

    @Override
    public void loadConfig(String name) {
        File file = new File(folder, name + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists())
            createConfig(file, config);
        configs.put(name, config);
        files.put(name, file);
    }

    @Override
    public void save(String name) {
        try {
            configs.get(name).save(files.get(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reload() {
        configs = new HashMap<>();
        files = new HashMap<>();
    }

    @Override
    public YamlConfiguration getConfigByName(String name) {
        return configs.get(name);
    }


    @Override
    public HashMap<String, YamlConfiguration> getConfigs() {
        return configs;
    }


    @Override
    public boolean exists(String key) {
        return getConfigs().keySet().contains(key);
    }

    @Override
    public void delete(String key) {
        this.getConfigs().remove(key);
        this.files.get(key).delete();
        this.files.remove(key);
    }

    @Override
    public void deleteAll() {
        this.configs = new HashMap<>();
        this.files.forEach((s, f) -> f.delete());
        this.files = new HashMap<>();
    }
}
