package info.beastsoftware.beastcore.gui.controller;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.beastutils.gui.controller.AbstractGUIController;
import info.beastsoftware.beastcore.beastutils.gui.entity.IButton;
import info.beastsoftware.beastcore.beastutils.gui.entity.IPage;
import info.beastsoftware.beastcore.beastutils.gui.repository.GUIRepo;
import info.beastsoftware.beastcore.beastutils.gui.repository.IGUIRepository;
import info.beastsoftware.beastcore.beastutils.utils.ButtonUtil;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.beastutils.utils.PaginationUtil;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.struct.ButtonType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ItemFilterController extends AbstractGUIController {


    private final IDataConfig dataConfig;


    public ItemFilterController(IConfig config, IDataConfig dataConfig, Class eventClass, Plugin plugin) {
        super(config, eventClass, plugin);
        this.dataConfig = dataConfig;
    }


    @Override
    public void click(Player clicker, Inventory clickedInv, ItemStack clickedItem, int page, String buttonRole, int clickedSlot) {
        ButtonType type = ButtonType.valueOf(buttonRole);

        switch (type) {
            case TOGGLEABLE: {

                IButton clickedButton = service.getButton(clicker, page, clickedItem);

                Material material = clickedButton.getItem().getType();

                boolean enabled = dataConfig.getConfigByName(clicker.getUniqueId().toString()).getBoolean("Filtered-Materials." + material.toString() + ".pickup-enabled");

                dataConfig.getConfigByName(clicker.getUniqueId().toString()).set("Filtered-Materials." + material.toString() + ".pickup-enabled", !enabled);
                dataConfig.save(clicker.getUniqueId().toString());

                List<String> lore = ButtonUtil.getToggeableLore(config, "Item-Filter.GUI.Buttons.materials-format.", !enabled);

                ItemMeta meta = clickedItem.getItemMeta();
                meta.setLore(StrUtils.translateLore(lore));
                clickedItem.setItemMeta(meta);

                clickedButton.setItem(clickedItem);
                service.replaceButton(clicker, page, clickedButton);
                return;
            }

            case NEXT_PAGE: {
                clicker.closeInventory();
                int pageNext = page + 1;
                clicker.openInventory(service.getPageGuiInventory(clicker, pageNext));
                return;
            }

            case PREVIOUS_PAGE: {
                clicker.closeInventory();
                int pagePrev = page - 1;
                clicker.openInventory(service.getPageGuiInventory(clicker, pagePrev));
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void createGui(Player owner) {

        if (!dataConfig.getConfigs().keySet().contains(owner.getUniqueId().toString())) {
            dataConfig.loadConfig(owner.getUniqueId().toString());
        }


        String nextButtonPath = "Item-Filter.GUI.Buttons.next-page.";
        String previousButtonPath = "Item-Filter.GUI.Buttons.previous-page.";

        IButton nextButton = ButtonUtil.createButton(config, nextButtonPath, false, false, ButtonType.NEXT_PAGE.toString());
        IButton previousButton = ButtonUtil.createButton(config, previousButtonPath, false, false, ButtonType.PREVIOUS_PAGE.toString());

        List<Material> mats = Arrays.asList(Material.values());

        int pageSize = config.getConfig().getInt("Item-Filter.GUI.page-size");
        String pageTitle = config.getConfig().getString("Item-Filter.GUI.page-title");


        List<IButton> buttons = new ArrayList<>();
        //filter al materials to check if can be an item
        int cont = 0;
        for (Iterator<Material> iterator = mats.iterator(); iterator.hasNext(); ) {
            if (cont >= pageSize - 9) {
                cont = 0;
            }

            Material material = iterator.next();
            if (IInventoryUtil.materialCanBeDisplayed(material)) {
                boolean enabled = dataConfig.getConfigByName(owner.getUniqueId().toString()).getBoolean("Filtered-Materials." + material.toString() + ".pickup-enabled");
                String name = StrUtils.replacePlaceholder(config.getConfig().getString("Item-Filter.GUI.Buttons.materials-format.name"), "{material_name}", material.toString());
                List<String> lore = ButtonUtil.getToggeableLore(config, "Item-Filter.GUI.Buttons.materials-format.", enabled);
                IButton button = ButtonUtil.generateButton(material, name, lore, Short.valueOf(0 + ""), cont, ButtonType.TOGGLEABLE.toString());
                buttons.add(button);
                cont++;
            }
        }


        ItemStack spacer = null;
        if (config.getConfig().getBoolean("Item-Filter.GUI.fill-with-spacers")) {
            Material spacerMat = Material.getMaterial(config.getConfig().getString("Item-Filter.GUI.Buttons.Spacer-Button.material"));
            short damage = Short.valueOf(config.getConfig().getInt("Item-Filter.GUI.Buttons.Spacer-Button.damage") + "");
            spacer = new ItemStack(spacerMat);
            spacer.setDurability(damage);
            ItemMeta meta = spacer.getItemMeta();
            meta.setDisplayName(" ");
            spacer.setItemMeta(meta);
        }


        // create every page
        List<IPage> pages = PaginationUtil.createPages(buttons, pageSize, 9, pageTitle, nextButton, previousButton, spacer);


        //add gui repo to service
        IGUIRepository repo = new GUIRepo(pages);
        service.createGui(owner, repo);
    }


}
