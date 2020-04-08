package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.beastutils.utils.IPlayerUtil;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ExpWithdrawCommand extends BeastCommand {
    public ExpWithdrawCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.EXP_WITHDRAW, FeatureType.EXP_WITHDRAW);
        addAlias("xpbottle");
        addAlias("bottlexp");
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) return;

        Player player = (Player) sender;

        if (args.length < 1) {
            //sent formats
            for (String s : config.getConfig().getStringList("command.formats"))
                plugin.sms(player, s);
            return;
        }


        //check the way of getting the amount
        boolean level = false;
        boolean raw = false;
        boolean all = false;


        //args == 1
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("all"))
                all = true;
        }


        //args > 1
        else {

            if (args[1].equalsIgnoreCase("raw"))
                raw = true;
            else {
                if (args[1].equalsIgnoreCase("level"))
                    level = true;


            }
        }


        if (!raw && !level && !all) {
            //send formats
            for (String s : config.getConfig().getStringList("command.formats"))
                plugin.sms(player, s);
            return;
        }
        //check the way of getting the amount


        int amount = 0;

        //not all, get amount from params
        if (!all) {

            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                //invalid amount
                plugin.sms(player, config.getConfig().getString("command.invalid-amount"));
                return;
            }

            if (amount < 1) {
                //invalid amount
                plugin.sms(player, config.getConfig().getString("command.invalid-amount"));
                return;
            }
        }

        ItemStack bottle;


        if (all) {
            amount = player.getTotalExperience();


            if (!player.hasPermission(config.getConfig().getString("command.all-permission"))) {
                //send message max reached
                plugin.sms(player, config.getConfig().getString("command.no-permission-msg"));
                return;
            }


            IPlayerUtil.changePlayerExp(player, -amount);
            //add item to player
            bottle = createBottle(player, amount, "raw");
            player.getInventory().addItem(bottle);
            String message = config.getConfig().getString("command.success-message-all");
            message = stringUtils.replacePlaceholder(message, "{amount}", amount + "");
            plugin.sms(player, message);
            return;


        }


        if (raw) {

            if (!player.hasPermission(config.getConfig().getString("command.raw-permission"))) {
                //send message max reached
                plugin.sms(player, config.getConfig().getString("command.no-permission-msg"));
                return;
            }

            if (amount > config.getConfig().getInt("Settings.max-raw-withdraw")) {
                //send message max reached
                plugin.sms(player, stringUtils.replacePlaceholder(config.getConfig().getString("command.max-reached"), "{max}", config.getConfig().getInt("Settings.max-raw-withdraw") + ""));
                return;
            }

            if (player.getTotalExperience() < amount) {
                //sen message not enought
                plugin.sms(player, config.getConfig().getString("command.not-enought-exp"));
                return;
            }

            IPlayerUtil.changePlayerExp(player, -amount);
            //add item to player
            bottle = createBottle(player, amount, args[1]);
            player.getInventory().addItem(bottle);
            String message = config.getConfig().getString("command.success-message-raw");
            message = stringUtils.replacePlaceholder(message, "{amount}", amount + "");
            plugin.sms(player, message);
            return;
        }

        if (!player.hasPermission(config.getConfig().getString("command.level-permission"))) {
            //send message no perm
            plugin.sms(player, config.getConfig().getString("command.no-permission-msg"));
            return;
        }

        if (amount > config.getConfig().getInt("Settings.max-level-withdraw")) {
            //send message max reached
            plugin.sms(player, stringUtils.replacePlaceholder(config.getConfig().getString("command.max-reached"), "{max}", config.getConfig().getInt("Settings.max-level-withdraw") + ""));
            return;
        }

        if (player.getLevel() < amount) {
            //sen message not enought
            plugin.sms(player, config.getConfig().getString("command.not-enought-exp"));
            return;
        }

        int newLevel = player.getLevel() - amount;
        player.setLevel(newLevel);
        bottle = createBottle(player, amount, args[1]);
        player.getInventory().addItem(bottle);
        String message = config.getConfig().getString("command.success-message-level");
        message = stringUtils.replacePlaceholder(message, "{amount}", amount + "");
        plugin.sms(player, message);
    }


    private ItemStack createBottle(Player player, int amount, String way) {
        ItemStack itemStack;

        Material material = Material.valueOf(config.getConfig().getString("Item.material"));
        String name = config.getConfig().getString("Item.name");
        String damage = config.getConfig().getInt("Item.damage") + "";

        List<String> lore;
        if (way.equalsIgnoreCase("level")) {
            lore = config.getConfig().getStringList("Item.lore-level");
        } else lore = config.getConfig().getStringList("Item.lore-raw");


        List<String> loreReplaced = new ArrayList<>();
        for (String loreLine : lore) {
            loreLine = stringUtils.replacePlaceholder(loreLine, "{player}", player.getName());
            loreLine = stringUtils.replacePlaceholder(loreLine, "{amount}", amount + "");
            loreReplaced.add(loreLine);
        }

        itemStack = IInventoryUtil.createItem(1, material, name, loreReplaced, Short.valueOf(damage), true);


        if (way.equalsIgnoreCase("level"))
            itemStack.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, amount);
        else itemStack.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, amount);


        return itemStack;
    }

}
