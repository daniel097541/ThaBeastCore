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
            loadWithoutCreating(mob.getEntityType().toString());
        }

        loadWithoutCreating("ALL");
    }

    @Override
    public void createConfig(File file, YamlConfiguration config) {
    }

}
