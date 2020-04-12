package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.ILocationUtil;
import info.beastsoftware.beastcore.entity.MobHopper;
import info.beastsoftware.beastcore.struct.Mob;
import info.beastsoftware.hookcore.entity.BeastChunkImpl;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MobHoppersService {

    private final MobHoppersManager mobHoppersManager;


    public MobHoppersService(IConfig dataConfig) {
        mobHoppersManager = new MobHoppersManager(dataConfig);
    }


    public MobHopper get(Location location, EntityType entityType){
        return this.mobHoppersManager.get(location, entityType);
    }

    public MobHopper fromLocation(Location location){
        return this.mobHoppersManager.getFromLocation(location);
    }

    public void add(Location location, String type) {
        MobHopper mobHopper = new MobHopper(type, location);
        this.mobHoppersManager.save(mobHopper);
    }


    public void delete(MobHopper mobHopper){
        this.mobHoppersManager.remove(mobHopper);
    }

    public Set<MobHopper> getInRadiusSortedByDistance(int radius, Location location, EntityType type) {
        Set<MobHopper> hoppers = new HashSet<>();
        for (Chunk c : ILocationUtil.getChunksAroundLocation(location, radius)) {
            Set<MobHopper> inChunk = this.mobHoppersManager.getByChunkAndType(new BeastChunkImpl(c), type.name());
            hoppers.addAll(inChunk);
        }
        return hoppers
                .stream()
                .sorted( (h1, h2) -> (int) (h1.distanceTo(location) - h2.distanceTo(location)))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


}
