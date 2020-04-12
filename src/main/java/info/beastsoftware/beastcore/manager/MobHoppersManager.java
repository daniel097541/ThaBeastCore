package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.entity.MobHopper;
import info.beastsoftware.hookcore.entity.BeastChunk;
import info.beastsoftware.hookcore.entity.BeastChunkImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;

import java.util.*;
import java.util.stream.Collectors;

public class MobHoppersManager {


    private final Map<String, Map<String, Map<BeastChunk, Map<String, MobHopper>>>> indexedData = new HashMap<>();
    private final IConfig dataConfig;

    public MobHoppersManager(IConfig dataConfig) {
        this.dataConfig = dataConfig;
        this.load();
    }

    private void load() {
        String path = "data";

        ConfigurationSection section = this.dataConfig.getConfig().getConfigurationSection(path);

        if (Objects.isNull(section)) {
            this.dataConfig.getConfig().set("data", new ArrayList<>());
            this.dataConfig.save();
            return;
        }

        for (String idString : section.getKeys(false)) {

            //get chest info from players config
            String worldName = this.dataConfig.getConfig().getString(path + "." + idString + ".world");
            int x = this.dataConfig.getConfig().getInt(path + "." + idString + ".x");
            int y = this.dataConfig.getConfig().getInt(path + "." + idString + ".y");
            int z = this.dataConfig.getConfig().getInt(path + "." + idString + ".z");
            String type = this.dataConfig.getConfig().getString(path + "." + idString + ".type");
            World world = Bukkit.getWorld(worldName);

            //world = null means world removed or invalid, ignore this section and continue
            if (world == null) continue;

            Location location = new Location(world, x, y, z);

            this.addToCache(new MobHopper(type, location));
        }
    }


    public void save(MobHopper mobHopper) {
        this.saveInConfig(mobHopper);
        this.addToCache(mobHopper);
    }

    private void saveInConfig(MobHopper mobHopper) {
        Location location = mobHopper.getLocation();
        String worldName = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        String path = "data." + mobHopper.getIndex() + ".";

        this.dataConfig.getConfig().set(path + "world", worldName);
        this.dataConfig.getConfig().set(path + "x", x);
        this.dataConfig.getConfig().set(path + "y", y);
        this.dataConfig.getConfig().set(path + "z", z);
        this.dataConfig.getConfig().set(path + "type", mobHopper.getType());

        this.dataConfig.save();
    }

    private void addToCache(MobHopper mobHopper) {

        String index = mobHopper.getIndex();
        String worldName = mobHopper.getLocation().getWorld().getName();
        String type = mobHopper.getType();


        Map<String, Map<BeastChunk, Map<String, MobHopper>>> worldMap = this.indexedData.get(worldName);
        if (Objects.isNull(worldMap)) {
            this.indexedData.put(worldName, new HashMap<>());
        }


        Map<BeastChunk, Map<String, MobHopper>> entityTypeMap = this.indexedData.get(worldName).get(type);
        if (Objects.isNull(entityTypeMap)) {
            this.indexedData.get(worldName).put(type, new HashMap<>());
        }

        Map<String, MobHopper> chunkMap = this.indexedData.get(worldName).get(type).get(mobHopper.getChunk());
        if (Objects.isNull(chunkMap)) {
            this.indexedData.get(worldName).get(type).put(mobHopper.getChunk(), new HashMap<>());
        }

        this.indexedData.get(worldName).get(type).get(mobHopper.getChunk()).put(index, mobHopper);
    }


    public MobHopper get(Location location, EntityType entityType) {

        String worldName = location.getWorld().getName();
        String index = MobHopper.encodeIndex(location);
        MobHopper mobHopper = null;
        // get by world
        Map<String, Map<BeastChunk, Map<String, MobHopper>>> worldMap = this.indexedData.get(worldName);

        if (Objects.nonNull(worldMap)) {
            // get by entity type
            Map<BeastChunk, Map<String, MobHopper>> locationsMap = worldMap.get(entityType);
            if (Objects.nonNull(locationsMap)) {
                Map<String, MobHopper> hopperMap = locationsMap.get(new BeastChunkImpl(location.getChunk()));
                if (Objects.nonNull(hopperMap)) {
                    return hopperMap.get(index);
                }
            }
        }
        return mobHopper;
    }

    public MobHopper getFromLocation(Location location) {
        String worldName = location.getWorld().getName();
        String index = MobHopper.encodeIndex(location);
        MobHopper mobHopper = null;

        // get by world
        Map<String, Map<BeastChunk, Map<String, MobHopper>>> worldMap = this.indexedData.get(worldName);

        if (Objects.nonNull(worldMap)) {
            for (String entityType : worldMap.keySet()) {
                // get by entity type
                Map<BeastChunk, Map<String, MobHopper>> locationsMap = worldMap.get(entityType);
                if (Objects.nonNull(locationsMap)) {
                    Map<String, MobHopper> mobHopperMap = locationsMap.get(new BeastChunkImpl(location.getChunk()));
                    if (Objects.nonNull(mobHopperMap)) {
                        mobHopper = mobHopperMap.get(index);
                        if (Objects.nonNull(mobHopper)) {
                            break;
                        }
                    }
                }
            }
        }
        return mobHopper;
    }


    public void remove(MobHopper mobHopper) {
        this.removeFromConfig(mobHopper);
        this.removeFromCache(mobHopper);
    }

    private void removeFromConfig(MobHopper mobHopper) {
        this.dataConfig.getConfig().set("data." + mobHopper.getIndex(), null);
    }

    private void removeFromCache(MobHopper mobHopper) {
        this.indexedData.get(mobHopper.getLocation().getWorld().getName()).get(mobHopper.getType()).remove(mobHopper.getIndex());
    }

    public Set<MobHopper> getByChunkAndType(BeastChunk chunk, String entityType) {
        String world = chunk.getWorldName();
        Map<String, Map<BeastChunk, Map<String, MobHopper>>> worldMap = this.indexedData.get(world);
        Set<MobHopper> set = new HashSet<>();
        if (Objects.nonNull(worldMap)) {
            for (String type : worldMap.keySet()) {
                if (type.equalsIgnoreCase(entityType) || type.equalsIgnoreCase("ALL")) {
                    Map<BeastChunk, Map<String, MobHopper>> chunkMap = worldMap.get(type);
                    if (Objects.nonNull(chunkMap)) {
                        Map<String, MobHopper> hopperMap = chunkMap.get(chunk);
                        if (Objects.nonNull(hopperMap)) {
                            set.addAll(hopperMap
                                    .entrySet()
                                    .stream()
                                    .flatMap(e -> Collections.singleton(e.getValue()).stream())
                                    .collect(Collectors.toSet()));
                        }
                    }
                }
            }
        }
        return set;
    }

}
