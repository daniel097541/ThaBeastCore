package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.itemgiveevent.BeastToolGiveEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BeastToolsCommand extends BeastCommand {
    public BeastToolsCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.LIGHTING_TOOLS, FeatureType.BEAST_TOOLS);
        addAlias("lightningwands");
        addAlias("lt");
        addAlias("lw");
        addAlias("lightningwand");
        addAlias("lightningtool");
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        if (args.length == 0 || args.length == 1){
            String fotmat = config.getConfig().getString("Lightning-Tools.command.format");
            BeastCore.getInstance().sms(sender, fotmat);
            return;
        }

        if (!args[0].equalsIgnoreCase("give")) return;

        Player reciever = Bukkit.getPlayer(args[1]);

        if (reciever == null) {
            plugin.sms(sender, config.getConfig().getString("Lightning-Tools.command.player-not-online"));
            return;
        }

        int number = 1;
        if (args.length == 3) {
            try {
                number = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                //show formats
                plugin.sms(sender, "Type  number you stupid and beautiful guy!");
            }
        }

        Bukkit.getPluginManager().callEvent(new BeastToolGiveEvent(playerSender, number));
        plugin.sms(reciever, config.getConfig().getString("Lightning-Tools.command.message"));

    }


}
