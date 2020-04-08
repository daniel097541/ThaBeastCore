package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.ItemFilterGuiOpenEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ItemFilterCommand extends BeastCommand {
    public ItemFilterCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.ITEMFILTER, FeatureType.ITEM_FILTER);
        addAlias("filter");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        Bukkit.getPluginManager().callEvent(new ItemFilterGuiOpenEvent(playerSender));
    }
}
