package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.cooldown.BeastCooldown;
import info.beastsoftware.beastcore.entity.APIAccessor;
import info.beastsoftware.beastcore.feature.Checkable;
import info.beastsoftware.beastcore.feature.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public interface IBeastCommand extends Registrable, Checkable, APIAccessor {
    void run(CommandSender sender, String[] args);
    BeastCooldown getBeastCooldown();
    String getName();
    void addAlias(String alias);
    default void broadcastUnregistered(){
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &eCommand &4" + getFeatureType().toString() + " &cunregistered!"));
    }
    default void broadcastRegistered(){
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &eCommand &4" + getFeatureType().toString() + " &aregistered!"));
    }
}
