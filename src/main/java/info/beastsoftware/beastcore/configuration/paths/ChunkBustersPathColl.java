package info.beastsoftware.beastcore.configuration.paths;

import info.beastsoftware.beastcore.beastutils.config.path.impl.PathColl;
import info.beastsoftware.beastcore.struct.CommandType;

import java.util.ArrayList;
import java.util.List;

public class ChunkBustersPathColl extends PathColl {


    public ChunkBustersPathColl() {
        super();
    }

    @Override
    public void init() {
        addPath("Chunk-Busters.enabled", true);
        addPath("Chunk-Busters.disabled-message", "&c(!) &4Chunkbusters are disabled!");
        addPath(CommandType.CHUNKBUSTERS.getEnabledPath(), true);
        addPath("Chunk-Busters.command.permission", "bfc.chunkbusters.give");
        addPath("Chunk-Busters.command.no-permission-msg", "&c(!) &4You dont have permission to use chunkbusters command!");
        addPath("Chunk-Busters.command.player-offline", "&4(!) &cThe player is offline or does not exist!");
        addPath("Chunk-Busters.command.recieve-message", "&eYou got &7{count} &echunkbuster/s !");
        addPath("Chunk-Busters.command.gave-message", "&eYou gave &7{count} &echunkbuster/s to &d{player}");
        addPath("Chunk-Busters.command.no-slot", "&eThe player has not an empty slot to recieve the item!");
        addPath("Chunk-Busters.command.format", "&eCommand ussage: &7/chunkbust give <player> <amount> <chunkbuster_radius>");
        addPath("Chunk-Busters.command.invalid-radius", "&eIntroduce a radius that can be divided by 3 (1x1, 3x3, 6x6, 9x9 ...)");
        addPath("Chunk-Busters.command.invalid-amount", "&cIntroduce a valid radius!");
        List<String> lore = new ArrayList<>();

        lore.add("&eRemove chunks in a radius of:");
        lore.add("&e{radius} &7x &e{radius}");

        addPath("Chunk-Busters.ChunkBuster.item-material", "TNT");
        addPath("Chunk-Busters.ChunkBuster.item-damage", "0");
        addPath("Chunk-Busters.ChunkBuster.item-name", "&dChunk Buster");
        addPath("Chunk-Busters.ChunkBuster.lore", lore);


        List<Integer> radius = new ArrayList<>();
        radius.add(1);
        radius.add(3);
        radius.add(9);
        addPath("Chunk-Busters.Settings.break-other-factions-chunks-in-radius", false);
        addPath("Chunk-Busters.Settings.deny-placing-outside-own-faction-land", true);
        addPath("Chunk-Busters.Settings.deny-placing-outside-own-faction-land-message", "&cYou cant place chunkbusters outside of your claims!");
        addPath("Chunk-Busters.Settings.radius", radius);
        addPath("Chunk-Busters.Settings.place-beastCooldown", 300);
        addPath("Chunk-Busters.Settings.broadcast-beastCooldown", true);
        addPath("Chunk-Busters.Settings.place-beastCooldown-message", "&eYou cant place another chunkbuster until the beastCooldown ends! &d{beastCooldown} s");
        addPath("Chunk-Busters.Settings.explode-beastCooldown", 10);
        addPath("Chunk-Busters.Settings.explode-beastCooldown-message", "&dThe chunk will be removed in.. {seconds} s");
        addPath("Chunk-Busters.Settings.explode-message", "&eThe Chunk was removed!");


        List<String> ignored = new ArrayList<>();
        ignored.add("BEDROCK");
        ignored.add("BEACON");
        addPath("Chunk-Busters.Settings.ignored-blocks", ignored);


    }
}
