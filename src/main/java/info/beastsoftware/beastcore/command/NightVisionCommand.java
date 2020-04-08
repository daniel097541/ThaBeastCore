package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.command.CommandSender;

public class NightVisionCommand extends BeastCommand {


    public NightVisionCommand(IConfig config) {
        super(BeastCore.getInstance(), config, CommandType.NV, FeatureType.NV);
        this.addAlias("nightvision");
    }

    @Override
    public void run(CommandSender sender, String[] args) {


        boolean nv = API().isOnNv(playerSender);
        String message = null;

        //disable nv
        if (nv) {
            message = this.config.getConfig().getString("disabled-message");
            API().removeFromNv(playerSender);
        }


        //enable nv
        else {
            message = this.config.getConfig().getString("enabled-message");
            API().addToNv(playerSender);
        }


        //send message
        playerSender.sms(message);
    }
}
