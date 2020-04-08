package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;

import java.util.ArrayList;
import java.util.List;

public class AntiItemCraftPathColl extends PathColl {

    public AntiItemCraftPathColl() {
        super();
    }

    @Override
    public void init() {
        List<String> filter = new ArrayList<>();

        List<String> loreEnabled = new ArrayList<>();
        loreEnabled.add("&eEnabled");

        List<String> loreDisabled = new ArrayList<>();
        loreDisabled.add("&cDisabled");

        addPath("Anti-Item-Craft.message", "&cYou cant craft &e{item}");

        addPath("Anti-Item-Craft.enabled", true);
        addPath(CommandType.ANTIITEMCRAFT.getEnabledPath(), true);

        addPath("Anti-Item-Craft.command.permission", "btf.antiitemcraft.command");
        addPath("Anti-Item-Craft.command.no-permission-msg", "&4(!) &cYou don´t have permission!");
        addPath("Anti-Item-Craft.menu.save-message", "&aFilter saved!");

        addPath("Anti-Item-Craft.menu.toggle-button.material", "BOOK");
        addPath("Anti-Item-Craft.menu.toggle-button.name", "&dEnabled");
        addPath("Anti-Item-Craft.menu.toggle-button.damage", "0");
        addPath("Anti-Item-Craft.menu.toggle-button.lore-enabled", loreEnabled);
        addPath("Anti-Item-Craft.menu.toggle-button.lore-disabled", loreDisabled);
        addPath("Anti-Item-Craft.menu.toggle-button.position", 51);


        addPath("Anti-Item-Craft.menu.save-button.material", "EMERALD");
        addPath("Anti-Item-Craft.menu.save-button.name", "&aSave filter");
        addPath("Anti-Item-Craft.menu.save-button.damage", "0");
        addPath("Anti-Item-Craft.menu.save-button.lore", filter);
        addPath("Anti-Item-Craft.menu.save-button.position", 52);

        addPath("Anti-Item-Craft.menu.close-button.material", "REDSTONE_BLOCK");
        addPath("Anti-Item-Craft.menu.close-button.name", "&CClose");
        addPath("Anti-Item-Craft.menu.close-button.damage", "0");
        addPath("Anti-Item-Craft.menu.close-button.lore", filter);
        addPath("Anti-Item-Craft.menu.close-button.position", 53);

        addPath("Anti-Item-Craft.menu.gui-title", "&dAnti Item Craft Filter");
        addPath("Anti-Item-Craft.menu.gui-size", 54);


        addPath("Anti-Item-Craft.filtered-items", new ArrayList<>());
        addPath("Anti-Item-Craft.filtered-items", filter);


    }
}
