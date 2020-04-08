package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.event.itemgiveevent.ChunkBustersGiveEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChunkBusterCommand extends BeastCommand {
    public ChunkBusterCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.CHUNKBUSTERS, FeatureType.CHUNK_BUSTERS);
        addAlias("chunkb");addAlias("chunkbusters");
        addAlias("chunkbuster");

    }


    @Override
    public void run(CommandSender sender, String[] args) {

        if (args.length < 2 || !args[0].equalsIgnoreCase("give")) {
            //send formats
            String formats = config.getConfig().getString("Chunk-Busters.command.format");
            BeastCore.getInstance().sms(sender, formats);
            return;
        }

        Player recieverBP = Bukkit.getPlayer(args[1]);


        //not online player
        if (recieverBP == null) {
            plugin.sms(sender, config.getConfig().getString("Chunk-Busters.command.player-offline"));
            return;
        }

        BeastPlayer reciever = this.getPlayer(recieverBP);

        int number = 1;
        if (args.length > 2) {
            try {
                number = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                //show formats
                plugin.sms(sender, "Type  number you stupid and beautiful guy!");
            }
        }

        int radius;

        if (args.length == 4) {
            try {
                radius = Integer.parseInt(args[3]);
            } catch (NumberFormatException ignored) {
                playerSender.sms(config.getConfig().getString("Chunk-Busters.command.invalid-amount"));
                return;
            }
        } else radius = 1;


        if (radius % 3 != 0 || radius <= 0) {

            if (radius != 1) {
                playerSender.sms(config.getConfig().getString("Chunk-Busters.command.invalid-radius"));
                return;
            }

        }

        //check the inventory
        boolean slot = IInventoryUtil.hasEmptySlot(reciever.getInventory());

        //not empty slot
        if (!slot || number > 64) {
            plugin.sms(sender, config.getConfig().getString("Chunk-Busters.command.no-slot"));
            return;
        }

        // give event
        Bukkit.getPluginManager().callEvent(new ChunkBustersGiveEvent(reciever, number, radius));

        //send messages to reciever and sender
        String message = config.getConfig().getString("Chunk-Busters.command.gave-message");
        message = stringUtils.replacePlaceholder(message, "{player}", reciever.getName());
        message = stringUtils.replacePlaceholder(message, "{count}", number + "");
        plugin.sms(sender, message);

        String messageReciever = config.getConfig().getString("Chunk-Busters.command.recieve-message");
        messageReciever = stringUtils.replacePlaceholder(messageReciever, "{count}", number + "");
        reciever.sms(messageReciever);
    }


}
