package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.event.grace.GracePauseEvent;
import info.beastsoftware.beastcore.event.grace.GraceDisplayCommandEvent;
import info.beastsoftware.beastcore.event.grace.GraceResumeEvent;
import info.beastsoftware.beastcore.event.grace.GraceTaskRestartEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GraceCommand extends BeastCommand {
    public GraceCommand(BeastCore plugin, IConfig config ) {
        super(plugin, config, CommandType.GRACE, FeatureType.GRACE);
    }

    @Override
    public void run(CommandSender sender, String[] args) {


        //// GRACE DISPLAY
        if (args.length == 0) {
            Bukkit.getPluginManager().callEvent(new GraceDisplayCommandEvent(sender));
            return;
        }


        ///// SEND FORMATS
        if (args[0].equalsIgnoreCase("help")) {
            this.sendFormats(sender);
            return;
        }

        String gracePerm = config.getConfig().getString("Grace-Period.toggle-permission");


        //no perm to toggle
        if (!sender.hasPermission(gracePerm)) {
            plugin
                    .sms(sender, config.getConfig()
                            .getString(commandName.getNoPermissionMsgPath()));
            return;
        }


        //// ENABLE
        if (args[0].equalsIgnoreCase("enable")) {
            Bukkit.getPluginManager().callEvent(new GraceResumeEvent(sender));
            return;
        }


        //// DISABLE
        if (args[0].equalsIgnoreCase("disable")) {
            Bukkit
                    .getPluginManager()
                    .callEvent(new GracePauseEvent(sender));
            return;
        }


        //// RESTART
        if (args[0].equalsIgnoreCase("restart")) {
            Bukkit
                    .getPluginManager()
                    .callEvent(new GraceTaskRestartEvent());
            return;
        }

        ///// SEND FORMATS
        sendFormats(sender);
    }

    private void sendFormats(CommandSender sender){
        List<String> formats = config
                .getConfig()
                .getStringList("Grace-Period.formats");
        StrUtils
                .mms(formats,sender);
    }
}
