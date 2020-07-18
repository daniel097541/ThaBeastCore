package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.event.itemgiveevent.HarvesterHoeGiveEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HarvesterHoesCommand extends BeastCommand {

    public HarvesterHoesCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.HARVESTER_HOES, FeatureType.HARVESTER_HOES);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length > 1) {
            if (args[0].equalsIgnoreCase("give")) {
                String targetName = args[1];
                Player player = Bukkit.getPlayer(targetName);
                if (Objects.nonNull(player)) {
                    String message = config.getConfig().getString("command.success-message");
                    StrUtils.sms(sender, StrUtils.replacePlaceholder(message, "{player}", player.getName()));
                    HarvesterHoeGiveEvent event = new HarvesterHoeGiveEvent(this.getPlayer(player), 1);
                    Bukkit.getPluginManager().callEvent(event);
                    return;
                } else {
                    StrUtils.sms(sender, StrUtils.replacePlaceholder(config.getConfig().getString("command.player-offline"), "{player}", targetName));
                    return;
                }
            }
        }

        StrUtils.sms(sender, config.getConfig().getString("command.format"));
    }
}
