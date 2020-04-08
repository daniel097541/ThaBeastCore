package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.EditDropsGUIOpenEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditDropsCommand extends BeastCommand {
    public EditDropsCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.DROPS, FeatureType.EDIT_DROPS);
        addAlias("editdrops");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        //get the invetory
        Bukkit.getPluginManager().callEvent(new EditDropsGUIOpenEvent(player));
    }
}
