package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class PriterPathColl extends PathColl {

    public PriterPathColl() {
        super();
    }

    @Override
    public void init() {
        addPath("enabled", true);

        addPath("Settings.use-essentials-woths", false);
        addPath("Settings.use-shop-gui-plus-woths", false);
        addPath("Settings.nearby-enemy-radius", 32);
        addPath("Settings.allow-printer-only-in-own-land", true);
        addPath("Settings.no-fall-damage-on-printer-disable", true);
        addPath("Settings.no-fall-damage-time", 30);
        addPath("Settings.use-default-sell-system-if-others-fail", false);

        addPath("Settings.Messages.you-cant-break-in-printer-mode", "&cYou cant break blocks in printer mode!");
        addPath("Settings.Messages.cant-teleport-in-printer-mode", "&cYou cant teleport while in printer mode!");
        addPath("Settings.Messages.cant-drop-items", "&cYou cant drop items in printer mode!");
        addPath("Settings.Messages.you-dont-have-money", "&cYou dont have enought money!");
        addPath("Settings.Messages.nearby-enemy", "&cThere is a nearby enemy!");
        addPath("Settings.Messages.you-cant-open-inventories", "&cYou cant open inventories while in printer mode!");
        addPath("Settings.Messages.block-is-not-in-shop", "&c(!)&4 The block you tried to place is not in the shop!");
        addPath("Settings.Messages.cant-place-that-block", "&c(!) &4You cannot place that block!");

        List<String> blackList = new ArrayList<>();
        blackList.add(Material.BEDROCK.toString());
        blackList.add(Material.DRAGON_EGG.toString());
        //////////// 1.13 ////////////////
        Material material;
        try {
            material = Material.valueOf("MOB_SPAWNER");
        } catch (IllegalArgumentException ignored) {
            material = Material.SPAWNER;
        }
        blackList.add(material.toString());
        //////////// 1.13 ////////////////


        addPath("Settings.blacklisted-blocks", blackList);

        addPath("command.permission", "bfc.printer.use");
        addPath("command.no-permission-msg", "&c(!) &4You dont have permission!");
        addPath("command.enabled", true);

        addPath("command.Messages.enabled", "&aPrinter enabled!");
        addPath("command.Messages.disabled", "&cPrinter Disabled!");
        addPath("command.Messages.format", "&eCommand use: &7/printer on/off");
        addPath("command.Messages.format-money", "&eCheck how much you spent using printer: &7/printer money");
        addPath("command.Messages.already-enabled", "&eYou have the printer mode already enabled!");
        addPath("command.Messages.already-disabled", "&eYou have the printer mode already disabled!");
        addPath("command.Messages.only-in-your-faction-land", "&cYou only can use printer in your factions land!");
        addPath("command.Messages.spent-using-printer", "&eYou spent &b{money} &a$ &ein printer mode!");

        List<String> mats = new ArrayList<>();
        mats.add(Material.ITEM_FRAME.toString());

        addPath("Settings.interact-blacklisted-blocks", mats);


        addPath("Settings.Messages.blocked-interaction", "&cYou tried to interact with a blacklisted block!");


        List<String> cmds = new ArrayList<>();

        cmds.add("auction");
        cmds.add("ah");
        cmds.add("auctionhouse");

        addPath("Settings.Blocked-All-Commands", true);
        addPath("Settings.Black-List-Mode", true);
        addPath("Settings.White-List-Mode", false);



        addPath("Settings.Blocked-Commands", cmds);
        addPath("Settings.Whitelisted-Commands", cmds);

        addPath("Settings.Messages.blocked-command", "&cThat command is blocked while in printer mode!");

        List<String> ent = new ArrayList<>();
        ent.add(EntityType.ITEM_FRAME.name());

        addPath("Settings.interact-blacklisted-entities", ent);

        List<String> items = new ArrayList<>();
        Material egg;
        Material expBottle;
        try {
            egg = Material.valueOf("MONSTER_EGG");
            expBottle = Material.valueOf("EXP_BOTTLE");
        } catch (IllegalArgumentException ignored) {
            egg = Material.CREEPER_SPAWN_EGG;
            expBottle = Material.EXPERIENCE_BOTTLE;
        }
        items.add(egg.toString());
        items.add(expBottle.toString());

        addPath("Settings.interact-blacklisted-items-in-hand", items);

        addPath("Settings.disable-item-pickup", true);
        addPath("Settings.disable-blocks-with-inventory-inside", false);
        addPath("Settings.allow-players-remove-blocks-placed-by-them-in-printer", true);


        addPath("Settings.deny-if-is-wearing-armour", true);
        addPath("Settings.Messages.take-off-armor", "&cYou cant enter into printer mode if you are wearing an armour equipment!");
        addPath("Settings.show-money-in-interval", true);
        addPath("Settings.show-money-interval-in-seconds", 30);

        addPath("Settings.allow-teleport", false);
        addPath("Settings.remove-nbt-tags", true);

    }
}
