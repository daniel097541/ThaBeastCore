package info.beastsoftware.beastcore.configuration.data;

import info.beastsoftware.beastcore.beastutils.config.BeastDataConfig;
import info.beastsoftware.beastcore.struct.Mob;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class EditDropsDataConfig extends BeastDataConfig {

    public EditDropsDataConfig(String folder) {
        super(folder);
        for (Mob mob : Mob.values()) {
            if (mob.getEntityType() == null) continue;
            loadConfig(mob.toString());
        }
    }

    @Override
    public void createConfig(File file, YamlConfiguration config) {
        config.set("Vanilla-Drops", true);
        config.set("Use-Advanced-Drops", false);

        config.createSection("Item-List");
        config.createSection("Drops.Items");

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void reload() {
        super.reload();
        for (Mob mob : Mob.values())
            loadConfig(mob.toString());
    }

}
