package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;

import java.util.ArrayList;
import java.util.List;

public class VoidChestPathColl extends PathColl {

    public VoidChestPathColl() {
        super();
    }

    @Override
    public void init() {


        addPath("enabled", true);

        String commandPath = "Void-Chests.command.";

        String lifetimePath = "Void-Chests.Life-Time.";

        addPath("Void-Chests.use-essentials-worth", false);
        addPath("Void-Chests.use-shop-gui-plus-worth", false);
        addPath("Void-Chests.disabled-message", "&c(!) &4Void chests are disabled!");
        addPath("Void-Chests.use-default-sell-system-if-others-fail", false);

        addPath(CommandType.VOID_CHESTS.getEnabledPath(), true);

        addPath(commandPath + "permission", "btf.voidchest.give");
        addPath(commandPath + "no-permission-msg", "&c(!) &4You dont have permission!");
        addPath(commandPath + "offline-player", "&cPlayer is offline!");
        addPath(commandPath + "inventory-full", "&cPlayers inventory has not an empty slot!");
        addPath(commandPath + "give-message", "&eYou gave &c{player} &a{number} &evoid chest/s!");
        addPath(commandPath + "recieve-message", "&eYou recieved &c{number} &evoid chest/s!");

        List<String> formats = new ArrayList<>();
        formats.add("&7- &e/voidchest give <player> <number>");
        addPath(commandPath + "formats", formats);

        String itemsPath = "Void-Chests.Item.";

        List<String> lore = new ArrayList<>();

        lore.add("&eAutomatically sell items inside");


        addPath(itemsPath + "name", "&cVoid &7Chest");
        addPath(itemsPath + "lore", lore);

        String settingsPath = "Void-Chests.Settings.";
        addPath(settingsPath + "break-permission", "bfc.voidchest.break");
        addPath(settingsPath + "no-break-permission-msg", "&c(!) &4You dont have permission to break void chests!");
        addPath(settingsPath + "place-permission", "bfc.voidchest.place");
        addPath(settingsPath + "no-place-permission-msg", "&c(!) &4You dont have permission to place void chests!");
        addPath(settingsPath + "sell-time", 30);


        //HERE STARTs THE UPDATED FEATURES SECTION

        addPath(lifetimePath + "enabled", false);
        addPath(lifetimePath + "max-life-time-in-seconds", 86400);
        addPath(lifetimePath + "remove-chest", true);

        addPath("Void-Chests.Interact-Messages." + "get-chest-owners-name-message", "&7The void chest you clicked is owned by: &c{player}");


        addPath("Void-Chests.Holograms.enabled", true);
        List<String> lines = new ArrayList<>();
        lines.add("&7Void chest owned by: &c{player}");
        lines.add("&eLife Time: &c{life_time}");
        addPath("Void-Chests.Holograms.hologram-lines", lines);


        addPath("Void-Chests.broadcast-sold", true);
        addPath("Void-Chests.broadcast-sold-interval", 300);
        addPath("Void-Chests.message-sold", "&eYour voidchests sold {sold} &a$ &ein the last 5 minutes!");
    }
}
