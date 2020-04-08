package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import java.util.ArrayList;
import java.util.List;

public class LapisFillerFeatureListener extends AbstractFeatureListener {

    private final List<Inventory> filled = new ArrayList<>();
    private final ItemStack lapis;

    public LapisFillerFeatureListener(IConfig config) {
        super(config, FeatureType.LAPIS_FILLER);

        Dye d = new Dye();
        d.setColor(DyeColor.BLUE);
        this.lapis = d.toItemStack();
        this.lapis.setAmount(64);
    }

    @EventHandler
    public void onEnchatTableOpen(InventoryOpenEvent event) {

        if (!isOn() || event.isCancelled()) return;

        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();


        if (!(inventory instanceof EnchantingInventory)) return;

        String permission = config.getConfig().getString("permission");

        if (permission == null) {
            Bukkit.getConsoleSender().sendMessage(StrUtils.translate("&7BeastCore >> &4WARNING!!!  &cThe permission of lapis lazuli feature is set tu null!!!"));
            permission = "btf.lapis.fill";
        }

        if (!player.hasPermission(permission)) return;

        inventory.setItem(1, lapis);

        filled.add(inventory);

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        if (!isOn()) return;

        Inventory inventory = event.getInventory();

        if (!(inventory instanceof EnchantingInventory)) return;

        if (filled.contains(inventory)) {
            inventory.setItem(1, null);
            filled.remove(inventory);
        }
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (!isOn()) return;

        Inventory inventory = event.getClickedInventory();

        if (!filled.contains(inventory)) return;

        int slot = event.getSlot();

        if (slot == 1) {
            event.setCancelled(true);
        }

    }


    @EventHandler
    public void onEnchant(EnchantItemEvent event) {

        if (!isOn()) return;

        Inventory inventory = event.getInventory();

        if (!filled.contains(inventory)) return;

        inventory.setItem(1, lapis);

    }

}
