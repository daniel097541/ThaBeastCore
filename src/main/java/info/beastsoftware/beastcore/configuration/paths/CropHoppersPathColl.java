package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;

import java.util.ArrayList;
import java.util.List;

public class CropHoppersPathColl extends PathColl {

    public CropHoppersPathColl() {
        super();
    }

    @Override
    public void init() {

        addPath("enabled", true);


        String commandPath = "Crop-Hoppers.command.";
        String itemPath = "Crop-Hoppers.Item.";
        String settingsPath = "Crop-Hoppers.Settings.";
        String menuPath = "Crop-Hoppers.Menu.";

        addPath("Crop-Hoppers.disabled-message", "&cCrop Hoppers are disabled!");
        addPath(CommandType.CROP_HOPPERS.getEnabledPath(), true);

        addPath(commandPath + "permission", "bfc.crophoppers.give");
        addPath(commandPath + "no-permission-msg", "&C(!) You dont have permission!");
        addPath(commandPath + "player-not-online", "&cThe player is not online!");
        addPath(commandPath + "no-slot", "&cThe player has not a disponible slot!");
        addPath(commandPath + "give-message", "&eYou gave &d{player} &e{number} &7crop hopper/s!");
        addPath(commandPath + "recieve-message", "&eYou recieved &d{number} &ecrop hopper/s!");

        List<String> formats = new ArrayList<>();

        formats.add("&dUssage: ");
        formats.add("&7- &6/crophoppers give <player> <number>  &dGive a crophopper!");
        formats.add("&7- &6/crophoppers menu  &dFilter what items will crophoppers recolect!");

        addPath(commandPath + "formats", formats);

        List<String> lore = new ArrayList<>();
        lore.add("&ePlace this hopper");
        lore.add("&eand collect all crops!");
        addPath(itemPath + "name", "&dCrop hopper!");
        addPath(itemPath + "lore", lore);

        addPath(settingsPath + "factions-mode", false);
        addPath(settingsPath + "collect-radius-in-chunks", 2);
        addPath(settingsPath + "place-permission", "bfc.crophoppers.place");
        addPath(settingsPath + "break-permission", "bfc.crophoppers.break");

        List<String> lores = new ArrayList<>();
        List<String> loreEnabled = new ArrayList<>();
        loreEnabled.add("&aEnabled");
        List<String> loreDisabled = new ArrayList<>();
        loreDisabled.add("&cDisabled");

        addPath(menuPath + "title", "&dCrop hoppers");
        addPath(menuPath + "size", 54);


        addPath(menuPath + "save-button.material", "EMERALD");
        addPath(menuPath + "save-button.name", "&aSave");
        addPath(menuPath + "save-button.lore", lores);
        addPath(menuPath + "save-button.damage", "0");
        addPath(menuPath + "save-button.position", 51);

        addPath(menuPath + "close-button.material", "REDSTONE_BLOCK");
        addPath(menuPath + "close-button.name", "&cClose");
        addPath(menuPath + "close-button.lore", lores);
        addPath(menuPath + "close-button.damage", "0");
        addPath(menuPath + "close-button.position", 52);


        addPath(menuPath + "toggle-button.material", "BOOK");
        addPath(menuPath + "toggle-button.name", "&eToggle");
        addPath(menuPath + "toggle-button.lore-enabled", loreEnabled);
        addPath(menuPath + "toggle-button.lore-disabled", loreDisabled);
        addPath(menuPath + "toggle-button.damage", "0");
        addPath(menuPath + "toggle-button.position", 53);


        addPath("Crop-Hoppers.save-message", "&aSaved!");
        List<String> list = new ArrayList<>();
        addPath("Crop-Hoppers.filtered-items", list);


        addPath("Crop-Hoppers.Convert-Command.enabled", true);
        addPath("Crop-Hoppers.Convert-Command.permission", "btf.crophopper.convert");
        addPath("Crop-Hoppers.Convert-Command.no-permission-message", "&cYou dont have permission!");
        addPath("Crop-Hoppers.Convert-Command.not-holding-a-hopper", "&cYou dont have a hopper in your hand!");
        addPath("Crop-Hoppers.Convert-Command.price-per-hopper", 10000);
        addPath("Crop-Hoppers.Convert-Command.not-enough-money", "&cYou dont have enough money!");
        addPath("Crop-Hoppers.Convert-Command.converted", "&aYou converted &e{amount} &ahopper/s into crophopper/s!");


    }
}
