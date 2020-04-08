package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.Material;

public class WorthPathColl extends PathColl {

    public WorthPathColl() {
        super();
    }

    @Override
    public void init() {
        for (Material material : Material.values()) {
            if (material.equals(Material.AIR)) continue;
            addPath("Item-Worths." + material.toString() + ".damage", 0);
            addPath("Item-Worths." + material.toString() + ".price", 10);
        }
    }
}
