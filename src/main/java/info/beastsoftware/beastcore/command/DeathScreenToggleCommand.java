package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.command.CommandSender;

public class DeathScreenToggleCommand extends BeastCommand {

    public DeathScreenToggleCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.TOGGLE_DEATHSCREEN, FeatureType.NO_RESPAWN_SCREEN);
    }

    @Override
    public void run(CommandSender sender, String[] args) {


        String message;

        String playerPath = "disabled-players." + playerSender.getUuid().toString();

        boolean b = config.getConfig().getBoolean(playerPath);

        if(b){
            message = config.getConfig().getString("Command.toggled-on");
        }
        else{
            message = config.getConfig().getString("Command.toggled-off");
        }


        config.getConfig().set(playerPath, !b);
        config.save();
        playerSender.sms(message);
    }

}
