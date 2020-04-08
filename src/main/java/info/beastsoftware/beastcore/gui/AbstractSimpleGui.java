package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class AbstractSimpleGui implements ISimpleGui {

    protected Inventory menu;

    protected InventoryButton toggleButton;

    protected InventoryButton closeButton;

    protected InventoryButton saveButton;

    protected IConfig config;

    protected String savePath;

    protected String togglePath;

    protected String savedMessagePath;


    public AbstractSimpleGui(IConfig config) {
        this.config = config;
        this.autoRegister();
    }

    @Override
    public void reload() {
        initGui();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getClickedInventory() == null) return;

        if (!e.getClickedInventory().equals(menu)) return;
        if (e.getCurrentItem() == null) return;
        ItemStack clickedItem = e.getCurrentItem();

        //save
        if (clickedItem.equals(saveButton.getButton())) {
            saveContents(savePath);
            BeastCore.getInstance().sms(e.getWhoClicked(), config.getConfig().getString(savedMessagePath));
            e.setCancelled(true);
            return;
        }

        //close
        if (clickedItem.equals(closeButton.getButton())) {
            e.getWhoClicked().closeInventory();
            e.setCancelled(true);
            return;
        }

        //toggle
        if (clickedItem.equals(toggleButton.getButton())) {
            config.getConfig().set(togglePath, !config.getConfig().getBoolean(togglePath));
            createButtons();
            menu.setItem(toggleButton.getGuiIndex(), toggleButton.getButton());
            e.setCancelled(true);
        }

    }

    @Override
    public void initGui() {
        createInventory();
        loadContents(savePath);
        createButtons();
        menu.setItem(saveButton.getGuiIndex(), saveButton.getButton());
        menu.setItem(closeButton.getGuiIndex(), closeButton.getButton());
        menu.setItem(toggleButton.getGuiIndex(), toggleButton.getButton());
    }

    @Override
    public void loadContents(String path) {
        if (config.getConfig().getList(path) == null)
            return;
        if (config.getConfig().getList(path).isEmpty())
            return;
        ItemStack[] contents = (config.getConfig().getList(path)).toArray(new ItemStack[0]);
        menu.setContents(contents);
    }

    @Override
    public void saveContents(String path) {
        config.getConfig().set(path, menu.getContents());
        config.save();
    }


    public boolean isAButton(ItemStack itemStack) {
        return itemStack.equals(saveButton.getButton()) || itemStack.equals(closeButton.getButton()) || itemStack.equals(toggleButton.getButton());
    }


    public Inventory getMenu() {
        return menu;
    }

    public IConfig getConfig() {
        return config;
    }
}
