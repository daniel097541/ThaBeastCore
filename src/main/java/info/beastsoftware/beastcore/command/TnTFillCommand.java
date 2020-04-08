package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.beastutils.utils.ILocationUtil;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TnTFillCommand extends BeastCommand {

    public TnTFillCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.TNTFILL, FeatureType.TNTFILL);
    }


    @Override
    public void run(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) return;

        Player player = (Player) sender;
        String pathCommand = "TnT-Fill.command.";
        String pathSettings = "TnT-Fill.Settings.";

        String noPerm = config.getConfig().getString(this.commandName.getNoPermissionMsgPath());

        double radius;
        int tntPerDispenser;
        boolean gamemodeBypass = false;
        int MAXTNT = 64 * 9;


        //less than 2 args
        if (args.length < 2) {

            if (args.length == 0) {
                //send formats
                for (String msg : config.getConfig().getStringList(pathCommand + "formats"))
                    plugin.sms(player, msg);
                return;
            }

            radius = config.getConfig().getInt(pathSettings + "default-fill-radius");
        } else {

            //no perm for use custom radius
            if (!player.hasPermission(config.getConfig().getString(pathCommand + "custom-radius-fill-permission"))) {
                //send message no permission
                plugin.sms(player, noPerm);
                return;
            }


            //CHECK ARGUMENTS
            try {
                radius = Double.parseDouble(args[0]);
            } catch (NumberFormatException e) {
                //send message invalid radius
                plugin.sms(player, config.getConfig().getString(pathCommand + "invalid-radius"));
                return;
            }

            //max radius bypass
            String perm = config.getConfig().getString("TnT-Fill.command.max-radius-bypass-permission");
            int maxRadius = config.getConfig().getInt("TnT-Fill.Settings.max-radius");
            if (!player.hasPermission(perm) && radius > maxRadius) {
                plugin.sms(player, config.getConfig().getString("TnT-Fill.command.max-radius-reached"));
                return;
            }


        }

        try {
            tntPerDispenser = Integer.parseInt(args[args.length - 1]);
        } catch (NumberFormatException e) {
            if (!args[args.length - 1].equalsIgnoreCase("full")) {
                //send message to player no amount
                plugin.sms(player, config.getConfig().getString(pathCommand + "invalid-amount"));
                return;
            } else if (player.hasPermission(config.getConfig().getString(pathCommand + "full-fill-permission"))) {
                tntPerDispenser = MAXTNT;
                gamemodeBypass = true;
            } else {
                plugin.sms(player, config.getConfig().getString(commandName.getNoPermissionMsgPath()));
                return;
            }
        }
        //CHECK ARGUMENTS

        //too much tnt
        if (tntPerDispenser > MAXTNT) {
            plugin.sms(player, config.getConfig().getString(pathCommand + "too-much-tnt"));
            return;
        }


        //CHECK IF THERE ARE NEARBY DISPENSERS AND STORE THEM IN A LIST

        List<Block> dispensers = ILocationUtil.getNearbyBlocks(player.getLocation(), (int) radius, Material.DISPENSER);
        //amount of dispensers
        int amount = dispensers.size();
        //no near dispensers
        if (amount == 0) {
            //send message no near dispensers
            plugin.sms(player, config.getConfig().getString(pathCommand + "no-near-dispensers"));
            return;
        }

        //CHECK IF THERE ARE NEARBY DISPENSERS AND STORE THEM IN A LIST


        ///////// GAMEMODE BYPASS /////////////
        //do stuff for gamemode bypass during fill
        if (gamemodeBypass) {
            for (Block blockDispenser : dispensers) {
                Dispenser dispenser = (Dispenser) blockDispenser.getState();
                dispenser.getInventory().addItem(new ItemStack(Material.TNT, tntPerDispenser));
            }
            String message = config.getConfig().getString(pathCommand + "filled-message");
            message = stringUtils.replacePlaceholder(message, "{number}", dispensers.size() + "");
            plugin.sms(player, message);
            return;
        }
        ///////// GAMEMODE BYPASS /////////////


        //invalid tnt
        if (tntPerDispenser <= 0) {
            //send message negative tnt
            plugin.sms(player, config.getConfig().getString(pathCommand + "invalid-amount"));
            return;
        }


        int amountOfTnT = IInventoryUtil.getAmountOfBlocks(player.getInventory(), Material.TNT);

        //no tnt in inventory
        if (amountOfTnT == 0) {
            //send message no tnt
            plugin.sms(player, config.getConfig().getString(pathCommand + "no-tnt-in-inventory"));
            return;
        }

        int filled = 0;
        //FILL DISPENSERS AND TAKE TNT FROM PLAYER
        for (Block block : dispensers) {
            int addAmount = tntPerDispenser;
            Dispenser dispenser = (Dispenser) block.getState();
            if (amountOfTnT <= 0) break;
            if (amountOfTnT < addAmount)
                addAmount = amountOfTnT;

            if (dispenser.getInventory().containsAtLeast(new ItemStack(Material.TNT), tntPerDispenser)) {
                continue;
            }

            if (!IInventoryUtil.canAddItem(Material.TNT, addAmount, dispenser.getInventory())) continue;
            dispenser.getInventory().addItem(new ItemStack(Material.TNT, addAmount));
            IInventoryUtil.removeItemFromInventory(player.getInventory(), Material.TNT, addAmount);
            amountOfTnT -= addAmount;
            filled++;
        }

        //send message with filled dispensers
        String message = config.getConfig().getString(pathCommand + "filled-message");
        message = stringUtils.replacePlaceholder(message, "{number}", filled + "");
        plugin.sms(player, message);

    }


}
