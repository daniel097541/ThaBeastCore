package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public abstract class AbstractComplexGui implements IComplexGui {


    protected IConfig config;
    protected Inventory mainMenu;
    protected IDataConfig dataConfig;
    protected HashMap<String, HashMap<String, Inventory>> inventories;
    protected HashMap<String, InventoryButton> mainMenuButtons;
    protected HashMap<String, InventoryButton> secondaryMenuButtons;
    protected StringUtils stringUtils;


    public AbstractComplexGui(IConfig config, IDataConfig dataConfig) {
        this.config = config;
        this.dataConfig = dataConfig;
        inventories = new HashMap<>();
        mainMenuButtons = new HashMap<>();
        secondaryMenuButtons = new HashMap<>();
        stringUtils = new StringUtils();
        startMenus();
        this.autoRegister();
    }

    @Override
    public void createMenus(String menuName, int size) {
        createButtons();
        mainMenu = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', menuName));
        for (InventoryButton inventoryButton : mainMenuButtons.values()) {
            mainMenu.setItem(inventoryButton.getGuiIndex(), inventoryButton.getButton());
        }
    }

    @Override
    public Inventory getInventoryByName(String name, String role) {
        return inventories.get(name).get(role);
    }


    @Override
    public void reload() {
        inventories = new HashMap<>();
        mainMenuButtons = new HashMap<>();
        secondaryMenuButtons = new HashMap<>();
        startMenus();
    }

    @Override
    public boolean containsInventory(String name, Inventory inventory) {

        if (!inventories.containsKey(name)) return false;

        for (Inventory inv : inventories.get(name).values()) {
            if (inv.equals(inventory))
                return true;
        }
        return false;
    }


    public boolean existsInventory(String name) {
        return inventories.keySet().contains(name);
    }

    public Inventory getMainMenu() {
        return mainMenu;
    }

    public IDataConfig getDataConfig() {
        return dataConfig;
    }

}
