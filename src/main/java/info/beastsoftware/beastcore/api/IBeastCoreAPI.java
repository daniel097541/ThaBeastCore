package info.beastsoftware.beastcore.api;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.IBeastFeature;
import info.beastsoftware.beastcore.manager.NVManager;
import info.beastsoftware.beastcore.service.StackedMobsService;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.api.BeastFactionsHookAPI;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;


public interface IBeastCoreAPI {


    List<IBeastFeature> getAllFeatures();

    IConfig getPricesConfig();


    default BeastPlayer getPlayerByName(String name) {
        return this.getPlayer(Bukkit.getOfflinePlayer(name).getUniqueId());
    }

    default BeastPlayer getPlayer(Player player) {
        return this.getPlayer(player.getUniqueId());
    }

    default BeastPlayer getPlayer(UUID uuid) {
        return BeastFactionsHookAPI.getPlayerByUUID(uuid);
    }

    // FEATURES
    boolean isFeatureEnabled(FeatureType type);

    void toggleFeature(FeatureType type);

    //COMBAT
    boolean isPlayerInCombat(BeastPlayer player);

    int getCombatTimeOfPlayer(BeastPlayer player);

    String getCombatTimeFormatted(BeastPlayer player);

    void setPlayerInCombat(BeastPlayer player, int time);

    void removePlayerInCombat(BeastPlayer player);

    //EDIT DROPS
    boolean hasCustomDrops(Entity entity);

    ItemStack[] getSimpleCustomDrops(Entity entity);

    ItemStack[] getAdvancedCustomDrops(Entity entity);


    // GAPPLE CD
    boolean isOnGappleCooldown(BeastPlayer player);

    int getGappleCDOfPlayer(BeastPlayer player);


    // GRACE
    boolean isOnGrace();

    int getGraceTimeLeft();


    // HOPPER FILTER
    boolean isItemFiltered(ItemStack itemStack);


    // JELLY LEGS
    boolean hasJellyLegsEnabled(BeastPlayer player);


    // MOB MERGER
    void killAllMergedMobs();

    StackedMobsService getMobsService();


    // PRINTER
    void removeAllPlayersInPrinter();

    void toggleOnPrinter(BeastPlayer player);

    void toggleOffPrinter(BeastPlayer player);

    boolean isOnPrinterMode(BeastPlayer player);

    IBeastFeature getFeature(FeatureType featureType);

    int getPearlCDOfPlayer(BeastPlayer player);


    //EXP BOTTLE
    boolean isExpBottle(ItemStack itemStack);


    NVManager getNvManager();


    default boolean isOnNv(BeastPlayer player) {
        return getNvManager().isOnNv(player);
    }


    default void removeFromNv(BeastPlayer player) {
        this.getNvManager().removeFromNv(player);
    }


    default void addToNv(BeastPlayer player) {
        this.getNvManager().addToNv(player);
    }

}
