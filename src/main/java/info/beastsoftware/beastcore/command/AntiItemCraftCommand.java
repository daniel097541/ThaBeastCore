package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.AntiItemCraftOpenEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AntiItemCraftCommand extends BeastCommand {

    public AntiItemCraftCommand(BeastCore plugin, IConfig config, CommandType type) {
        super(plugin, config, type, FeatureType.ANTI_ITEM_CRAFT);
    }

    @Override
    public void run(CommandSender sender,  String[] args) {
        //not a player
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        //open inventory
        Bukkit.getPluginManager().callEvent(new AntiItemCraftOpenEvent(player));
    }
}
