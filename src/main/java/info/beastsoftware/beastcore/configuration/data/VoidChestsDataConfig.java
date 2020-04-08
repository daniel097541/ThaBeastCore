package info.beastsoftware.beastcore.configuration.data;

import info.beastsoftware.beastcore.beastutils.config.BeastDataConfig;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class VoidChestsDataConfig extends BeastDataConfig {
    public VoidChestsDataConfig(String folder) {
        super(folder);

        File file = new File(folder);

        //get all files inside the folder
        File[] files = file.listFiles();

        if (files == null) return;

        //load all files
        for (File fileInside : files) {
            String name = fileInside.getName().replace(".yml", "");
            loadConfig(name);
        }

    }

    @Override
    public void createConfig(File file, YamlConfiguration config) {

        config.createSection("Chest-Locations");
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
