package info.beastsoftware.beastcore.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.Worths;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.PrinterToggleEvent;
import info.beastsoftware.beastcore.feature.IBeastFeature;
import info.beastsoftware.beastcore.feature.IFeatureManager;
import info.beastsoftware.beastcore.feature.impl.*;
import info.beastsoftware.beastcore.manager.MobMergerManager;
import info.beastsoftware.beastcore.manager.NVManager;
import info.beastsoftware.beastcore.printer.IPrinterManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;


@Singleton
public class BeastCoreAPI implements IBeastCoreAPI {


    @Inject
    @Worths
    private IConfig pricesConfig;


    @Inject
    private IFeatureManager featureManager;

    @Inject
    private IPrinterManager printerManager;

    @Inject
    private MobMergerManager mobMergerManager;

    @Inject
    private NVManager nvManager;


    @Override
    public List<IBeastFeature> getAllFeatures() {
        return featureManager.getFeatures();
    }

    @Override
    public IConfig getPricesConfig() {
        return pricesConfig;
    }

    @Override
    public boolean isFeatureEnabled(FeatureType type) {
        return featureManager.getFeatureByType(type).isOn();
    }

    @Override
    public void toggleFeature(FeatureType type) {
        this.featureManager.getFeatureByType(type).toggle();
    }

    @Override
    public boolean isPlayerInCombat(BeastPlayer player) {
        CombatFeature combatFeature = (CombatFeature) featureManager.getFeatureByType(FeatureType.COMBAT);
        return combatFeature.isPlayerInCombat(player);
    }

    @Override
    public int getCombatTimeOfPlayer(BeastPlayer player) {
        CombatFeature combatFeature = (CombatFeature) featureManager.getFeatureByType(FeatureType.COMBAT);
        return combatFeature.getCombatTimeOfPlayer(player);
    }

    @Override
    public String getCombatTimeFormatted(BeastPlayer player) {
        CombatFeature combatFeature = (CombatFeature) featureManager.getFeatureByType(FeatureType.COMBAT);
        return combatFeature.getCombatFormatted(player);
    }

    @Override
    public void setPlayerInCombat(BeastPlayer player, int time) {
        CombatFeature combatFeature = (CombatFeature) featureManager.getFeatureByType(FeatureType.COMBAT);
        combatFeature.setPlayerInCombat(player, time);
    }

    @Override
    public void removePlayerInCombat(BeastPlayer player) {
        CombatFeature combatFeature = (CombatFeature) featureManager.getFeatureByType(FeatureType.COMBAT);
        combatFeature.removePlayerInCombat(player);
    }

    @Override
    public boolean hasCustomDrops(Entity entity) {
        EditDropsFeature editDropsFeature = (EditDropsFeature) featureManager.getFeatureByType(FeatureType.EDIT_DROPS);
        return editDropsFeature.hasCustomDrops(entity);
    }

    @Override
    public ItemStack[] getSimpleCustomDrops(Entity entity) {
        EditDropsFeature editDropsFeature = (EditDropsFeature) featureManager.getFeatureByType(FeatureType.EDIT_DROPS);
        return editDropsFeature.getSimpleDrops(entity);
    }

    @Override
    public ItemStack[] getAdvancedCustomDrops(Entity entity) {
        EditDropsFeature editDropsFeature = (EditDropsFeature) featureManager.getFeatureByType(FeatureType.EDIT_DROPS);
        return editDropsFeature.getAdvancedDrops(entity);
    }

    @Override
    public boolean isOnGappleCooldown(BeastPlayer player) {
        return ((GappleCDFeature) featureManager.getFeatureByType(FeatureType.GAPPLE_CD)).isOnCooldown(player);
    }

    @Override
    public int getGappleCDOfPlayer(BeastPlayer player) {
        return ((GappleCDFeature) featureManager.getFeatureByType(FeatureType.GAPPLE_CD)).getCooldown(player);
    }

    @Override
    public boolean isOnGrace() {
        return ((GraceFeature) this.featureManager.getFeatureByType(FeatureType.GRACE)).isOnGrace();
    }

    @Override
    public int getGraceTimeLeft() {
        return ((GraceFeature) this.featureManager.getFeatureByType(FeatureType.GRACE)).getGrace();
    }

    @Override
    public boolean isItemFiltered(ItemStack itemStack) {
        return ((HopperFilterFeature) this.featureManager.getFeatureByType(FeatureType.HOPPER_FILTER)).isItemFiltered(itemStack);
    }


    @Override
    public boolean hasJellyLegsEnabled(BeastPlayer player) {
        return ((JellyLegsFeature) this.featureManager.getFeatureByType(FeatureType.JELLY_LEGS)).hasJellyLegsEnabled(player);
    }

    @Override
    public void killAllMergedMobs() {
        this.getMobsManager().killAll();
    }

    @Override
    public MobMergerManager getMobsManager() {
        return this.mobMergerManager;
    }


    @Override
    public void removeAllPlayersInPrinter() {
        this.printerManager.disableForAll();
    }

    @Override
    public void toggleOnPrinter(BeastPlayer player) {
        Bukkit.getPluginManager().callEvent(new PrinterToggleEvent(true, player));
    }

    @Override
    public void toggleOffPrinter(BeastPlayer player) {
        Bukkit.getPluginManager().callEvent(new PrinterToggleEvent(false, player));
    }

    @Override
    public boolean isOnPrinterMode(BeastPlayer player) {
        return ((PrinterFeature) this.getFeature(FeatureType.PRINTER)).isOnPrinter(player);
    }

    @Override
    public IBeastFeature getFeature(FeatureType featureType) {
        return featureManager.getFeatureByType(featureType);
    }

    @Override
    public int getPearlCDOfPlayer(BeastPlayer player) {
        return ((EPCooldownFeature) featureManager.getFeatureByType(FeatureType.ENDER_PEARL_COOLDOWN)).getCooldown(player);
    }

    @Override
    public boolean isExpBottle(ItemStack itemStack) {
        return ((ExpWithdrawFeature) featureManager.getFeatureByType(FeatureType.EXP_WITHDRAW)).isExpBottle(itemStack);
    }

    @Override
    public NVManager getNvManager() {
        return nvManager;
    }
}
