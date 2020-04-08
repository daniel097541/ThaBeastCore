package info.beastsoftware.beastcore.configuration.data;

import info.beastsoftware.beastcore.beastutils.config.BeastDataConfig;
import info.beastsoftware.beastcore.struct.Mob;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MobHoppersDataConfig extends BeastDataConfig {
    public MobHoppersDataConfig(String folder) {
        super(folder);

        for (Mob mob : Mob.values()) {
            if (mob.getEntityType() == null) continue;
            loadConfig(mob.getEntityType().toString());
        }

        loadConfig("ALL");
    }

    @Override
    public void createConfig(File file, YamlConfiguration config) {
        config.createSection("Locations");
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
