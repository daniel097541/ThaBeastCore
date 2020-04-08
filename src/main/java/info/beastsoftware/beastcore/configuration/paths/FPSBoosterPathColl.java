package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.ButtonType;
import info.beastsoftware.beastcore.struct.CommandType;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class FPSBoosterPathColl extends PathColl {
    @Override
    public void init() {

        addPath("enabled", true);


        String path = "FPS-Booster.";
        String pathCommand = "FPS-Booster.command.";
        String pathGUI = "FPS-Booster.gui.";
        String pathDrops = path + "invisible-drops-list";

        List<String> invisibleDrops = new ArrayList<>();
        invisibleDrops.add(org.bukkit.Material.IRON_INGOT.toString());
        invisibleDrops.add(org.bukkit.Material.ROTTEN_FLESH.toString());


        //PATHS
        String pathSpacer = pathGUI + "Buttons.spacer-button.";
        String pathInvisibleTnTButton = pathGUI + "Buttons." + ButtonType.TNT_TOGGLE.toString();
        String pathInvisibleSandButton = pathGUI + "Buttons." + ButtonType.SAND_TOGGLE.toString();
        String pathInvisibleDropsButton = pathGUI + "Buttons." + ButtonType.DROPS_TOGGLE.toString();
        String pathInvisibleMobsButton = pathGUI + "Buttons." + ButtonType.MOBS_TOGGLE.toString();
        String pathInvisiblePistonButton = pathGUI + "Buttons." + ButtonType.EXPLOSIONS_TOGGLE.toString();
        String pathInvisibleSpawnersButton = pathGUI + "Buttons." + ButtonType.SPAWNERS_TOGGLE.toString();
        String pathInvisibleParticlesButton = pathGUI + "Buttons." + ButtonType.PARTICLES_TOGGLE.toString();

        //MAIN OPTIONS
        addPath(path + ".disabled-message", "&cFps booster was disabled by an operator");

        addPath(CommandType.FPS_BOOSTER.getEnabledPath(), true);

        //COMMAND
        addPath(pathCommand + "permission", "btf.fpsboost.use");
        addPath(pathCommand + "no-permission-msg", "&c(!) &4You dont have permission!");

        //DROP LIST
        addPath(pathDrops, invisibleDrops);


        //BUTTONS
        List<String> loreEnabled = new ArrayList<>();
        List<String> loreDisabled = new ArrayList<>();

        loreDisabled.add("&cDisabled");
        loreEnabled.add("&aEnabled");

        addPath(pathGUI + "enabled-lore", loreEnabled);
        addPath(pathGUI + "disabled-lore", loreDisabled);
        addPath(pathGUI + "size", 27);
        addPath(pathGUI + "title", "&eFps Booster!");


        ///////////// 1.13
        Material matSpacer;
        Material matPistons;
        Material matMobs;
        Material matSpawner;
        Material matFlower;
        try {
            matSpacer = Material.valueOf("STAINED_GLASS_PANE");
            matPistons = Material.valueOf("SULPHUR");
            matMobs = Material.valueOf("MONSTER_EGG");
            matSpawner = Material.valueOf("MOB_SPAWNER");
            matFlower = Material.valueOf("YELLOW_FLOWER");
        } catch (IllegalArgumentException ignored) {
            matSpacer = Material.BLACK_STAINED_GLASS_PANE;
            matPistons = Material.LEGACY_SULPHUR;
            matMobs = Material.CREEPER_SPAWN_EGG;
            matSpawner = Material.SPAWNER;
            matFlower = Material.LEGACY_YELLOW_FLOWER;
        }
        ///////////// 1.13


        addPath(pathSpacer + "material", matSpacer.toString());
        addPath(pathSpacer + "damage", 0);

        addPath(pathInvisibleTnTButton + ".name", "&eInvisible TnT");
        addPath(pathInvisibleTnTButton + ".material", Material.TNT.toString());
        addPath(pathInvisibleTnTButton + ".damage", 0);
        addPath(pathInvisibleTnTButton + ".position", 11);

        addPath(pathInvisibleSandButton + ".name", "&eInvisible Sand");
        addPath(pathInvisibleSandButton + ".material", org.bukkit.Material.SAND.toString());
        addPath(pathInvisibleSandButton + ".damage", 0);
        addPath(pathInvisibleSandButton + ".position", 12);

        addPath(pathInvisiblePistonButton + ".name", "&eInvisible Explosions");
        addPath(pathInvisiblePistonButton + ".material", matPistons.toString());
        addPath(pathInvisiblePistonButton + ".damage", 0);
        addPath(pathInvisiblePistonButton + ".position", 13);

        addPath(pathInvisibleDropsButton + ".name", "&eInvisible Drops");
        addPath(pathInvisibleDropsButton + ".material", Material.GOLD_NUGGET.toString());
        addPath(pathInvisibleDropsButton + ".damage", 0);
        addPath(pathInvisibleDropsButton + ".position", 14);


        addPath(pathInvisibleMobsButton + ".name", "&eInvisible Mobs");
        addPath(pathInvisibleMobsButton + ".material", matMobs.toString());
        addPath(pathInvisibleMobsButton + ".damage", 0);
        addPath(pathInvisibleMobsButton + ".position", 10);

        addPath(pathInvisibleSpawnersButton + ".name", "&eInvisible Spawners");
        addPath(pathInvisibleSpawnersButton + ".material", matSpawner.toString());
        addPath(pathInvisibleSpawnersButton + ".damage", 0);
        addPath(pathInvisibleSpawnersButton + ".position", 16);

        addPath(pathInvisibleParticlesButton + ".name", "&eInvisible Particles");
        addPath(pathInvisibleParticlesButton + ".material", matFlower.toString());
        addPath(pathInvisibleParticlesButton + ".damage", 0);
        addPath(pathInvisibleParticlesButton + ".position", 15);
    }
}
