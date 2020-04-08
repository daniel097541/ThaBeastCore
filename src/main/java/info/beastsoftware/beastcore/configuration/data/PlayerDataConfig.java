package info.beastsoftware.beastcore.configuration.data;


import info.beastsoftware.beastcore.beastutils.config.BeastDataConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class PlayerDataConfig extends BeastDataConfig {


    public PlayerDataConfig(String folder) {
        super(folder);
    }

    @Override
    public void createConfig(File file, YamlConfiguration config) {

        try {
            config.save(file);
        } catch (Exception ignored) {

        }

    }
}
