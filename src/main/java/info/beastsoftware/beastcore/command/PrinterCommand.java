package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.PrinterToggleEvent;
import info.beastsoftware.beastcore.event.ShowMoneySpentInPrinterEvent;
import info.beastsoftware.beastcore.manager.IEconomyHook;
import info.beastsoftware.beastcore.printer.IPrinterManager;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.EconomyUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

public class PrinterCommand extends BeastCommand {

    private EconomyUtil economyUtil;

    private final IPrinterManager printerManager;


    public PrinterCommand(BeastCore plugin, IConfig config, IEconomyHook economyHook, IPrinterManager printerManager) {
        super(plugin, config, CommandType.PRINTER, FeatureType.PRINTER);
        this.printerManager = printerManager;

        if (economyHook == null) {
            return;
        }
        economyUtil = new EconomyUtil(economyHook.getEconomy());
    }


    @Override
    public void run(CommandSender sender, String[] args) {

        if (playerSender == null) {
            return;
        }

        if (args.length == 0) {
            //send formats
            playerSender.sms(config.getConfig().getString("command.Messages.format"));
            playerSender.sms(config.getConfig().getString("command.Messages.format-money"));
            return;
        }


        if (args[0].equalsIgnoreCase("on")) {

            if (!economyUtil.hasEnoughtMoney(playerSender.getBukkitPlayer(), 1)) {
                //no money
                playerSender.sms(config.getConfig().getString("Settings.Messages.you-dont-have-money"));
                return;
            }

            if (this.isFactionsHooked() && ((!playerSender.hasFaction() || playerSender.isInOthersLand()) && config.getConfig().getBoolean("Settings.allow-printer-only-in-own-land"))) {
                //only in your land
                playerSender.sms(config.getConfig().getString("command.Messages.only-in-your-faction-land"));
                return;
            }

//            if (this.isFactionsHooked() && config.getConfig().getBoolean("Settings.allow-printer-only-in-own-land") && !hook.getFactionIdAtLocation(playerSender.getLocation()).equalsIgnoreCase(hook.getFactionId(playerSender))) {
//                plugin.sms(playerSender, config.getConfig().getString("command.Messages.only-in-your-faction-land"));
//                return;
//            }


            if (this.printerManager.isPlayerInPrinter(playerSender)) {
                playerSender.sms(config.getConfig().getString("command.Messages.already-enabled"));
                return;
            }

            //check armour
            if (config.getConfig().getBoolean("Settings.deny-if-is-wearing-armour")) {
                for (ItemStack itemStack : playerSender.getInventory().getArmorContents()) {
                    if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
                        String mess = config.getConfig().getString("Settings.Messages.take-off-armor");
                        playerSender.sms(mess);
                        return;
                    }
                }
            }


            Bukkit.getPluginManager().callEvent(new PrinterToggleEvent(true, playerSender));
            playerSender.sms(config.getConfig().getString("command.Messages.enabled"));
            return;
        }

        if (args[0].equalsIgnoreCase("off")) {

            if (!this.printerManager.isPlayerInPrinter(playerSender)) {
                playerSender.sms(config.getConfig().getString("command.Messages.already-disabled"));
                return;
            }

            Bukkit.getPluginManager().callEvent(new PrinterToggleEvent(false, playerSender));
            playerSender.sms(config.getConfig().getString("command.Messages.disabled"));
            return;
        }


        if (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("money")) {

            if (!this.printerManager.isPlayerInPrinter(playerSender)) {
                playerSender.sms(config.getConfig().getString("command.Messages.already-disabled"));
                return;
            }


            Bukkit.getPluginManager().callEvent(new ShowMoneySpentInPrinterEvent(playerSender));
            return;
        }


        playerSender.sms(config.getConfig().getString("command.Messages.format"));
        playerSender.sms(config.getConfig().getString("command.Messages.format-money"));

    }
}
