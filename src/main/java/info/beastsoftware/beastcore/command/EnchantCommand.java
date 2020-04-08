package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.util.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnchantCommand extends BeastCommand {
    public EnchantCommand(BeastCore plugin, IConfig config, CommandType commandName) {
        super(plugin, config, commandName, null);
    }


    @Override
    public void run(CommandSender sender, String[] args) {

        if (args.length == 0 || args.length > 2) {
            //send formats
            plugin.sms(sender, config.getConfig().getString("Commands.Enchant.command-ussage"));
            return;
        }

        if (!(sender instanceof Player)) {
            //send meesage no player
            plugin.sms(sender, config.getConfig().getString("Commands.Enchant.you-are-not-a-player"));
            return;
        }

        Player playerSender = (Player) sender;

        ItemStack itemStack = playerSender.getItemInHand();

        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            //no item in hand
            plugin.sms(playerSender, config.getConfig().getString("Commands.Enchant.you-are-not-holding-anything"));
            return;
        }

        int level = 1;

        if (args.length == 2) {
            try {
                level = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                //Send message invalid level
                plugin.sms(playerSender, config.getConfig().getString("Commands.Enchant.invalid-level"));
                return;
            }
        }

        StringUtils stringUtils = new StringUtils();

        Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());

        if (enchantment == null) {
            //invalid enchant
            String message = config.getConfig().getString("Commands.Enchant.invalid-enchant");
            message = stringUtils.replacePlaceholder(message, "{enchant_name}", args[0]);
            plugin.sms(playerSender, message);

            for (Enchantment enchantment1 : Enchantment.values()) {
                String message1 = config.getConfig().getString("Commands.Enchant.available-enchants-format-message");
                message1 = stringUtils.replacePlaceholder(message1, "{enchant_name}", enchantment1.getName());
                plugin.sms(playerSender, message1);
            }

            return;
        }


        //enchant and send message
        itemStack.addUnsafeEnchantment(enchantment, level);
        String message = config.getConfig().getString("Commands.Enchant.item-enchanted");
        message = stringUtils.replacePlaceholder(message, "{enchant_name}", enchantment.getName());
        message = stringUtils.replacePlaceholder(message, "{level}", level + "");
        plugin.sms(playerSender, message);


    }
}
