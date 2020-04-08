package info.beastsoftware.beastcore.beastutils.config;

import info.beastsoftware.beastcore.beastutils.config.path.IPath;
import info.beastsoftware.beastcore.beastutils.config.path.IPathColl;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class BeastConfig implements IConfig {

    protected String fileName;
    protected IPathColl pathColl;
    protected File file;
    protected YamlConfiguration config;
    private Plugin plugin;

    public BeastConfig(String fileName, IPathColl pathColl, Plugin plugin) {
        this.fileName = fileName;
        this.pathColl = pathColl;
        this.plugin = plugin;
        //load the config
        loadConfig();

        //update the config ( for apply changes without delete the config )
        update();

        //save the config
        save();

    }

    public BeastConfig(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void createConfig() {
        for (IPath path : pathColl.getPaths())
            config.set(path.getPath(), path.getDefaultValue());
    }


    @Override
    public void loadConfig() {
        file = new File(plugin.getDataFolder(), fileName);
        config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            createConfig();
        }
    }


    @Override
    public void reload() {
        loadConfig();
    }

    @Override
    public void update() {
        for (IPath path : pathColl.getPaths())
            if (config.get(path.getPath()) == null)
                config.set(path.getPath(), path.getDefaultValue());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }

}
