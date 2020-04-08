package info.beastsoftware.beastcore.feature;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.entity.APIAccessor;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public interface IListener extends Registrable, Listener, APIAccessor {

    default void autoRegister(){
        Bukkit.getPluginManager().registerEvents(this, BeastCore.getInstance());
    }


    default void unregister(){
        HandlerList.unregisterAll(this);
    }

}
