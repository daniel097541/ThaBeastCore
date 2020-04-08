package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherFeatureListener extends AbstractFeatureListener {
    public WeatherFeatureListener(IConfig config) {
        super(config, FeatureType.WEATHER);
    }


    @EventHandler
    public void onWeather(WeatherChangeEvent event){
        if(!isOn() || event.isCancelled()) return;
        event.setCancelled(true);
        event.getWorld().setWeatherDuration(0);
    }


}
