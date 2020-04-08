package info.beastsoftware.beastcore.configuration;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.BeastConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MainConfig extends BeastConfig {

    public MainConfig(String fileName) {
        super(fileName);
        loadConfig();
        save();
    }

    @Override
    public void createConfig() {
        if (!file.exists())
            BeastCore.getInstance().saveDefaultConfig();
        config.options().copyDefaults(true);
    }

    @Override
    public void update() {

    }


    @Override
    public void loadConfig() {
        File file = new File(BeastCore.getInstance().getDataFolder(), fileName);
        this.file = file;
        this.config = YamlConfiguration.loadConfiguration(file);
        if (!file.exists())
            createConfig();
    }
}
