package info.beastsoftware.beastcore.beastutils.gui.controller;

import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import info.beastsoftware.beastcore.feature.IListener;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IGUIController extends IListener {


    void onOpen(GuiOpenEvent event);

    void onClick(InventoryClickEvent event);

    void click(Player clicker, Inventory clickedInv, ItemStack clickedItem, int page, String buttonRole, int clickedSlot);

    void onClose(InventoryCloseEvent event);

    void createGui(Player owner);

    void reload();
}
