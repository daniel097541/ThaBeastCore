package info.beastsoftware.beastcore.gui.controller;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.gui.controller.AbstractGUIController;
import info.beastsoftware.beastcore.beastutils.gui.entity.Button;
import info.beastsoftware.beastcore.beastutils.gui.entity.IButton;
import info.beastsoftware.beastcore.beastutils.gui.entity.IPage;
import info.beastsoftware.beastcore.beastutils.gui.repository.GUIRepo;
import info.beastsoftware.beastcore.beastutils.gui.repository.IGUIRepository;
import info.beastsoftware.beastcore.beastutils.utils.ButtonUtil;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.beastutils.utils.PaginationUtil;
import info.beastsoftware.beastcore.feature.IBeastFeature;
import info.beastsoftware.beastcore.struct.ButtonType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainMenuController extends AbstractGUIController {


    private final HashMap<Integer, HashMap<Integer, FeatureType>> slotMap;


    public MainMenuController(IConfig config, Class eventClass, Plugin plugin) {
        super(config, eventClass, plugin);
        slotMap = new HashMap<>();
    }

    @Override
    public void click(Player clicker, Inventory clickedInv, ItemStack clickedItem, int page, String buttonRole, int clickedSlot) {
        ButtonType type = ButtonType.valueOf(buttonRole);
        switch (type) {
            case NEXT_PAGE: {
                int newPage = page + 1;
                clicker.openInventory(service.getPageGuiInventory(clicker, newPage));
                return;
            }

            case PREVIOUS_PAGE: {
                int newPage = page - 1;
                clicker.openInventory(service.getPageGuiInventory(clicker, newPage));
                return;
            }

            case TOGGLEABLE: {
                FeatureType featureType = slotMap.get(page).get(clickedSlot);
                IBeastFeature feature = BeastCore.getInstance().getApi().getFeature(featureType);
                ItemStack item;
                String name = config.getConfig().getString("Features-Menu.Features-List." + featureType.toString() + ".name");

                if (feature.isOn()) {
                    item = createDisabled(name);
                } else item = createEnabled(name);

                feature.toggle();

                IButton button = service.getButton(clicker, page, clickedItem);
                button.setItem(item);
                service.replaceButton(clicker, page, button);
            }
        }
    }


    @Override
    public void onClose(InventoryCloseEvent event) {
        if (service.isGui((Player) event.getPlayer(), event.getInventory()))
            service.removeGui((Player) event.getPlayer());
    }


    private ItemStack createEnabled(String name) {
        String pathEnabled = "Features-Menu.Buttons.Enabled.";
        Material enabledMat = Material.getMaterial(config.getConfig().getString(pathEnabled + "material"));
        short enabledDam = Short.valueOf(config.getConfig().getInt(pathEnabled + "damage") + "");
        List<String> enLore = config.getConfig().getStringList(pathEnabled + "lore");
        return IInventoryUtil.createItem(1, enabledMat, name, enLore, enabledDam, true);
    }


    private ItemStack createDisabled(String name) {
        String pathDisabled = "Features-Menu.Buttons.Disabled.";
        Material disMat = Material.getMaterial(config.getConfig().getString(pathDisabled + "material"));
        short disDam = Short.valueOf(config.getConfig().getInt(pathDisabled + "damage") + "");
        List<String> disLore = config.getConfig().getStringList(pathDisabled + "lore");
        return IInventoryUtil.createItem(1, disMat, name, disLore, disDam, true);
    }


    @Override
    public void createGui(Player owner) {
        IButton nextButton = ButtonUtil.createButton(config, "Features-Menu.Buttons.Next-Page-Button.", false, false, ButtonType.NEXT_PAGE.toString());
        IButton previousButton = ButtonUtil.createButton(config, "Features-Menu.Buttons.Previous-Page-Button.", false, false, ButtonType.PREVIOUS_PAGE.toString());

        int pageSize = config.getConfig().getInt("Features-Menu.size");
        String pageTitle = config.getConfig().getString("Features-Menu.title");

        int subListSize = pageSize - 9;

        List<IButton> buttons = new ArrayList<>();
        int cont = 0;
        int page = 0;
        for (IBeastFeature feature : BeastCore.getInstance().getApi().getAllFeatures()) {

            if (cont >= subListSize) {
                cont = 0;
                page++;
            }

            String name = config.getConfig().getString("Features-Menu.Features-List." + feature.getFeatureType().toString() + ".name");

            ItemStack butonItem;
            if (feature.isOn()) {
                butonItem = createEnabled(name);
            } else {
                butonItem = createDisabled(name);
            }

            IButton button = new Button(cont, ButtonType.TOGGLEABLE.toString(), butonItem);
            buttons.add(button);

            // slot map will store a map with the listeners type in each slot, with its page number
            if (!slotMap.containsKey(page)) {
                slotMap.put(page, new HashMap<>());
            }
            slotMap.get(page).put(cont, feature.getFeatureType());
            // slot map will store a map with the listeners type in each slot, with its page number

            cont++;
        }


        List<IPage> pages = PaginationUtil.createPages(buttons, pageSize, 9, pageTitle, nextButton, previousButton, null);


        IGUIRepository gui = new GUIRepo(pages);
        service.createGui(owner, gui);
    }
}
