package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.itemgiveevent.CropHopperGiveEvent;
import info.beastsoftware.beastcore.manager.IEconomyHook;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.EconomyUtil;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class HopperConvertCommand extends BeastCommand {

    private final EconomyUtil economyUtil;

    public HopperConvertCommand(BeastCore plugin, IConfig config, IEconomyHook economy) {
        super(plugin, config, CommandType.CROPCONVERT, FeatureType.CROP_HOPPERS);
        if (Objects.nonNull(economy)) {
            this.economyUtil = new EconomyUtil(economy.getEconomy());
        } else {
            economyUtil = null;
        }
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        ItemStack hand = playerSender.getItemInHand();
        //not holding a hopper
        if (!hand.getType().equals(Material.HOPPER)) {
            playerSender.sms(config.getConfig().getString("Crop-Hoppers.Convert-Command.not-holding-a-hopper"));
            return;
        }


        int amount = hand.getAmount();
        double price = config.getConfig().getInt("") * amount;

        //not enough money
        if (!economyUtil.hasEnoughtMoney(playerSender.getBukkitPlayer(), price)){
            playerSender.sms(config.getConfig().getString("Crop-Hoppers.Convert-Command.not-enough-money"));
            return;
        }


        //take money and remove hoppers in hand
        economyUtil.takeMoney(playerSender.getBukkitPlayer(), amount);
        playerSender.setItemInHand(null);


        //call event give hopper
        Bukkit.getPluginManager().callEvent(new CropHopperGiveEvent(playerSender, amount));


        //send success message
        playerSender.sms(stringUtils.replacePlaceholder(config.getConfig().getString("Crop-Hoppers.Convert-Command.converted"), "{amount}", amount + ""));
    }
}
