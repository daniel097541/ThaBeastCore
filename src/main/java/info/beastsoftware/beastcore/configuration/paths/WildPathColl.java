package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import org.bukkit.Material;
import org.bukkit.block.Biome;

import java.util.ArrayList;
import java.util.List;

public class WildPathColl extends PathColl {


    @Override
    public void init() {

        addPath("enabled", true);


        String path = "Command.Wilderness.";

        addPath(path + "permission", "btf.wilderness");
        addPath(path + "no-permission-msg", "&cYou dont have permission!");
        addPath(path + "delay", 5);
        addPath(path + "countdown", true);
        addPath(path + "countdown-message", "&eExecuting wild in &7{seconds} &edont move!");
        addPath(path + "cancelled-due-movement", "&cYou moved while warming up!");
        addPath(path + "deny-teleport-to-factions", true);
        addPath(path + "max-tries-to-find-location", 10000);
        addPath(path + "no-suitable-location", "&cWe didnt find any suitable location, try again!");
        addPath(path + "teleporting", "&eTeleporting...");
        List<String> blocks = new ArrayList<>();
        blocks.add(Material.WATER.toString());
        blocks.add(Material.LAVA.toString());

        List<String> biomes = new ArrayList<>();
        biomes.add(Biome.OCEAN.toString());
        biomes.add(Biome.JUNGLE.toString());
        biomes.add(Biome.DEEP_OCEAN.toString());

        addPath(path + "blacklisted-blocks", blocks);
        addPath(path + "blacklisted-biomes", biomes);
        addPath(path + "world-not-allowed", "&cThe world you are in, does not allow &7/wild &c!");
        addPath(path + "Worlds.world.max-radius", 6000);
        addPath(path + "Worlds.world.min-radius", 1000);
        addPath(path + "Worlds.other_world_name.max-radius", 3000);
        addPath(path + "Worlds.other_world_name.min-radius", 600);

    }
}
