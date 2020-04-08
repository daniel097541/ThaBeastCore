package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.wild.WildCommandEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WildCommand extends BeastCommand {
    public WildCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.WILD, FeatureType.WILDERNESS);
        addAlias("wilderness");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;

        Player player = (Player) sender;

        if (!allowedWorld(player.getWorld().getName())) {
            BeastCore.getInstance().sms(player, config.getConfig().getString("Command.Wilderness.world-not-allowed"));
            return;
        }

        int time = config.getConfig().getInt("Command.Wilderness.delay");

        //call event
        Bukkit.getPluginManager().callEvent(new WildCommandEvent(player, time));


    }


    private boolean allowedWorld(String world) {
        for (String worldName : config.getConfig().getConfigurationSection("Command.Wilderness.Worlds").getKeys(false)) {
            if (world.equalsIgnoreCase(worldName)) return true;
        }

        return false;
    }
}
