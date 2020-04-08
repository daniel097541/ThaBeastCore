package info.beastsoftware.beastcore.feature;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.entity.APIAccessor;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public interface IFeatureListener extends IListener, Checkable, Toggable, APIAccessor {

    default void autoRegister(){
        Bukkit.getPluginManager().registerEvents(this, BeastCore.getInstance());
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &e" + getFeatureType().toString() + " &blistener autoregistered!"));
    }

    default void unregister(){
        HandlerList.unregisterAll(this);
    }

    FeatureType getFeatureType();

    IConfig getConfig();

}
