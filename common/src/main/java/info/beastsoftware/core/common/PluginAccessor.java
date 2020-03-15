package info.beastsoftware.core.common;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public interface PluginAccessor {
    default Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("ThaBeastCore");
    }
}
