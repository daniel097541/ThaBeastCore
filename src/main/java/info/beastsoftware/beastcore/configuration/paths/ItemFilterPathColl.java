package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemFilterPathColl extends PathColl {
    @Override
    public void init() {

        addPath("enabled", true);


        String commandPath = "Item-Filter.Command.";
        addPath(commandPath + "permission", "btf.itemfilter.command");
        addPath(commandPath + "no-permission-message", "&cYou dont have permission!");


        String guiPath = "Item-Filter.GUI.";

        addPath(guiPath + "page-size", 54);
        addPath(guiPath + "page-title", "&eItem filter page &d{page}");
        addPath(guiPath + "fill-with-spacers", true);
        String buttonsPath = guiPath + "Buttons.";

        String previousPageButtonPath = buttonsPath + "previous-page.";
        String nextPageButtonPath = buttonsPath + "next-page.";

        addPath(previousPageButtonPath + "material", Material.ARROW.toString());
        addPath(previousPageButtonPath + "name", "&ePrevious page");
        addPath(previousPageButtonPath + "damage", 0);
        addPath(previousPageButtonPath + "position", 45);

        addPath(nextPageButtonPath + "material", Material.ARROW.toString());
        addPath(nextPageButtonPath + "name", "&eNext page");
        addPath(nextPageButtonPath + "damage", 0);
        addPath(nextPageButtonPath + "position", 53);

        String materialPath = buttonsPath + "materials-format.";

        addPath(materialPath + "name", "&e{material_name}");

        List<String> lore = new ArrayList<>();
        lore.add("&eClick to toggle this item pickup!");
        lore.add("&7Status: &aEnabled");
        addPath(materialPath + "lore-enabled", lore);

        List<String> loreD = new ArrayList<>();
        loreD.add("&eClick to toggle this item pickup!");
        loreD.add("&7Status: &cDisabled");
        addPath(materialPath + "lore-disabled", loreD);

        String spacer = buttonsPath + "Spacer-Button.";

        addPath(spacer + "material", Material.GLASS.toString());
        addPath(spacer + "damage", 0);


    }
}
