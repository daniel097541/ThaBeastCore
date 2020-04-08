package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.JellyLegsToogleEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JellyLegsCommand extends BeastCommand {

    public JellyLegsCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.JELLY_LEGS, FeatureType.JELLY_LEGS);
        addAlias("nofall");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;

        if (!isOn()) {
            plugin.sms(sender, config.getConfig().getString("Commands.Jelly-Legs.disabled-message"));
            return;
        }

        if (BeastCore.getInstance().getApi().hasJellyLegsEnabled(playerSender)) {
            Bukkit.getServer().getPluginManager().callEvent(new JellyLegsToogleEvent(false, (Player) sender));
            plugin.sms(sender, config.getConfig().getString("Commands.Jelly-Legs.disabled-message"));
        } else {
            Bukkit.getServer().getPluginManager().callEvent(new JellyLegsToogleEvent(true, (Player) sender));
            plugin.sms(sender, config.getConfig().getString("Commands.Jelly-Legs.enabled-message"));
        }
    }
}
