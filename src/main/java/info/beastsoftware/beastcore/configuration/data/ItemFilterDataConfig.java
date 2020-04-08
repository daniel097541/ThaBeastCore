package info.beastsoftware.beastcore.configuration.data;

import info.beastsoftware.beastcore.beastutils.config.BeastDataConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ItemFilterDataConfig extends BeastDataConfig {
    public ItemFilterDataConfig(String folder) {
        super(folder);
    }

    @Override
    public void createConfig(File file, YamlConfiguration config) {
        for (Material material : Material.values()) {
            if (material.equals(Material.AIR)) continue;
            if (!IInventoryUtil.materialCanBeDisplayed(material)) continue;
            config.set("Filtered-Materials." + material.toString() + ".pickup-enabled", true);
        }
        try {
            config.save(file);
        } catch (IOException ignored) {
        }
    }
}
