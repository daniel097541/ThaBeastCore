package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.command.CommandSender;

public class KillMobsCommand extends BeastCommand {
    public KillMobsCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.KILL_MERGED, FeatureType.MOB_MERGER);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        //run command

        if (!isOn()) return;

        int killed = plugin.getApi().getMobsService().getAmountOfStacks();
        plugin.getApi().killAllMergedMobs();

        String message = config.getConfig().getString("command.killed-all-msg");
        message = stringUtils.replacePlaceholder(message, "{number_killed}", killed + "");
        plugin.sms(sender, message);
    }
}
