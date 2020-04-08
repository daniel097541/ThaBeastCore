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

public class VoidChestsCommand extends BeastCommand {
    public VoidChestsCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.VOID_CHESTS, FeatureType.VOID_CHESTS);
        addAlias("voidc");
    }


    @Override
    public void run(CommandSender sender,  String[] args) {

        //send formats if wrong command
        if (args.length == 0 || args.length > 3 || (!args[0].equalsIgnoreCase("menu") && !args[0].equalsIgnoreCase("give"))) {
            for (String message : config.getConfig().getStringList("Void-Chests.command.formats"))
                plugin.sms(sender, message);
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);

        //not online
        if (player == null) {
            plugin.sms(sender, config.getConfig().getString("Void-Chests.command.offline-player"));
            return;
        }

        int number = 1;

        if (args.length == 3) {
            try {
                number = Integer.parseInt(args[2]);
            } catch (NumberFormatException ignored) {

            }
        }
        //info for the hopper
        String basePath = "Void-Chests.Item.";
        String name = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString(basePath + "name"));
        List<String> lore = stringUtils.translateLore(config.getConfig().getStringList(basePath + "lore"));

        //create the hopper
        ItemStack itemStack = IInventoryUtil.createItem(1, Material.CHEST, name, lore, true);

        //check empty slots
        int slot = IInventoryUtil.hasEmptySlot(player.getInventory(), itemStack);
        boolean empty = IInventoryUtil.hasEmptySlot(player.getInventory());

        //no empty slots
        if (!empty && slot < 0 || number > 64) {
            plugin.sms(sender, config.getConfig().getString("Void-Chests.command.inventory-full"));
            return;
        }

        //add item to the inventory
        IInventoryUtil.addItem(itemStack, player, empty, number, slot);

        //send messages to sender and reciever
        plugin.sms(player, stringUtils.replacePlaceholder(config.getConfig().getString("Void-Chests.command.recieve-message"), "{number}", number + ""));

        String giveMessage = config.getConfig().getString("Void-Chests.command.give-message");
        giveMessage = stringUtils.replacePlaceholder(giveMessage, "{number}", number + "");
        giveMessage = stringUtils.replacePlaceholder(giveMessage, "{player}", player.getName());

        plugin.sms(sender, giveMessage);


    }
}
