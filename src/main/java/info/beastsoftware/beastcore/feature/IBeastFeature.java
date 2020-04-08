package info.beastsoftware.beastcore.feature;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.command.IBeastCommand;
import org.bukkit.Bukkit;

public interface IBeastFeature extends Toggable {


    default void reload(){
        IConfig config = this.getConfig();
        if(config != null){
            config.reload();
        }

        IDataConfig dataConfig = this.getDataConfig();
        if(dataConfig != null){
            dataConfig.reload();
        }
    }

    IDataConfig getDataConfig();

    IFeatureListener getListener();

    IBeastCommand getCommand();


    default void disableAll(){
        if(this.getListener() != null){
            getListener().unregister();
        }

        if(this.getCommand() != null){
            this.getCommand().unregister();
        }

        this.broadcastUnRegistered();
    }



    default void enableAll(){
        if(this.getListener() != null){
            this.getListener().autoRegister();
        }

        if(this.getCommand() != null){
            this.getCommand().autoRegister();
        }

        this.broadcastRegistered();
    }



    default void broadcastRegistered() {
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &eFeature " + getFeatureType().toString() + " &aenabled!"));
    }

    default void broadcastUnRegistered() {
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &eFeature " + getFeatureType().toString() + " &cDisabled!"));
    }

    default boolean isOn() {
        return getConfig().getConfig().getBoolean(getFeatureType().getPath());
    }

    default void toggle(){
        boolean on = isOn();

        if(on){
            this.disableAll();
        }
        else{
            this.enableAll();
        }

        this.getConfig().getConfig().set(this.getFeatureType().getPath(),!on);
        this.getConfig().save();
    }

}
