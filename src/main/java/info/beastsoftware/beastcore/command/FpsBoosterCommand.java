package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.FPSGuiOpenEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FpsBoosterCommand extends BeastCommand {
    public FpsBoosterCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.FPS_BOOSTER, FeatureType.FPS);
        addAlias("booster");
        addAlias("fpsbooster");
        addAlias("fps");
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) return;

        Player player = (Player) sender;

        Bukkit.getPluginManager().callEvent(new FPSGuiOpenEvent(player));
    }
}
