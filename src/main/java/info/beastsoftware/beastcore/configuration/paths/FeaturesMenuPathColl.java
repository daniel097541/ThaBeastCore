package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class FeaturesMenuPathColl extends PathColl {
    @Override
    public void init() {

        addPath("enabled", true);


        String path = "Features-Menu.";
        String enabledbuttonpath = path + "Buttons.Enabled.";
        String disabledbuttonpath = path + "Buttons.Disabled.";

        List<String> enabledLore = new ArrayList<>();
        enabledLore.add("&aEnabled");

        List<String> disabled = new ArrayList<>();
        disabled.add("&cDisabled");

        addPath(path + "title", "&dMain Features menu");
        addPath(path + "size", 54);
        addPath(enabledbuttonpath + "material", "EMERALD");
        addPath(disabledbuttonpath + "material", "REDSTONE_BLOCK");
        addPath(enabledbuttonpath + "lore", enabledLore);
        addPath(disabledbuttonpath + "lore", disabled);
        addPath(enabledbuttonpath + "damage", "0");
        addPath(disabledbuttonpath + "damage", "0");

        int cont = 0;
        for (FeatureType featureType : FeatureType.values()) {
            addPath(path + "Features-List." + featureType.toString() + ".name", "&d" + featureType.toString());
            addPath(path + "Features-List." + featureType.toString() + ".position-in-gui", cont);
            cont++;
        }

        String nextButtonPath = path + "Buttons.Next-Page-Button.";
        addPath(nextButtonPath + "material", Material.ARROW.toString());
        addPath(nextButtonPath + "name", "&eNext page");
        addPath(nextButtonPath + "damage", 0);
        addPath(nextButtonPath + "lore", new ArrayList<>());
        addPath(nextButtonPath + "position", 53);


        String previous = path + "Buttons.Previous-Page-Button.";
        addPath(previous + "material", Material.ARROW.toString());
        addPath(previous + "name", "&ePrevious page");
        addPath(previous + "damage", 0);
        addPath(previous + "lore", new ArrayList<>());
        addPath(previous + "position", 45);
    }
}
