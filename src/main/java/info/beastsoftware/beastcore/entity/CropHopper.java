package info.beastsoftware.beastcore.entity;

import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.hookcore.entity.BeastChunk;
import info.beastsoftware.hookcore.entity.BeastChunkImpl;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.block.Hopper;
import org.bukkit.inventory.ItemStack;

import java.util.Base64;

@Getter
@EqualsAndHashCode
@ToString
public class CropHopper {
    private final String index;
    private final Location location;

    public CropHopper(Location location) {
        this.location = location;
        this.index = encodeIndex(location);
    }

    public BeastChunk getBeastChunk() {
        return new BeastChunkImpl(location.getChunk());
    }

    public Hopper getHopper() {
        return (Hopper) getLocation().getBlock().getState();
    }

    public boolean hasEmptySlotForItem(ItemStack itemStack) {
        return IInventoryUtil.canAddItem(itemStack.getType(), itemStack.getAmount(), this.getHopper().getInventory());
    }

    public void addItem(ItemStack itemStack) {
        Hopper hopper = this.getHopper();
        hopper.getInventory().addItem(itemStack);
    }

    public static String encodeIndex(Location location){
        return Base64.getEncoder().encodeToString(location.toString().getBytes());
    }
}
