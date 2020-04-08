package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.AlertsGuiOpenEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsCommand extends BeastCommand {

    public AlertsCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.FACTION_ALERTS, FeatureType.ALERTS);
        addAlias("alerts");
        addAlias("alert");
        addAlias("falerts");
        addAlias("falert");
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        //if not player return
        if (!(sender instanceof Player)) return;

        BeastPlayer player = this.getPlayer((Player) sender);

        //has not faction
        if (!player.hasFaction()) {
            player.sms(config.getConfig().getString("Faction-Alerts.command.no-faction"));
            return;
        }

        //not leader
        if (!player.isAdmin()) {
            player.sms(config.getConfig().getString("Faction-Alerts.command.no-faction-admin"));
            return;
        }

        if (!this.isOn()) {
            player.sms(config.getConfig().getString("Faction-Alerts.disabled-message"));
            return;
        }

        //open gui
        Bukkit.getPluginManager().callEvent(new AlertsGuiOpenEvent(player));
    }
}
