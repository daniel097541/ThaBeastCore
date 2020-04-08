package info.beastsoftware.beastcore.beastutils.gui.controller;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.event.gui.GuiOpenEvent;
import info.beastsoftware.beastcore.beastutils.gui.service.GUIService;
import info.beastsoftware.beastcore.beastutils.gui.service.IGUIService;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class AbstractGUIController implements IGUIController {

    protected IGUIService service;
    protected IConfig config;
    protected Class eventClass;
    protected Plugin plugin;


    public AbstractGUIController(IConfig config, Class eventClass, Plugin plugin) {
        this.config = config;
        this.service = new GUIService();
        this.eventClass = eventClass;
        this.plugin = plugin;
        this.autoRegister();
    }


    @EventHandler
    @Override
    public void onOpen(GuiOpenEvent event) {
        if (!event.getClass().equals(eventClass)) return;

        BeastPlayer player = event.getPlayer();

        /////// **** ASYNC GUI CREATION **** /////////////////
        if (!service.hasGui(player.getBukkitPlayer())) {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                createGui(player.getBukkitPlayer());
                player.openInventory(service.getPageGuiInventory(player.getBukkitPlayer(), 0));
            });
            return;
        }
        /////// **** ASYNC GUI CREATION **** /////////////////

        player.openInventory(service.getPageGuiInventory(player.getBukkitPlayer(), 0));
    }


    @EventHandler
    @Override
    public void onClick(InventoryClickEvent event) {

        Inventory inventory = event.getClickedInventory();

        if (!service.isGui(inventory)) return;

        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        ItemStack itemStack = event.getCurrentItem();

        int page = service.getPageIndexFromGui(player, inventory);
        String role = service.getButtonRole(player, page, itemStack);


        if (role == null) {
            return;
        }

        click(player, inventory, itemStack, page, role, event.getSlot());
    }


    @Override
    public void reload() {
        this.service.reload();
    }
}
