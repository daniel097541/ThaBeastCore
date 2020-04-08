package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.event.gui.CropHopperGUIOpenEvent;
import info.beastsoftware.beastcore.event.itemgiveevent.CropHopperGiveEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CropHopperCommand extends BeastCommand {
    public CropHopperCommand(BeastCore plugin, IConfig config ) {
        super(plugin, config, CommandType.CROP_HOPPERS, FeatureType.CROP_HOPPERS);

        addAlias("crophopper");
        addAlias("croph");
    }

    @Override
    public void run(CommandSender sender, String[] args) {


        //send formats if wrong command
        if (args.length == 0 || args.length > 3 || (!args[0].equalsIgnoreCase("menu") && !args[0].equalsIgnoreCase("give"))) {
            for (String message : config.getConfig().getStringList("Crop-Hoppers.command.formats"))
                plugin.sms(sender, message);
            return;
        }

        //open menu for the player
        if (args[0].equalsIgnoreCase("menu")) {

            if (!(sender instanceof Player)) return;

            Player player = (Player) sender;
            Bukkit.getPluginManager().callEvent(new CropHopperGUIOpenEvent(player));
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);

        //not online
        if (player == null) {
            plugin.sms(sender, config.getConfig().getString("Crop-Hoppers.command.player-not-online"));
            return;
        }

        int number = 1;

        if (args.length == 3) {
            try {
                number = Integer.parseInt(args[2]);
            } catch (NumberFormatException ignored) {

            }
        }

        //no empty slot
        if (!IInventoryUtil.hasEmptySlot(player.getInventory())) {
            BeastCore.getInstance().sms(sender, config.getConfig().getString("Crop-Hoppers.command.no-slot"));
            return;
        }

        //call event give
        Bukkit.getPluginManager().callEvent(new CropHopperGiveEvent(this.getPlayer(player), number));

        /// send message to sender
        String giveMessage = config.getConfig().getString("Crop-Hoppers.command.give-message");
        giveMessage = stringUtils.replacePlaceholder(giveMessage, "{number}", number + "");
        giveMessage = stringUtils.replacePlaceholder(giveMessage, "{player}", player.getName());
        BeastCore.getInstance().sms(sender, giveMessage);

        /// send message to reciever
        BeastCore.getInstance().sms(player, stringUtils.replacePlaceholder(config.getConfig().getString("Crop-Hoppers.command.recieve-message"), "{number}", number + ""));
    }


}
