package info.beastsoftware.beastcore.configuration.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.BeastConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.configuration.MainConfig;
import info.beastsoftware.beastcore.configuration.paths.*;
import info.beastsoftware.beastcore.struct.ConfigType;
import org.bukkit.Bukkit;

import java.util.HashMap;

@Singleton
public class ConfigManager implements IConfigManager {

    private HashMap<String, IConfig> configs = new HashMap<>();

    private final BeastCore plugin;

    @Inject
    public ConfigManager(BeastCore plugin) {
        this.plugin = plugin;
        this.init();
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &eAll configurations created!"));
    }




    @Override
    public IConfig getConfig(String name) {
        return this.configs.get(name);
    }

    @Override
    public void init() {
        this.addConfig(ConfigType.MAIN_CONFIG.toString(), new MainConfig("config.yml"));
        this.addConfig(ConfigType.ALERTS_CONFIG.toString(), new BeastConfig("Faction-Alerts.yml", new AlertsPathColl(), this.plugin));
        this.addConfig(ConfigType.ANTIITEMCRAFT_CONFIG.toString(), new BeastConfig("AntiItemCraft.yml", new AntiItemCraftPathColl(), this.plugin));
        this.addConfig(ConfigType.COMBAT_CONFIG.toString(), new BeastConfig("CombatTag.yml", new CombatPathColl(), this.plugin));
        this.addConfig(ConfigType.DROPS_CONFIG.toString(), new BeastConfig("EditDropsConfig.yml", new EditDropsPathColl(), this.plugin));
        this.addConfig(ConfigType.HOPPER_FILTER_CONFIG.toString(), new BeastConfig("HopperFilter.yml", new HopperFilterPathColl(), this.plugin));
        this.addConfig(ConfigType.MERGE_CONFIG.toString(), new BeastConfig("MobMerger.yml", new MergePathColl(), this.plugin));
        this.addConfig(ConfigType.BEAST_TOOLS_CONFIG.toString(), new BeastConfig("Lightning-Tools.yml", new BeastToolsPathColl(), this.plugin));
        this.addConfig(ConfigType.COMMAND_COOLDOWN.toString(), new BeastConfig("command-Cooldowns.yml", new CommandCooldownsPathColl(), this.plugin));
        this.addConfig(ConfigType.CHUNKBUSTER.toString(), new BeastConfig("ChunkBusters.yml", new ChunkBustersPathColl(), this.plugin));
        this.addConfig(ConfigType.GRACE.toString(), new BeastConfig("GraceConfig.yml", new GracePathColl(), this.plugin));
        this.addConfig(ConfigType.FEATURES_MENU.toString(), new BeastConfig("Main-Menu.yml", new FeaturesMenuPathColl(), this.plugin));
        this.addConfig(ConfigType.CROP_HOPPERS.toString(), new BeastConfig("Crop-Hoppers.yml", new CropHoppersPathColl(), this.plugin));
        this.addConfig(ConfigType.MOB_HOPPERS.toString(), new BeastConfig("Mob-Hoppers.yml", new MobHopperPathColl(), this.plugin));
        this.addConfig(ConfigType.VOID_CHESTS.toString(), new BeastConfig("Void-Chests.yml", new VoidChestPathColl(), this.plugin));
        this.addConfig(ConfigType.TNTFILL.toString(), new BeastConfig("tnt-fill.yml", new TnTFillPathColl(), this.plugin));
        this.addConfig(ConfigType.FPS_BOOSTER.toString(), new BeastConfig("FPS-Booster.yml", new FPSBoosterPathColl(), this.plugin));
        this.addConfig(ConfigType.EXP_BOTTLE.toString(), new BeastConfig("Exp-Withdraw.yml", new ExpWithdrawPathColl(), this.plugin));
        this.addConfig(ConfigType.ENCHANT_CONFIG.toString(), new BeastConfig("Enchant-Disenchant.yml", new EnchantPathColl(), this.plugin));
        this.addConfig(ConfigType.WORTH.toString(), new BeastConfig("Worths.yml", new WorthPathColl(), this.plugin));
        this.addConfig(ConfigType.PRINTER_CONFIG.toString(), new BeastConfig("Printer-Mode.yml", new PriterPathColl(), this.plugin));
        this.addConfig(ConfigType.WITHDRAW_CONFIG.toString(), new BeastConfig("Money-Withdraw.yml", new MoneyWithdrawPathColl(), this.plugin));
        this.addConfig(ConfigType.WILD.toString(), new BeastConfig("Wilderness.yml", new WildPathColl(), this.plugin));
        this.addConfig(ConfigType.POTION.toString(), new BeastConfig("Potion.yml", new PotionsPathColl(), this.plugin));
        this.addConfig(ConfigType.ITEMFILTER.toString(), new BeastConfig("Item-Filter.yml", new ItemFilterPathColl(), this.plugin));
        this.addConfig(ConfigType.TICK_COUNTER.toString(), new BeastConfig("Tick-Counter.yml", new RepeaterTicksCounterPathColl(), this.plugin));
        this.addConfig(ConfigType.PLACEHOLDERS.toString(), new BeastConfig("Placeholders.yml", new PlaceholdersPathColl(), this.plugin));
        this.addConfig(ConfigType.LAPIS_FILLER.toString(), new BeastConfig("Lapis-Lazuli-Filler.yml", new LapisFillerPathColl(), this.plugin));
        this.addConfig(ConfigType.POTION_STACKER.toString(), new BeastConfig("Potion-Stacker.yml", new PotionStackerPathColl(), this.plugin));
        this.addConfig(ConfigType.COLOR_ANVIL.toString(), new BeastConfig("Anvil-Color-Rename.yml", new AnvilColorRenamePathColl(), this.plugin));
    }

    @Override
    public void addConfig(String name, IConfig config) {
        this.configs.put(name, config);
    }

    @Override
    public void reloadConfigs() {
        for (String name : configs.keySet())
            reloadConfig(name);
    }

    @Override
    public void reloadConfig(String name) {
        getConfig(name).reload();
    }

    @Override
    public HashMap<String, IConfig> getConfigs() {
        return configs;
    }

    @Override
    public IConfig getByType(ConfigType configType) {
        return getConfig(configType.toString());
    }
}
