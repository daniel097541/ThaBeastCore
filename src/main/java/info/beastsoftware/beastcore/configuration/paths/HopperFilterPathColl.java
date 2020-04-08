package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;

import java.util.ArrayList;
import java.util.List;

public class HopperFilterPathColl extends PathColl {

    @Override
    public void init() {

        addPath("enabled", true);


        List<String> filter = new ArrayList<>();

        List<String> lore = new ArrayList<>();

        lore.add("&aEnabled");

        List<String> loreDisabled = new ArrayList<>();

        loreDisabled.add("&cDisabled");

        addPath("Hopper-Filter.menu.gui-size", 54);
        addPath("Hopper-Filter.command.permission", "btf.filter.command");
        addPath("Hopper-Filter.command.no-permission-msg", "&4(!) &cYou don´t have permission!");
        addPath("Hopper-Filter.menu.save-message", "&aFilter saved!");
        addPath(CommandType.HOPPERFILTER.getEnabledPath(), true);
        addPath("Hopper-Filter.menu.toggle-button.material", "BOOK");
        addPath("Hopper-Filter.menu.toggle-button.name", "&dEnabled");
        addPath("Hopper-Filter.menu.toggle-button.damage", "0");
        addPath("Hopper-Filter.menu.toggle-button.lore-enabled", lore);
        addPath("Hopper-Filter.menu.toggle-button.lore-disabled", loreDisabled);
        addPath("Hopper-Filter.menu.toggle-button.position", 51);

        addPath("Hopper-Filter.menu.save-button.material", "EMERALD");
        addPath("Hopper-Filter.menu.save-button.name", "&aSave filter");
        addPath("Hopper-Filter.menu.save-button.damage", "0");
        addPath("Hopper-Filter.menu.save-button.lore", filter);
        addPath("Hopper-Filter.menu.save-button.position", 52);

        addPath("Hopper-Filter.menu.close-button.material", "REDSTONE_BLOCK");
        addPath("Hopper-Filter.menu.close-button.name", "&cClose");
        addPath("Hopper-Filter.menu.close-button.damage", "0");
        addPath("Hopper-Filter.menu.close-button.lore", filter);
        addPath("Hopper-Filter.menu.close-button.position", 53);

        addPath("Hopper-Filter.menu.gui-title", "&dHopper Filter");
        addPath("Hopper-Filter.filtered-items", filter);
    }
}
