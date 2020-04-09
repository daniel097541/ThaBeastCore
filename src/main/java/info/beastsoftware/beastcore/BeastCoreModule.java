package info.beastsoftware.beastcore;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import info.beastsoftware.beastcore.annotations.configs.CropHoppersData;
import info.beastsoftware.beastcore.annotations.configs.MainConfig;
import info.beastsoftware.beastcore.annotations.features.*;
import info.beastsoftware.beastcore.api.BeastCoreAPI;
import info.beastsoftware.beastcore.api.IBeastCoreAPI;
import info.beastsoftware.beastcore.beastutils.config.BeastConfig;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.configuration.data.*;
import info.beastsoftware.beastcore.configuration.paths.*;
import info.beastsoftware.beastcore.feature.FeatureManager;
import info.beastsoftware.beastcore.feature.IBeastFeature;
import info.beastsoftware.beastcore.feature.IFeatureManager;
import info.beastsoftware.beastcore.feature.impl.*;
import info.beastsoftware.beastcore.manager.*;
import info.beastsoftware.beastcore.printer.IPrinterManager;
import info.beastsoftware.beastcore.printer.PrinterManager;
import info.beastsoftware.hookcore.service.FactionServiceImpl;
import info.beastsoftware.hookcore.service.FactionsService;
import info.beastsoftware.hookcore.service.PlayerService;
import info.beastsoftware.hookcore.service.PlayerServiceImpl;

import java.io.File;

public class BeastCoreModule extends AbstractModule {


    private final BeastCore plugin;


    private final IHookManager hookManager;

    private final PlayerService playerService = PlayerServiceImpl.getInstance();

    private final FactionsService factionsService = FactionServiceImpl.getInstance();

    BeastCoreModule(BeastCore plugin) {
        this.plugin = plugin;
        this.hookManager = new HookManager(this.plugin, factionsService);
    }

    Injector createInjector() {
        return Guice.createInjector(this);
    }


    @Override
    protected void configure() {
        this.bind(BeastCore.class).toInstance(this.plugin);
        this.bind(IHookManager.class).toInstance(this.hookManager);
        this.bind(PlayerService.class).toInstance(this.playerService);
        this.bind(FactionsService.class).toInstance(this.factionsService);


        this.bind(IPvPManager.class).to(PvPManager.class);
        this.bind(ICombatNPCManager.class).to(CombatNPCManager.class);
        this.bind(ICombatCooldownManager.class).to(CombatCooldownManager.class);
        this.bind(IPrinterManager.class).to(PrinterManager.class);
        this.bind(NVManager.class).to(NVManagerImpl.class);
        this.bind(HomesManager.class).to(HomesManagerImpl.class);

        /// DATA CONFIG BINDING
        String alertsFolder = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "F-Alerts-Data";
        this.bind(IDataConfig.class).annotatedWith(Alerts.class).toInstance(new AlertsDataConfig(alertsFolder));
        String dropsFolder = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "MobDrops";
        this.bind(IDataConfig.class).annotatedWith(EditDrops.class).toInstance(new EditDropsDataConfig(dropsFolder));
        String voidChests = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "VoidChests";
        this.bind(IDataConfig.class).annotatedWith(VoidChests.class).toInstance(new VoidChestsDataConfig(voidChests));
        String mobHoppers = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "MobHoppers";
        this.bind(IDataConfig.class).annotatedWith(MobHoppers.class).toInstance(new MobHoppersDataConfig(mobHoppers));
        String fpsBooster = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "FpsBooster";
        this.bind(IDataConfig.class).annotatedWith(FPS.class).toInstance(new FpsBoosterDataConfig(fpsBooster));
        String itemFilter = plugin.getDataFolder() + File.separator + "DataFiles" + File.separator + "Item-Filter";
        this.bind(IDataConfig.class).annotatedWith(ItemFilter.class).toInstance(new ItemFilterDataConfig(itemFilter));

        this.bind(MobMergerManager.class).to(MobMergerManagerImpl.class);

        /// CONFIG BINDING
        this.bind(IConfig.class).annotatedWith(MainConfig.class).toInstance(new info.beastsoftware.beastcore.configuration.MainConfig("config.yml"));
        this.bind(IConfig.class).annotatedWith(Alerts.class).toInstance(new BeastConfig("Faction-Alerts.yml", new AlertsPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(AntiItemCraft.class).toInstance(new BeastConfig("AntiItemCraft.yml", new AntiItemCraftPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(AnvilColor.class).toInstance(new BeastConfig("Anvil-Color-Rename.yml", new AnvilColorRenamePathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(BeastTools.class).toInstance(new BeastConfig("Lightning-Tools.yml", new BeastToolsPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(ChunkBusters.class).toInstance(new BeastConfig("ChunkBusters.yml", new ChunkBustersPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(Combat.class).toInstance(new BeastConfig("CombatTag.yml", new CombatPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(EditDrops.class).toInstance(new BeastConfig("EditDrops.yml", new EditDropsPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(ExpWithdraw.class).toInstance(new BeastConfig("Exp-Withdraw.yml", new ExpWithdrawPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(HopperFilter.class).toInstance(new BeastConfig("HopperFilter.yml", new HopperFilterPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(MobMerger.class).toInstance(new BeastConfig("MobMerger.yml", new MergePathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(Grace.class).toInstance(new BeastConfig("GraceConfig.yml", new GracePathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(CropHoppers.class).toInstance(new BeastConfig("Crop-Hoppers.yml", new CropHoppersPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(MobHoppers.class).toInstance(new BeastConfig("Mob-Hoppers.yml", new MobHopperPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(VoidChests.class).toInstance(new BeastConfig("Void-CHests.yml", new VoidChestPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(FPS.class).toInstance(new BeastConfig("FPS-Booster.yml", new FPSBoosterPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(Printer.class).toInstance(new BeastConfig("Printer-Mode.yml", new PriterPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(MoneyWithdraw.class).toInstance(new BeastConfig("Money-Withdraw.yml", new MoneyWithdrawPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(Wilderness.class).toInstance(new BeastConfig("Wilderness.yml", new WildPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(ItemFilter.class).toInstance(new BeastConfig("Item-Filter.yml", new ItemFilterPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(LapisFiller.class).toInstance(new BeastConfig("Lapis-Lazuli-Filler.yml", new LapisFillerPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(PotStacker.class).toInstance(new BeastConfig("Potion-Stacker.yml", new PotionStackerPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(Potion.class).toInstance(new BeastConfig("Potion.yml", new PotionsPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(TnTFill.class).toInstance(new BeastConfig("tnt-fill.yml", new TnTFillPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(MainMenu.class).toInstance(new BeastConfig("Main-Menu.yml", new FeaturesMenuPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(Worths.class).toInstance(new BeastConfig("Worths.yml", new WorthPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(Placeholders.class).toInstance(new BeastConfig("Placeholders.yml", new PlaceholdersPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(TickCounter.class).toInstance(new BeastConfig("Tick-Counter.yml", new RepeaterTicksCounterPathColl(), this.plugin));
        this.bind(IConfig.class).annotatedWith(CommandCooldown.class).toInstance(new BeastConfig("Command-Cooldowns.yml", new CommandCooldownsPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(NoRespawnScreen.class).toInstance(new BeastConfig("No-Respawn-Screen.yml", new NoRespawnScreenPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(Debuff.class).toInstance(new BeastConfig("Potion-Debuff.yml", new DebuffPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(Enchant.class).toInstance(new BeastConfig("Enchantments.yml", new EnchantPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(LootProtect.class).toInstance(new BeastConfig("Loot-Protection.yml", new LootProtectionPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(NV.class).toInstance(new BeastConfig("Night-Vision.yml", new NightVisionPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(NoEndermanTeleport.class).toInstance(new BeastConfig("No-Enderman-Teleport.yml", new NoEndermanTeleportPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(NoEnemyTeleport.class).toInstance(new BeastConfig("No-Enemy-Teleport.yml", new NoEnemyTeleportPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(NoEnemyHomes.class).toInstance(new BeastConfig("No-Enemy-Homes.yml", new NoEnemyHomesPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(CropHoppersData.class).toInstance(new BeastConfig("Crop-Hoppers-Data.yml", new CropHoppersDataPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(NoBabyZombies.class).toInstance(new BeastConfig("No-Baby-Zombies.yml", new NoBabyZombiesPathColl(), plugin));
        this.bind(IConfig.class).annotatedWith(ThrowableCegss.class).toInstance(new BeastConfig("Throwable-Ceggs.yml", new ThrowableCeggsPathColl(), plugin));

        /// FEATURE BINDINGAntiItemCraft
        this.bind(IBeastFeature.class).annotatedWith(NoExplosionsDamage.class).to(AntiExplosionDamageFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Alerts.class).to(AlertsFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(AntiItemCraft.class).to(AntiItemCraftFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(AntiItemPlace.class).to(AntiItemPlaceFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(AnvilColor.class).to(AnvilColorFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(AutoCannonsLimiter.class).to(AutoCannonsFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(BeastTools.class).to(BeastToolsFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(BottleRecycle.class).to(BottleRecycleFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(CannonProtector.class).to(CannonProtectorFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(ChunkBusters.class).to(ChunkBustersFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoCreeperGlich.class).to(CreeperGlitchFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Combat.class).to(CombatFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(EditDrops.class).to(EditDropsFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(EPCooldown.class).to(EPCooldownFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(ExpWithdraw.class).to(ExpWithdrawFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(ExplodeLava.class).to(ExplodeLavaFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(FLogOut.class).to(FLogOutFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(FlyingPeal.class).to(FlyingPearlFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(FPS.class).to(FPSFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(GappleCD.class).to(GappleCDFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(GolemDeath.class).to(GolemDeathFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(GolemFire.class).to(GolemFireFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Grace.class).to(GraceFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(HideStream.class).to(HideStreamFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(HopperFilter.class).to(HopperFilterFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(ItemBurn.class).to(ItemBurnFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(ItemFilter.class).to(ItemFilterFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(ItemSpawn.class).to(ItemSpawnFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(JellyLegs.class).to(JellyLegsFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(LapisFiller.class).to(LapisFillerFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(LavaSponge.class).to(LavaSpongeFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(MobHoppers.class).to(MobHoppersFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(MobMerger.class).to(MobMergerFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(MoneyWithdraw.class).to(MoneyWithdrawFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoExplosions.class).to(NoExplosionsFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoItemBurn.class).to(NoItemBurnFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoMCMMOGlitch.class).to(NoMCMMOFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NormalGappleCD.class).to(NormalGappleCDFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoSchemBug.class).to(NoSchemFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoSpawnerGuard.class).to(NoSpawnerGuardFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(PearlGlitch.class).to(PearlGlitchFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Printer.class).to(PrinterFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(SandStacker.class).to(SandStackerFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(ServerProtector.class).to(ServerProtectorFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(SpawnerMine.class).to(SpawnerMineFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(TickCounter.class).to(TickCounterFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(VoidChests.class).to(VoidChestsFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(VoidFall.class).to(VoidFallFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(WaterProofBlazes.class).to(WaterBlazesFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(WaterBorder.class).to(WaterBorderFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(WaterRedstone.class).to(WaterRedstoneFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(WaterSponge.class).to(WaterSpongeFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Weather.class).to(WeatherFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Wilderness.class).to(WildernessFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(WorldBorderPearl.class).to(WorldBorderPearlFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(TnTFill.class).to(TnTFillFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(TnTUnFill.class).to(TnTUnfillFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(CommandCooldown.class).to(CommandCooldownFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(CropHoppers.class).to(CropHoppersFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(PotStacker.class).to(PotStackerFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Potion.class).to(PotionFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(MainCommand.class).to(MainCommandFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(MainMenu.class).to(MainMenuFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Placeholders.class).to(PlaceholdersFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoWither.class).to(NoWitherFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoRespawnScreen.class).to(NoRespawnScreenFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Debuff.class).to(DebuffFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Enchant.class).to(EnchantFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(LootProtect.class).to(LootProtectFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NV.class).to(NvFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoEndermanTeleport.class).to(NoEndermanTeleportFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(Creeper.class).to(CreeperFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoEnemyTeleport.class).to(NoEnemyTeleportFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoEnemyHomes.class).to(NoEnemyHomesFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(NoBabyZombies.class).to(NoBabyZombiesFeature.class);
        this.bind(IBeastFeature.class).annotatedWith(ThrowableCegss.class).to(ThrowableCeggsFeature.class);

        /// FEATURE MANAGER
        this.bind(IFeatureManager.class).to(FeatureManager.class);
        /// API
        this.bind(IBeastCoreAPI.class).to(BeastCoreAPI.class);
    }
}
