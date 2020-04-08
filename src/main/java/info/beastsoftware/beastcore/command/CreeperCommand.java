package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CreeperCommand extends BeastCommand {
    public CreeperCommand(IConfig config, BeastCore plugin) {
        super(plugin, config, CommandType.CREEPER, FeatureType.CREEPER);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        //not a player
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        //spawn creeper
        String message = config.getConfig().getString("Commands.Creeper.message");
        String creeperName = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Commands.Creeper.creeper-custom-name"));
        creeperName = stringUtils.replacePlaceholder(creeperName, "{player}", player.getName());
        Location location = player.getLocation();
        Entity ent = player.getWorld().spawnEntity(location, EntityType.CREEPER);
        //custom name

        if (!Bukkit.getVersion().contains("1.7.")) {
            ent.setCustomName(creeperName);
            ent.setCustomNameVisible(config.getConfig().getBoolean(""));
        }
        //send message
        plugin.sms(player, message);
    }
}
