package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.event.ThrowableEggsGiveEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.StringUtils;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ThrowableEggsCommand extends BeastCommand {

    public ThrowableEggsCommand(IConfig config, BeastCore plugin) {
        super(plugin, config, CommandType.THROWABLE_EGGS, FeatureType.THROWABLE_CEGGS);
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        if(args.length >= 2){

            if(args[0].equalsIgnoreCase("give")) {
                Player player = Bukkit.getPlayer(args[1]);

                if(Objects.nonNull(player)){

                    BeastPlayer beastPlayer = this.getPlayer(player);

                    int amount = 1;

                    if(args.length == 3){
                        try{
                            amount = Integer.parseInt(args[2]);
                        }
                        catch (Exception ignored){}
                    }

                    new ThrowableEggsGiveEvent(beastPlayer, amount);

                    String message = config.getConfig().getString("command.given-egg");
                    message = StrUtils.replacePlaceholder(message, "{player}", beastPlayer.getName());
                    message = StrUtils.replacePlaceholder(message, "{amount}", String.valueOf(amount));

                    plugin.sms(sender, message);
                }

                //invalid player
                else{
                    String message = config.getConfig().getString("command.invalid-player");
                    message = StrUtils.replacePlaceholder(message, "{player}", args[1]);
                    plugin.sms(sender, message);
                }

                return;
            }
        }
        String message = config.getConfig().getString("command.formats");
        plugin.sms(sender, message);
    }
}
