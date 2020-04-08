package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoidSpawnCommand extends BeastCommand {
    public VoidSpawnCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.VOID_SPAWN_COMMAND, FeatureType.VOID_FALL);
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        Location location = player.getLocation();

        config.getConfig().set("Anti-Void-Falling.spawn.world", location.getWorld().getName());
        config.getConfig().set("Anti-Void-Falling.spawn.x", location.getX());
        config.getConfig().set("Anti-Void-Falling.spawn.y", location.getY());
        config.getConfig().set("Anti-Void-Falling.spawn.z", location.getZ());
        config.getConfig().set("Anti-Void-Falling.spawn.direction", location.getDirection());
        config.save();

        plugin.sms(player, config.getConfig().getString("Anti-Void-Falling.message"));
    }
}
