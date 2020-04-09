package info.beastsoftware.beastcore;

import com.google.inject.Inject;
import com.google.inject.Injector;
import info.beastsoftware.beastcore.api.IBeastCoreAPI;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.feature.IFeatureManager;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.hookcore.service.FactionsService;
import info.beastsoftware.hookcore.service.PlayerService;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public final class BeastCore extends JavaPlugin {

    public static String s = "%%__USER__%%";
    public static String d = "%%__NONCE__%%";


    private static BeastCore instance;


    @Inject
    private IHookManager hookManager;

    @Inject
    private IFeatureManager featureManager;

    @Inject
    private IBeastCoreAPI api;

    private BeastCoreModule beastCoreModule;

    @Inject
    private PlayerService playerService;

    @Inject
    private FactionsService factionsService;

    private void inject() {
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &eInjecting modules!"));
        this.beastCoreModule = new BeastCoreModule(this);
        Injector injector = beastCoreModule.createInjector();
        injector.injectMembers(this);
    }


    @Override
    public void onEnable() {
        BeastCore.instance = this;
        createConfig();
        inject();
    }

    @Override
    public void onDisable() {
        try {
            this.api.removeAllPlayersInPrinter();
            this.api.killAllMergedMobs();
        } catch (Exception ignored) {
        }
    }


    private void createConfig() {
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists())
            saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &eMain config created!"));
    }

    //sms player
    public void sms(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    //sms command sender
    public void sms(CommandSender player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }


    public void reload() {
        this.featureManager.reloadAllFeatures();
    }

    public static BeastCore getInstance() {
        return instance;
    }

}
