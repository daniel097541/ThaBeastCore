package info.beastsoftware.beastcore.feature;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.*;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class FeatureManager implements IFeatureManager {


    @Inject
    @NoExplosionsDamage
    private IBeastFeature noExplosionsDamageFeature;

    @Inject
    @Alerts
    private IBeastFeature alertsFeature;

    @Inject
    @AntiItemCraft
    private IBeastFeature antiItemCraftFeature;

    @Inject
    @AntiItemPlace
    private IBeastFeature antiItemPlaceFeature;

    @Inject
    @AnvilColor
    private IBeastFeature anvilColorFeature;

    @Inject
    @AutoCannonsLimiter
    private IBeastFeature autoCannonsLimiter;

    @Inject
    @BeastTools
    private IBeastFeature beastToolsFeature;

    @Inject
    @BottleRecycle
    private IBeastFeature bottleRecycle;

    @Inject
    @CannonProtector
    private IBeastFeature cannonProtector;

    @ChunkBusters
    @Inject
    private IBeastFeature chunkBusters;

    @Combat
    @Inject
    private IBeastFeature combat;


    @CommandCooldown
    @Inject
    private IBeastFeature commandCooldown;

    @NoCreeperGlich
    @Inject
    private IBeastFeature creeperGlitch;

    @CropHoppers
    @Inject
    private IBeastFeature cropHoppers;

    @EditDrops
    @Inject
    private IBeastFeature editDrops;

    @EPCooldown
    @Inject
    private IBeastFeature epCooldown;

    @ExplodeLava
    @Inject
    private IBeastFeature explodeLava;

    @ExpWithdraw
    @Inject
    private IBeastFeature expWithdraw;

    @FLogOut
    @Inject
    private IBeastFeature fLogout;

    @FlyingPeal
    @Inject
    private IBeastFeature flyingPearl;

    @FPS
    @Inject
    private IBeastFeature fps;

    @GappleCD
    @Inject
    private IBeastFeature gappleCD;

    @GolemDeath
    @Inject
    private IBeastFeature golemDeath;

    @GolemFire
    @Inject
    private IBeastFeature golemFire;

    @Grace
    @Inject
    private IBeastFeature grace;

    @HideStream
    @Inject
    private IBeastFeature hideStream;

    @HopperFilter
    @Inject
    private IBeastFeature hopperFilter;

    @ItemBurn
    @Inject
    private IBeastFeature itemBurn;

    @ItemFilter
    @Inject
    private IBeastFeature itemFilter;

    @ItemSpawn
    @Inject
    private IBeastFeature itemSpawn;


    @JellyLegs
    @Inject
    private IBeastFeature jellyLegs;


    @LapisFiller
    @Inject
    private IBeastFeature lapisFiller;


    @LavaSponge
    @Inject
    private IBeastFeature lavaSponge;


    @MobHoppers
    @Inject
    private IBeastFeature mobHoppers;

    @MobMerger
    @Inject
    private IBeastFeature mobMerger;

    @MoneyWithdraw
    @Inject
    private IBeastFeature moneyWithdraw;

    @NoExplosions
    @Inject
    private IBeastFeature noExplosions;

    @NoItemBurn
    @Inject
    private IBeastFeature noItemBurn;


    @NoMCMMOGlitch
    @Inject
    private IBeastFeature noMCMMO;

    @NormalGappleCD
    @Inject
    private IBeastFeature normalGappleCD;

    @NoSchemBug
    @Inject
    private IBeastFeature noSchem;


    @NoSpawnerGuard
    @Inject
    private IBeastFeature noSpawnerGuard;


    @PearlGlitch
    @Inject
    private IBeastFeature pearlGlitch;


    @Printer
    @Inject
    private IBeastFeature printer;

    @SandStacker
    @Inject
    private IBeastFeature sandStacker;


    @ServerProtector
    @Inject
    private IBeastFeature serverProtector;


    @SpawnerMine
    @Inject
    private IBeastFeature spawnerMine;


    @TickCounter
    @Inject
    private IBeastFeature tickCounter;


    @TnTFill
    @Inject
    private IBeastFeature tntfill;

    @TnTUnFill
    @Inject
    private IBeastFeature tntUnfill;


    @VoidChests
    @Inject
    private IBeastFeature voidChests;

    @VoidFall
    @Inject
    private IBeastFeature voidFall;


    @WaterProofBlazes
    @Inject
    private IBeastFeature waterBlazes;


    @WaterBorder
    @Inject
    private IBeastFeature waterBorder;


    @WaterSponge
    @Inject
    private IBeastFeature waterSponge;


    @WaterRedstone
    @Inject
    private IBeastFeature waterRedstone;


    @Weather
    @Inject
    private IBeastFeature weather;

    @Wilderness
    @Inject
    private IBeastFeature wild;


    @WorldBorderPearl
    @Inject
    private IBeastFeature worldBorderPearl;

    @PotStacker
    @Inject
    private IBeastFeature potStacker;


    @Potion
    @Inject
    private IBeastFeature potion;



    @Placeholders
    @Inject
    private IBeastFeature placeholders;


    @MainMenu
    @Inject
    private IBeastFeature mainMenu;


    @MainCommand
    @Inject
    private IBeastFeature mainCommand;


    @NoWither
    @Inject
    private IBeastFeature noWitherFeature;


    @NoRespawnScreen
    @Inject
    private IBeastFeature noRespawnScreen;


    @Debuff
    @Inject
    private IBeastFeature debuff;

    @Enchant
    @Inject
    private IBeastFeature enchants;

    @LootProtect
    @Inject
    private IBeastFeature lootProtect;

    @Inject
    @NV
    private IBeastFeature nighVision;

    @Inject
    @NoEndermanTeleport
    private IBeastFeature noEndermanTeleport;

    @Inject
    @Creeper
    private IBeastFeature creeperFeature;

    @Inject
    @NoEnemyTeleport
    private IBeastFeature noEnemyTeleportFeature;

    @Inject
    @NoEnemyHomes
    private IBeastFeature noEnemyHomes;

    @Inject
    @NoBabyZombies
    private IBeastFeature noBabyZombies;

    @Inject
    @ThrowableCegss
    private IBeastFeature throwableCeggs;

    public FeatureManager() {
        Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&dBeastCore &7>> &eFeature manager initialized!"));
    }

    @Override
    public List<IBeastFeature> getFeatures() {

        Class clazz = this.getClass();
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());

        return fields
                .parallelStream()
                .peek(f -> f.setAccessible(true))
                .map(f -> {
                    IBeastFeature feature = null;
                    try {
                        feature = (IBeastFeature) f.get(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return feature;
                })
                .collect(Collectors.toList());

    }
}
