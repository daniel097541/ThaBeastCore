package info.beastsoftware.beastcore.gui;


import info.beastsoftware.beastcore.feature.IListener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface ISimpleGui extends IListener {

    void saveContents(String path);

    void loadContents(String path);

    void createInventory();

    void createButtons();

    void initGui();

    void reload();

    Inventory getMenu();

    boolean isAButton(ItemStack itemStack);

}
