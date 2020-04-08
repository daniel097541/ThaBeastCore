package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.beastutils.utils.ILocationUtil;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastFaction;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TnTUnfillCommand extends BeastCommand {
    public TnTUnfillCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.TNTUNFILL, FeatureType.TNTUNFILL);
    }


    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;

        BeastFaction faction = playerSender.getMyFaction();

        String pathCommand = "TnT-Unfill.command.";
        String pathSettings = "TnT-Unfill.Settings.";

        String noPerm = config.getConfig().getString(this.commandName.getNoPermissionMsgPath());
        String invalidRadius = config.getConfig().getString(pathCommand + "invalid-radius");
        String invalidAmount = config.getConfig().getString(pathCommand + "invalid-amount");
        String tooMuchTnT = config.getConfig().getString(pathCommand + "too-much-tnt");
        String noNearDispensers =  config.getConfig().getString(pathCommand + "no-near-dispensers");
        String noNearWithTnT = config.getConfig().getString(pathCommand + "no-dispensers-with-tnt");
        double radius;
        int tntPerDispenser;
        boolean gamemodeBypass = false;
        int MAXTNT = 64 * 9;
        boolean factionsMode = config.getConfig().getBoolean(pathSettings + "factions-mode");


        //less than 2 args
        if (args.length < 2) {

            if (args.length == 0) {
                //send formats
                for (String msg : config.getConfig().getStringList(pathCommand + "formats"))
                    playerSender.sms(msg);
                return;
            }

            radius = config.getConfig().getInt(pathSettings + "default-unfill-radius");
        } else {

            //no perm for use custom radius
            if (!playerSender.hasPermission(config.getConfig().getString(pathCommand + "custom-radius-unfill-permission"))) {
                //send message no permission
                playerSender.sms(noPerm);
                return;
            }


            //CHECK ARGUMENTS
            try {
                radius = Double.parseDouble(args[0]);
            } catch (NumberFormatException e) {
                //send message invalid radius
                playerSender.sms(invalidRadius);
                return;
            }
        }

        try {
            tntPerDispenser = Integer.parseInt(args[args.length - 1]);
        } catch (NumberFormatException e) {
            if (!args[args.length - 1].equalsIgnoreCase("full")) {
                //send message to player no amount
                playerSender.sms(invalidAmount);
                return;
            } else if (playerSender.hasPermission(config.getConfig().getString(pathCommand + "full-unfill-permission"))) {
                tntPerDispenser = MAXTNT;
                gamemodeBypass = true;
            } else {
                playerSender.sms(noPerm);
                return;
            }
        }
        //CHECK ARGUMENTS


        //too much tnt
        if (tntPerDispenser > MAXTNT) {
            playerSender.sms(tooMuchTnT);
            return;
        }


        //CHECK IF THERE ARE NEARBY DISPENSERS AND STORE THEM IN A LIST

        List<Block> dispensers = ILocationUtil.getNearbyBlocks(playerSender.getBukkitLocation(), (int) radius, Material.DISPENSER);
        //amount of dispensers
        int amount = dispensers.size();
        //no near dispensers
        if (amount == 0) {
            //send message no near dispensers
            playerSender.sms(noNearDispensers);
            return;
        }

        //CHECK IF THERE ARE NEARBY DISPENSERS AND STORE THEM IN A LIST


        ///////// GAMEMODE BYPASS /////////////
        //do stuff for gamemode bypass during fill
        if (gamemodeBypass) {
            int totalAmount = 0;
            int unfilled = 0;
            for (Block blockDispenser : dispensers) {
                Dispenser dispenser = (Dispenser) blockDispenser.getState();

                int dispenserTnT = IInventoryUtil.getAmountOfBlocks(dispenser.getInventory(), Material.TNT);
                if (dispenserTnT == 0) continue;

                boolean sameFac = faction.equals(this.getAtLocation(blockDispenser.getLocation()));

                if (factionsMode && !sameFac)
                    continue;

                totalAmount += dispenserTnT;
                unfilled++;
                dispenser.getInventory().setContents(new ItemStack[9]);
            }
            if (totalAmount > 0) {
                playerSender.getInventory().addItem(new ItemStack(Material.TNT, totalAmount));
            }

            else {
                playerSender.sms(noNearWithTnT);
                return;
            }
            //give the taken tnt to the player

            //send message
            String message = config.getConfig().getString(pathCommand + "unfilled-message");
            message = stringUtils.replacePlaceholder(message, "{number}", unfilled + "");
            playerSender.sms(message);
            return;

        }
        ///////// GAMEMODE BYPASS /////////////


        //invalid tnt
        if (tntPerDispenser <= 0) {
            //send message negative tnt
            playerSender.sms(invalidAmount);
            return;
        }


        int unfilled = 0;
        int giveTnT = 0;

        //FILL DISPENSERS AND GIVE TNT TO PLAYER
        for (Block block : dispensers) {

            boolean sameFac = faction.equals(this.getAtLocation(block.getLocation()));

            //factions mode enabled
            if (factionsMode && !sameFac) {
                continue;
            }

            Dispenser dispenser = (Dispenser) block.getState();

            int tntContained = IInventoryUtil.getAmountOfBlocks(dispenser.getInventory(), Material.TNT);

            if (tntContained == 0) continue;

            if (tntContained < tntPerDispenser) {
                IInventoryUtil.removeItemFromInventory(dispenser.getInventory(), Material.TNT, tntContained);
                giveTnT += tntContained;
                unfilled++;
                continue;
            }

            IInventoryUtil.removeItemFromInventory(dispenser.getInventory(), Material.TNT, tntPerDispenser);
            giveTnT += tntPerDispenser;
            unfilled++;
        }


        //give tnt to player
        if (giveTnT > 0) {
            playerSender.getInventory().addItem(new ItemStack(Material.TNT, giveTnT));
        } else {
            playerSender.sms(noNearWithTnT);
            return;
        }
        //send message with unfilled dispensers
        String message = config.getConfig().getString(pathCommand + "unfilled-message");
        message = stringUtils.replacePlaceholder(message, "{number}", unfilled + "");
        playerSender.sms(message);

    }

}
