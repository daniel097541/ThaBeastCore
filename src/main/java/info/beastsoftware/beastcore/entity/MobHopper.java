package info.beastsoftware.beastcore.entity;

import info.beastsoftware.hookcore.entity.BeastChunk;
import info.beastsoftware.hookcore.entity.BeastChunkImpl;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.Base64;

@Getter
@EqualsAndHashCode
@ToString
public class MobHopper {

    private final String type;
    private final String index;
    private final Location location;


    public MobHopper(String type, Location location) {
        this.type = type;
        this.location = location;
        this.index = encodeIndex(location);
    }

    public static String encodeIndex(Location location) {
        return Base64.getEncoder().encodeToString(location.toString().getBytes());
    }

    public BeastChunk getChunk() {
        return new BeastChunkImpl(location.getChunk());
    }

    public Inventory getInventory() {
        Hopper hopper = (Hopper) Arrays.stream(this.location.getChunk().getTileEntities())
                .filter(e -> e instanceof Hopper)
                .filter(e -> e.getLocation().equals(this.location))
                .findFirst()
                .orElse(null);
        return hopper.getInventory();
    }

    public double distanceTo(Location location) {
        return this.getLocation().distance(location);
    }
}
