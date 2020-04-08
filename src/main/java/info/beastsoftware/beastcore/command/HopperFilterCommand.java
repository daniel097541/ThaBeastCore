package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.HopperFilterGUIOpenEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HopperFilterCommand extends BeastCommand {
    public HopperFilterCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.HOPPERFILTER, FeatureType.HOPPER_FILTER);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        Bukkit.getPluginManager().callEvent(new HopperFilterGUIOpenEvent(player));
    }
}
