package info.beastsoftware.beastcore.manager;

import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

public interface INBTHook {

    boolean hasNBTData(ItemStack itemStack);

    void removeNBTData(BlockState block);

}
