package info.beastsoftware.core.features.listener;

import info.beastsoftware.core.common.PluginAccessor;
import info.beastsoftware.core.common.Registrable;
import info.beastsoftware.core.common.Toggable;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public interface FeatureListener extends Toggable, Listener, Registrable, PluginAccessor {

    @Override
    default void register() {
        Bukkit.getScheduler().runTaskLater(this.getPlugin(), () -> {
            Bukkit.getPluginManager().registerEvents(this, this.getPlugin());
        }, 2L);
    }

    @Override
    default void unRegister(){
        HandlerList.unregisterAll(this);
    }
}
