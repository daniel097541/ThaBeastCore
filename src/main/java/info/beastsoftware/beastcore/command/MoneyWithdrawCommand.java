package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.event.itemgiveevent.BankNoteGiveEvent;
import info.beastsoftware.beastcore.manager.IEconomyHook;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.EconomyUtil;
import info.beastsoftware.beastcore.util.StringUtils;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyWithdrawCommand extends BeastCommand {
    private StringUtils stringUtils;
    private Economy economy;

    public MoneyWithdrawCommand(BeastCore plugin, IConfig config, IEconomyHook economy) {
        super(plugin, config, CommandType.WITHDRAW, FeatureType.MONEY_WITHDRAW);
        this.economy = economy.getEconomy();
        this.stringUtils = new StringUtils();
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        //Send formats
        if (args.length == 0) {
            for (String line : config.getConfig().getStringList("command.formats"))
                plugin.sms(sender, line);
            return;
        }
        if ((sender instanceof Player))
            playerSender = this.getPlayer((Player) sender);

        EconomyUtil economyUtil = new EconomyUtil(economy);
        double amount = 0.0;
        if (args.length == 1) {

            if (!(sender instanceof Player))
                return;

            try {
                amount = Double.parseDouble(args[0]);
            } catch (NumberFormatException e) {
                plugin.sms(sender, config.getConfig().getString("command.invalid-amount"));
                return;
            }


            //check money
            if (!economyUtil.hasEnoughtMoney(playerSender.getBukkitPlayer(), amount)) {
                playerSender.sms(config.getConfig().getString("command.you-dont-have-enought-money"));
                return;
            }

            if (amount > config.getConfig().getDouble("Settings.max-money")) {
                String message = config.getConfig().getString("command.too-much-money");
                message = stringUtils.replacePlaceholder(message, "{max}", config.getConfig().getDouble("Settings.max-money") + "");
                playerSender.sms(message);
                return;
            }

            if (amount < config.getConfig().getDouble("Settings.min-money")) {
                String message = config.getConfig().getString("command.too-low-money");
                message = stringUtils.replacePlaceholder(message, "{min}", config.getConfig().getDouble("Settings.min-money") + "");
                playerSender.sms(message);
                return;
            }

            if (!IInventoryUtil.hasEmptySlot(playerSender.getInventory())) {
                playerSender.sms(config.getConfig().getString("command.not-enough-space-in-inventory"));
                return;
            }

            economyUtil.takeMoney(playerSender.getBukkitPlayer(), amount);
            Bukkit.getPluginManager().callEvent(new BankNoteGiveEvent(playerSender, (int) amount));
            plugin.sms(sender, stringUtils.replacePlaceholder(config.getConfig().getString("command.success-message"), "{amount}", amount + ""));
            return;
        }


        if (args.length == 2) {

            if (!sender.hasPermission(config.getConfig().getString("command.withdraw-admin-permission"))) {
                plugin.sms(sender, config.getConfig().getString("command.no-permission-msg"));
                return;
            }


            Player player = Bukkit.getPlayer(args[1]);

            if (player == null) return;

            BeastPlayer beastPlayer = this.getPlayer(player);

            try {
                amount = Double.parseDouble(args[0]);
            } catch (NumberFormatException e) {
                plugin.sms(sender, config.getConfig().getString("command.invalid-amount"));
                return;
            }

            if (amount > config.getConfig().getDouble("Settings.max-money")) {
                String message = config.getConfig().getString("command.too-much-money");
                message = stringUtils.replacePlaceholder(message, "{max}", config.getConfig().getDouble("Settings.max-money") + "");
                plugin.sms(sender, message);
                return;
            }

            if (amount < config.getConfig().getDouble("Settings.min-money")) {
                String message = config.getConfig().getString("command.too-low-money");
                message = stringUtils.replacePlaceholder(message, "{min}", config.getConfig().getDouble("Settings.min-money") + "");
                plugin.sms(sender, message);
                return;
            }

            if (!IInventoryUtil.hasEmptySlot(player.getInventory())) {
                BeastCore.getInstance().sms(sender, config.getConfig().getString("command.not-enough-space-in-inventory"));
                return;
            }


            Bukkit.getPluginManager().callEvent(new BankNoteGiveEvent(beastPlayer, (int) amount));
            plugin.sms(sender, stringUtils.replacePlaceholder(config.getConfig().getString("command.success-message"), "{amount}", amount + ""));

        }

    }


}
