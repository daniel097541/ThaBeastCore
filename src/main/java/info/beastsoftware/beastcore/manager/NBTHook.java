package info.beastsoftware.beastcore.manager;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTTileEntity;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

public class NBTHook implements INBTHook {




    @Override
    public boolean hasNBTData(ItemStack itemStack) {
        NBTItem item = new NBTItem(itemStack);
        return item.hasNBTData();
    }

    @Override
    public void removeNBTData(BlockState block) {
        NBTTileEntity tileEntity;
        try {
            tileEntity = new NBTTileEntity(block);
        }
        catch (Exception ignored){
            return;
        }
        tileEntity
                .getKeys()
                .parallelStream()
                .forEach(tileEntity::removeKey);
    }
}
