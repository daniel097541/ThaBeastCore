package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.Placeholders;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.placeholder.Papi;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;

@Singleton
public class PlaceholdersFeature extends AbstractFeature {

    private Papi papi;


    @Inject
    public PlaceholdersFeature(@Placeholders IConfig config) {
        super(config, FeatureType.PLACEHOLDERS);

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            this.papi = new Papi(config);
            Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &ePlaceholderAPI hooked!"));
        }

    }
}
