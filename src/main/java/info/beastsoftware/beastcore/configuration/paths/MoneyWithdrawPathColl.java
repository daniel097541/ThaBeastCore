package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class MoneyWithdrawPathColl extends PathColl {

    public MoneyWithdrawPathColl() {
        super();
    }

    @Override
    public void init() {
        String path = "command.";

        addPath("enabled", true);
        addPath(path + "permission", "btf.moneywithdraw.command");
        addPath(path + "withdraw-admin-permission", "btf.adminwithdraw.command");
        List<String> formats = new ArrayList<>();
        formats.add("&eCommand ussage for yourself: &7/withdraw <amount>");
        formats.add("&eCommand ussage for others: &7/withdraw <amount> <player>");
        addPath(path + "formats", formats);
        addPath(path + "no-permission-msg", "&cYou dont have permission to use this command!");
        addPath(path + "you-dont-have-enought-money", "&cYou dont have enought money!");
        addPath(path + "invalid-amount", "&cIntroduce a valid amount of money!");
        addPath(path + "success-message", "&aYou withdrew &e{amount} &a$ !");
        addPath(path + "too-much-money", "&cYou cant withdraw more than &e{max} !");
        addPath(path + "too-low-money", "&cYou have to withdraw at least &e{min} !");
        addPath(path + "not-enough-space-in-inventory", "&cNot enough space in the inventory!");

        String pathItem = "Item.";
        addPath(pathItem + "material", Material.PAPER.toString());
        addPath(pathItem + "name", "&eBank note of &d{player}");
        addPath(pathItem + "damage", 0);
        List<String> lore = new ArrayList<>();
        lore.add("&eLeft click with this note in your hand");
        lore.add("&eto add &d{amount} &a$ &eto your balance!");
        addPath(pathItem + "lore", lore);

        String pathSettings = "Settings.";

        addPath(pathSettings + "min-money", 10);
        addPath(pathSettings + "max-money", 1000000000);

        addPath("message-deposit-money", "&eYou deposited &a{amount} &emoney from a bank note!");
        ;

    }
}
