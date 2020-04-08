package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.HopperFilterReloadEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.gui.HopperFilterGui;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HopperFilterFeatureListener extends AbstractFeatureListener {

    private final HopperFilterGui hopperFilterGui;

    public HopperFilterFeatureListener(IConfig config) {
        super(config, FeatureType.HOPPER_FILTER);
        this.hopperFilterGui = new HopperFilterGui(config);
    }



    public boolean isItemFiltered(ItemStack itemStack){
        return hopperFilterGui.isListed(itemStack.getType());
    }



    @EventHandler
    public void onItemPickUp(InventoryPickupItemEvent e) {

        if (e.isCancelled()) return;

        if (!isOn())
            return;

        Inventory inventory = e.getInventory();

        if (!inventory.getType().equals(InventoryType.HOPPER)) return;

        ItemStack itemStack = e.getItem().getItemStack();

        if (isItemFiltered(itemStack))
            e.setCancelled(true);

    }


    @Override
    public void toggle() {
        super.toggle();
        Bukkit.getPluginManager().callEvent(new HopperFilterReloadEvent());
    }
}
