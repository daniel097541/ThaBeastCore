package info.beastsoftware.beastcore.manager;

import com.sun.scenario.effect.Crop;
import gnu.trove.map.hash.THashMap;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.ILocationUtil;
import info.beastsoftware.beastcore.entity.CropHopper;
import info.beastsoftware.hookcore.entity.BeastChunk;
import info.beastsoftware.hookcore.entity.BeastChunkImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class CropHoppersDataManager {

    private final Map<BeastChunk, Map<String, CropHopper>> indexedData = new HashMap<>();
    private final IConfig dataConfig;
    private final String PATH = "data";


    public CropHoppersDataManager(IConfig dataConfig) {
        this.dataConfig = dataConfig;
        this.load();
    }

    private void load() {

        if (this.dataConfig.getConfig().getConfigurationSection(PATH) == null) {
            this.dataConfig.getConfig().createSection(PATH);
            return;
        }

        for (String id : this.dataConfig.getConfig().getConfigurationSection(PATH).getKeys(false)) {
            CropHopper hopper = this.getFromConfig(id);

            if(Objects.nonNull(hopper)) {
                this.addHopperToCache(hopper);
            }
        }
    }

    public CropHopper getFromConfig(String id){
        String worldName = dataConfig.getConfig().getString(PATH + "." + id + ".world");

        if (worldName == null) {
            return null;
        }

        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            return null;
        }
        int x = this.dataConfig.getConfig().getInt(PATH + "." + id + ".x");
        int y = this.dataConfig.getConfig().getInt(PATH + "." + id + ".y");
        int z = this.dataConfig.getConfig().getInt(PATH + "." + id + ".z");
        Location location = new Location(world, x, y, z);
        return new CropHopper(location);
    }


    public void saveHopper(CropHopper cropHopper){
        Location location = cropHopper.getLocation();
        this.dataConfig.getConfig().set(PATH + "." + cropHopper.getIndex() + ".world", location.getWorld().getName());
        this.dataConfig.getConfig().set(PATH + "." + cropHopper.getIndex() + ".x", location.getBlockX());
        this.dataConfig.getConfig().set(PATH + "." + cropHopper.getIndex() + ".y", location.getBlockY());
        this.dataConfig.getConfig().set(PATH + "." + cropHopper.getIndex() + ".z", location.getBlockZ());
        this.dataConfig.save();
        this.addHopperToCache(cropHopper);
    }


    public void removeHopper(CropHopper cropHopper){
        Location location = cropHopper.getLocation();
        String index = cropHopper.getIndex();

        // remove from config
        this.dataConfig.getConfig().set(PATH + "." + index, null);
        this.dataConfig.save();

        // remove from cache
        Map<String, CropHopper> cropHopperMap = this.indexedData.get(new BeastChunkImpl(location.getChunk()));
        cropHopperMap.remove(index);
    }


    public void addHopperToCache(CropHopper cropHopper) {

        BeastChunk chunk = new BeastChunkImpl(cropHopper.getLocation().getChunk());
        String index = cropHopper.getIndex();

        if (!this.indexedData.containsKey(chunk)) {
            this.indexedData.put(chunk, new THashMap<>());
        }

        this.indexedData.get(chunk).put(index, cropHopper);
    }


    public CropHopper get(Location location) {

        Block block = location.getBlock();

        if(!block.getType().equals(Material.HOPPER)){
            return null;
        }

        BeastChunk chunk = new BeastChunkImpl(location.getChunk());
        String index = CropHopper.encodeIndex(location);
        Map<String, CropHopper> hopperMap = this.indexedData.get(chunk);

        //get hopper from indexed data
        if(Objects.nonNull(hopperMap)){
            return hopperMap.get(index);
        }

        // get from cache failed, try getting it from config
        else{
            CropHopper hopper = this.getFromConfig(index);
            if(Objects.nonNull(hopper)){
                this.addHopperToCache(hopper);
            }
            return hopper;
        }
    }


    public Set<CropHopper> findAllInChunkAndRadius(BeastChunk chunk, int radius, ItemStack itemStack) {

        //hopper is in fas cache
        if (this.indexedData.containsKey(chunk)) {

            for (CropHopper hopper : this.indexedData.get(chunk).values())
                if (hopper.hasEmptySlotForItem(itemStack)) {
                    return Collections.singleton(hopper);
                }
        }

        return this.indexedData
                .entrySet()
                .stream()
                .filter(entry -> ILocationUtil.isInChunkRadius(chunk.getBukkitChunk(), entry.getKey().getBukkitChunk(), radius))
                .flatMap(entry -> entry.getValue().values().stream())
                .filter(hopper -> hopper.hasEmptySlotForItem(itemStack))
                .collect(Collectors.toSet());
    }
}
