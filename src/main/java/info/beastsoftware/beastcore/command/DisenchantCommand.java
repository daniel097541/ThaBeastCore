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
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class DisenchantCommand extends BeastCommand {
    public DisenchantCommand(BeastCore plugin, IConfig config, CommandType commandName) {
        super(plugin, config, commandName, null);
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        if (args.length == 0) {
            //Send formats
            for (String line : config.getConfig().getStringList("Commands.Disenchant.command-ussage")) {
                plugin.sms(sender, line);
            }
            return;
        }

        if (!(sender instanceof Player)) {
            //send meesage no player
            plugin.sms(sender, config.getConfig().getString("Commands.Disenchant.you-are-not-a-player"));
            return;
        }

        Player playerSender = (Player) sender;

        ItemStack itemStack = playerSender.getItemInHand();

        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            //no item in hand
            plugin.sms(playerSender, config.getConfig().getString("Commands.Disenchant.you-are-not-holding-anything"));
            return;
        }

        Map<Enchantment, Integer> enchants = itemStack.getEnchantments();

        if (enchants.isEmpty()) {
            //item has no enchant
            plugin.sms(playerSender, config.getConfig().getString("Commands.Disenchant.the-item-has-no-enchant"));
            return;
        }
        StringUtils stringUtils = new StringUtils();
        boolean giveBooks = config.getConfig().getBoolean("Commands.Disenchant.give-book");
        int level = 0;
        //remove all enchants
        if (args[0].equalsIgnoreCase("all")) {

            //no permission all
            if (!playerSender.hasPermission(config.getConfig().getString("Commands.Disenchant.permission-all"))) {
                plugin.sms(playerSender, config.getConfig().getString("Commands.Disenchant.no-permission-msg"));
                return;
            }


            for (Enchantment enchantment : enchants.keySet()) {
                itemStack.removeEnchantment(enchantment);
                level = enchants.get(enchantment);
                if (giveBooks) {
                    giveBook(playerSender, level, enchantment);
                }
            }
        }

        //try remove certain enchant
        else {

            Enchantment enchantment = null;

            for (Enchantment enchantment1 : enchants.keySet()) {
                if (enchantment1.getName().equalsIgnoreCase(args[0])) {
                    enchantment = enchantment1;
                    level = enchants.get(enchantment);
                    break;
                }
            }

            //invalid enchant
            if (enchantment == null) {
                plugin.sms(playerSender, config.getConfig().getString("Commands.Disenchant.invalid-enchant"));
                for (Enchantment ench : enchants.keySet()) {
                    String message = config.getConfig().getString("Commands.Disenchant.available-enchants-format-message");
                    message = stringUtils.replacePlaceholder(message, "{enchant_name}", ench.getName());
                    plugin.sms(playerSender, message);
                }
                return;
            }


            //remove it
            itemStack.removeEnchantment(enchantment);

            if (giveBooks) {
                giveBook(playerSender, level, enchantment);
            }
        }

        //Send message disenchanted
        plugin.sms(playerSender, config.getConfig().getString("Commands.Disenchant.item-disenchanted"));

    }


    private void giveBook(Player player, int level, Enchantment enchantment) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        meta.addStoredEnchant(enchantment, level, true);
        book.setItemMeta(meta);
        player.getInventory().addItem(book);
    }
}
