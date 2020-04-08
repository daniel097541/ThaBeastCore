package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.annotations.features.AnvilColor;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.AnvilColorFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Singleton
public class AnvilColorFeature extends AbstractFeature {

    @Inject
    protected AnvilColorFeature(@AnvilColor IConfig config) {
        super(config, new AnvilColorFeatureListener(config), FeatureType.ANVIL_COLOR);
    }



    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {

        if (!isOn() || event.isCancelled()) return;

        Inventory inventory = event.getClickedInventory();

        if (inventory == null) return;

        InventoryType type = inventory.getType();

        if (!type.equals(InventoryType.ANVIL)) return;

        int slot = event.getRawSlot();
        if (slot != 2) return;


        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null) return;


        Player player = null;
        try {
            player = (Player) event.getWhoClicked();
        } catch (Exception e) {
            return;
        }

        if (!player.hasPermission(config.getConfig().getString("permission"))) return;


        ItemStack translated = translateItem(itemStack);
        event.setCurrentItem(translated);
    }


    private ItemStack translateItem(ItemStack itemStack) {


        if (!itemStack.hasItemMeta()) return itemStack;


        ItemStack returned = itemStack.clone();
        ItemMeta meta = itemStack.getItemMeta();

        if (!meta.hasDisplayName()) return itemStack;

        String displayName = meta.getDisplayName();

        displayName = StrUtils.translate(displayName);

        meta.setDisplayName(displayName);
        returned.setItemMeta(meta);
        return returned;
    }

}
