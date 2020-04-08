package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;

import java.util.ArrayList;
import java.util.List;

public class EditDropsPathColl extends PathColl {
    @Override
    public void init() {
        List<String> lore = new ArrayList<>();
        List<String> loreEnabled = new ArrayList<>();
        loreEnabled.add("&aEnabled");

        List<String> loreDisabled = new ArrayList<>();
        loreDisabled.add("&cDisabled");


        addPath("Edit-Drops.enabled", true);

        addPath("Gui-Menu.menu-title", "&dDrop editing!");
        addPath(CommandType.DROPS.getEnabledPath(), true);
        addPath("Gui-Menu.back-button.name", "&eBack");
        addPath("Gui-Menu.back-button.position", 53);
        addPath("Gui-Menu.back-button.material", "ARROW");
        addPath("Gui-Menu.back-button.damage", "0");
        addPath("Gui-Menu.back-button.lore", lore);

        addPath("Gui-Menu.save-button.name", "&aSave");
        addPath("Gui-Menu.save-button.position", 52);
        addPath("Gui-Menu.save-button.material", "BOOK");
        addPath("Gui-Menu.save-button.damage", "0");
        addPath("Gui-Menu.save-button.lore", lore);

        addPath("Gui-Menu.close-button.name", "&cClose");
        addPath("Gui-Menu.close-button.position", 53);
        addPath("Gui-Menu.close-button.material", "REDSTONE_BLOCK");
        addPath("Gui-Menu.close-button.damage", "0");
        addPath("Gui-Menu.close-button.lore", lore);

        addPath("Gui-Menu.vanilla-drops-button.name", "&dVanilla Drops");
        addPath("Gui-Menu.vanilla-drops-button.position", 52);
        addPath("Gui-Menu.vanilla-drops-button.material", "BOOK");
        addPath("Gui-Menu.vanilla-drops-button.damage", "0");
        addPath("Gui-Menu.vanilla-drops-button.lore-enabled", loreEnabled);
        addPath("Gui-Menu.vanilla-drops-button.lore-disabled", loreDisabled);


        //UPDATE
        addPath("Gui-Menu.advanced-drops-button.name", "&eAdvanced Drops");
        addPath("Gui-Menu.advanced-drops-button.position", 51);
        addPath("Gui-Menu.advanced-drops-button.material", "PAPER");
        addPath("Gui-Menu.advanced-drops-button.damage", "0");
        addPath("Gui-Menu.advanced-drops-button.lore-enabled", loreEnabled);
        addPath("Gui-Menu.advanced-drops-button.lore-disabled", loreDisabled);

        List<String> loreOpen = new ArrayList<>();
        loreOpen.add("&eOpen advanced drops menu");

        addPath("Gui-Menu.advanced-drops-button-open.name", "&eAdvanced Drops Menu");
        addPath("Gui-Menu.advanced-drops-button-open.position", 50);
        addPath("Gui-Menu.advanced-drops-button-open.material", "GOLD_NUGGET");
        addPath("Gui-Menu.advanced-drops-button-open.damage", "0");
        addPath("Gui-Menu.advanced-drops-button-open.lore", loreOpen);

        List<String> loreInfo = new ArrayList<>();
        loreInfo.add("&aLeft click &e---> &fIncrease drop chance by: &a{chance_increase} %");
        loreInfo.add("&cRight click &e---> &fDecrease drop chance by: &c{chance_decrease} %");
        loreInfo.add("&aShift + Click &e---> &fRemove the item from the drops list");

        addPath("Gui-Menu.info-drops-button.name", "&eInfo");
        addPath("Gui-Menu.info-drops-button.position", 52);
        addPath("Gui-Menu.info-drops-button.material", "GOLD_NUGGET");
        addPath("Gui-Menu.info-drops-button.damage", "0");
        addPath("Gui-Menu.info-drops-button.lore", loreInfo);

        addPath("Gui-Menu.Advanced-Drops.chance-lore-line", "&eChance: &a{chance}&e %");
        addPath("Gui-Menu.Advanced-Drops.chance-increase-by-click", 10);
        addPath("Gui-Menu.Advanced-Drops.chance-decrease-by-click", 10);


        addPath("command.no-permission", "&4(!) &cYou dont have permission!");
        addPath("command.permission", "btf.drops-editor.command");
        addPath("Gui-Menu.drops-saved-message", "&aDrops succesfully saved!");
    }
}
