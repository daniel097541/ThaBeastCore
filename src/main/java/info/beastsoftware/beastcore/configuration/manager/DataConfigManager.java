package info.beastsoftware.beastcore.configuration.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.configuration.data.*;
import info.beastsoftware.beastcore.struct.ConfigType;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.HashMap;


@Singleton
public class DataConfigManager implements IDataConfigManager {


    private final HashMap<String, IDataConfig> dataConfigs = new HashMap<>();
    private final BeastCore plugin;


    @Inject
    public DataConfigManager(BeastCore plugin) {
        this.plugin = plugin;
        this.init();
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &eData configs created!"));
    }

    @Override
    public void addConfig(String name, IDataConfig dataConfig) {
        dataConfigs.put(name, dataConfig);
    }

    @Override
    public IDataConfig getConfig(String name) {
        return dataConfigs.get(name);
    }

    @Override
    public IDataConfig getByType(ConfigType type) {
        return this.getConfig(type.toString());
    }

    @Override
    public HashMap<String, IDataConfig> getConfigs() {
        return dataConfigs;
    }

    @Override
    public void init() {
        String alertsFolder = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "F-Alerts-Data";
        this.addConfig(ConfigType.ALERTS_CONFIG.toString(), new AlertsDataConfig(alertsFolder));
        String dropsFolder = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "MobDrops";
        this.addConfig(ConfigType.DROPS_CONFIG.toString(), new EditDropsDataConfig(dropsFolder));
        String voidChests = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "VoidChests";
        this.addConfig(ConfigType.VOID_CHESTS.toString(), new VoidChestsDataConfig(voidChests));
        String mobHoppers = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "MobHoppers";
        this.addConfig(ConfigType.MOB_HOPPERS.toString(), new MobHoppersDataConfig(mobHoppers));
        String fpsBooster = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "FpsBooster";
        this.addConfig(ConfigType.FPS_BOOSTER.toString(), new FpsBoosterDataConfig(fpsBooster));
        String itemFilter = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "Item-Filter";
        this.addConfig(ConfigType.ITEMFILTER.toString(), new ItemFilterDataConfig(itemFilter));
    }
}
