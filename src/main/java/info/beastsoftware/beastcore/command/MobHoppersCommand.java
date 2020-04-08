package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MobHoppersCommand extends BeastCommand {
    public MobHoppersCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.MOB_HOPPER, FeatureType.MOB_HOPPERS);
        addAlias("mobh");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length < 4 || !args[0].equalsIgnoreCase("give")) {
            for (String message : config.getConfig().getStringList("Mob-Hoppers.command.formats"))
                plugin.sms(sender, message);
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);

        //not online
        if (player == null) {
            plugin.sms(sender, config.getConfig().getString("Mob-Hoppers.command.player-not-online"));
            return;
        }

        //not a mob
        String MOBPLACEHOLDER = "{mob}";
        if (!config.getConfig().getStringList("Mob-Hoppers.Settings.mob-list").contains(args[2].toUpperCase())) {
            //send error in mob message
            String message = config.getConfig().getString("Mob-Hoppers.command.not-allowed-mob");
            message = stringUtils.replacePlaceholder(message, MOBPLACEHOLDER, args[2].toUpperCase());
            plugin.sms(sender, message);
            return;
        }

        int number = 1;

        if (args.length == 4) {
            try {
                number = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {

                plugin.sms(player, "Introduce a number!");
                return;
            }
        }
        //info for the hopper
        String basePath = "Mob-Hoppers.Item.";
        String name = ChatColor.translateAlternateColorCodes('&', stringUtils.replacePlaceholder(config.getConfig().getString(basePath + "name"), MOBPLACEHOLDER, args[2].toUpperCase()));
        List<String> lore = stringUtils.translateLore(config.getConfig().getStringList(basePath + "lore"));

        //create the hopper
        ItemStack itemStack = IInventoryUtil.createItem(1, Material.HOPPER, name, lore, true);

        //check empty slots
        int slot = IInventoryUtil.hasEmptySlot(player.getInventory(), itemStack);
        boolean empty = IInventoryUtil.hasEmptySlot(player.getInventory());

        //no empty slots
        if (!empty && slot < 0 || number > 64) {
            plugin.sms(sender, config.getConfig().getString("Mob-Hoppers.command.no-slot"));
            return;
        }

        //add item to the inventory
        IInventoryUtil.addItem(itemStack, player, empty, number, slot);

        //send messages to sender and reciever
        String recieveMessage = config.getConfig().getString("Mob-Hoppers.command.recieve-message");
        recieveMessage = stringUtils.replacePlaceholder(recieveMessage, MOBPLACEHOLDER, args[2].toUpperCase());
        plugin.sms(player, stringUtils.replacePlaceholder(recieveMessage, "{number}", number + ""));

        String giveMessage = config.getConfig().getString("Mob-Hoppers.command.give-message");
        giveMessage = stringUtils.replacePlaceholder(giveMessage, "{number}", number + "");
        giveMessage = stringUtils.replacePlaceholder(giveMessage, "{player}", player.getName());
        giveMessage = stringUtils.replacePlaceholder(giveMessage, MOBPLACEHOLDER, args[2].toUpperCase());
        plugin.sms(sender, giveMessage);

    }
}
