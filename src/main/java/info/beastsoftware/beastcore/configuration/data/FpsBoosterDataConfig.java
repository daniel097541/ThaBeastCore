package info.beastsoftware.beastcore.configuration.data;

import info.beastsoftware.beastcore.beastutils.config.BeastDataConfig;
import info.beastsoftware.beastcore.struct.ButtonType;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;


public class FpsBoosterDataConfig extends BeastDataConfig {
    public FpsBoosterDataConfig(String folder) {
        super(folder);
    }

    @Override
    public void createConfig(File file, YamlConfiguration config) {
        config.set(ButtonType.DROPS_TOGGLE.toString(), false);
        config.set(ButtonType.MOBS_TOGGLE.toString(), false);
        config.set(ButtonType.SPAWNERS_TOGGLE.toString(), false);
        config.set(ButtonType.EXPLOSIONS_TOGGLE.toString(), false);
        config.set(ButtonType.TNT_TOGGLE.toString(), false);
        config.set(ButtonType.PARTICLES_TOGGLE.toString(), false);
        config.set(ButtonType.SAND_TOGGLE.toString(), false);
    }

}
