package info.beastsoftware.beastcore.manager;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTTileEntity;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public interface NBTManager {


    default void removeNBTMetadata(BlockState state) {
        try {
            NBTTileEntity nbtTileEntity = new NBTTileEntity(state);
            Collection<String> keys = new ArrayList<>(nbtTileEntity.getKeys());

            for (String key : keys) {
                nbtTileEntity.removeKey(key);
            }

        } catch (Throwable ignored) {
        }
    }

    default boolean itemHasNBT(ItemStack item) {
        try {
            NBTItem nbtItem = new NBTItem(item);
            return nbtItem.hasNBTData();
        }
        catch (Throwable ignored){
            return false;
        }
    }


    default ItemStack removeNBTData(ItemStack itemStack) {
        NBTItem item = new NBTItem(itemStack);

        for (Iterator<String> key = item.getKeys().iterator(); key.hasNext(); ) {
            item.removeKey(key.next());
        }

        return item.getItem();
    }

}
