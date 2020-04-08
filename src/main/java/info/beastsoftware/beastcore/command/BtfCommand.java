package info.beastsoftware.beastcore.command;


import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.MainGuiOpenEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.test.ITester;
import info.beastsoftware.beastcore.test.Tester;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class BtfCommand extends BeastCommand {
    public BtfCommand(BeastCore plugin, IConfig config ) {
        super(plugin, config, CommandType.BTF, FeatureType.MAIN_COMMAND);
        addAlias("beastfactions");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        //1 argument
        if (args.length != 1) {
            //send formats
            for (String format : config.getConfig().getStringList("Commands.BtfCommand.Formats"))
                plugin.sms(sender, format);
            return;
        }

        //test
        if (args[0].equalsIgnoreCase("test")) {
            ITester tester = new Tester();
            tester.testCommands(playerSender);
            config.getConfig().set("test", playerSender);

            BeastPlayer test = (BeastPlayer) config.getConfig().get("test");
            Bukkit.broadcastMessage(test.getName());
            return;
        }


        //reload command
        if (args[0].equalsIgnoreCase("reload")) {
            //reload permission
            if (!sender.hasPermission(config.getConfig().getString("Commands.Reload.permission"))) {
                plugin.sms(sender, config.getConfig().getString(commandName.getNoPermissionMsgPath()));
                return;
            }
            plugin.reload();
            plugin.sms(sender, config.getConfig().getString("Commands.Reload.message"));
            return;
        }

        //features menu
        if (args[0].equalsIgnoreCase("gui") || args[0].equalsIgnoreCase("menu") || args[0].equalsIgnoreCase("features")) {
            if (!(sender instanceof Player)) return;
            Player player = (Player) sender;
            //Bukkit.getPluginManager().callEvent(new MainMenuOpenEvent(player));
            Bukkit.getPluginManager().callEvent(new MainGuiOpenEvent(player));
        }


    }


}
