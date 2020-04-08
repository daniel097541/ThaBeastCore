package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ExpWithdrawPathColl extends PathColl {
    @Override
    public void init() {

        addPath("enabled", true);
        String command = "command.";
        String settings = "Settings.";
        String item = "Item.";

        addPath(settings + "disabled-message", "&cExperience withdraw was disabled by an operator!");
        addPath(settings + "max-raw-withdraw", 10000);
        addPath(settings + "max-level-withdraw", 30);

        List<String> formats = new ArrayList<>();

        formats.add("&eUssage for levels: &7/expwithdraw <amount> level");
        formats.add("&eUssage for raw experience: &7/expwithdraw <amount> raw");
        formats.add("&eUssage for withdrawing all of your experience: &7/expwithdraw all");

        addPath(command + "enabled", true);
        addPath(command + "permission", "btf.expwithdraw.command");
        addPath(command + "no-permission-msg", "&cYou dont have permission to use this command!");
        addPath(command + "formats", formats);
        addPath(command + "raw-permission", "btf.bottlexp.raw.command");
        addPath(command + "level-permission", "btf.bottlexp.level.command");
        addPath(command + "all-permission", "btf.bottlexp.all.command");
        addPath(command + "invalid-amount", "&cIntroduce a valid amount of xp!");
        addPath(command + "not-enought-exp", "&cYou dont have enought experience to withdraw!");
        addPath(command + "max-reached", "&cThe max experience you can withdraw is &e{max}");
        addPath(command + "success-message-raw", "&eYou withdrew &7{amount} &eexperience");
        addPath(command + "success-message-level", "&eYou withdrew &7{amount} &elevel/s!");
        addPath(command + "success-message-all", "&eYou withdrew &7{amount} &eexperience");


        ///////////// 1.13
        Material material;
        try {
            material = Material.valueOf("EXP_BOTTLE");
        } catch (IllegalArgumentException ignored) {
            material = Material.EXPERIENCE_BOTTLE;
        }
        ///////////// 1.13
        addPath(item + "material", material.toString());
        addPath(item + "damage", 0);
        addPath(item + "name", "&6EXP bottle!");
        List<String> loreRaw = new ArrayList<>();
        loreRaw.add("&cLeft click to obtain the experience!");
        loreRaw.add("&eWithdrawn by: &6{player}");
        loreRaw.add("&eAmount of raw xp: &6{amount}");
        addPath(item + "lore-raw", loreRaw);


        List<String> loreLevel = new ArrayList<>(loreRaw);
        loreLevel.remove(loreLevel.size() - 1);
        loreLevel.add("&eAmount of xp levels: &6{amount}");
        addPath(item + "lore-level", loreLevel);

    }
}
