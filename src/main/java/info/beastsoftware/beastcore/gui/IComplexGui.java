package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.feature.IListener;
import org.bukkit.inventory.Inventory;

public interface IComplexGui extends IListener {


    Inventory getInventoryByName(String name, String role);

    void createInventory(String name, String role);

    void createButtons();

    void createMenus(String name, int size);

    boolean containsInventory(String name, Inventory inventory);

    void reload();

    void startMenus();
}

