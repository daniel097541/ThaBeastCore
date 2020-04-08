package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.event.gui.EditDropsGUIOpenEvent;
import info.beastsoftware.beastcore.struct.ButtonType;
import info.beastsoftware.beastcore.struct.Mob;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditDropsGui extends AbstractComplexGui {

    private HashMap<String, InventoryButton> mobButtons;
    private HashMap<String, Inventory> advancedDropsInventories;


    public EditDropsGui(IConfig config, IDataConfig dataConfig) {
        super(config, dataConfig);
    }

    private void loadAdvancedInventories() {
        advancedDropsInventories = new HashMap<>();


        for (Mob mob : Mob.values()) {
            if (mob.getEntityType() == null) continue;
            advancedDropsInventories.put(mob.toString(), createAdvancedInventory(mob.toString()));
        }
    }


    @EventHandler
    public void onOpen(EditDropsGUIOpenEvent event){
        BeastPlayer player = event.getPlayer();
        player.openInventory(getMainMenu());
    }


    // CREATE THE INVENTORY OF THE MOB
    private Inventory createAdvancedInventory(String mob) {
        Inventory inventory = Bukkit.createInventory(null, 54, mob);
        ItemStack[] contents = new ItemStack[52];

        //create items
        for (String idString : dataConfig.getConfigByName(mob).getConfigurationSection("Item-List").getKeys(false)) {
            int id = Integer.parseInt(idString);
            contents[id] = createItem(mob, id);
        }
        inventory.setContents(contents);

        inventory.setItem(secondaryMenuButtons.get(ButtonType.INFO.toString()).getGuiIndex(), secondaryMenuButtons.get(ButtonType.INFO.toString()).getButton());
        inventory.setItem(secondaryMenuButtons.get(ButtonType.BACK.toString()).getGuiIndex(), secondaryMenuButtons.get(ButtonType.BACK.toString()).getButton());
        return inventory;
    }


    private void updateItemChance(String mob, int slotId) {
        int newChance = advancedDropsInventories.get(mob).getItem(slotId).getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
        dataConfig.getConfigByName(mob).set("Item-List." + slotId + ".chance", newChance);
        dataConfig.save(mob);

        //update item lore
        ItemStack itemStack = createItem(mob, slotId);
        advancedDropsInventories.get(mob).setItem(slotId, itemStack);
    }


    //ADD ITEM TO THE INVENTORIES LIST
    private void addItem(ItemStack itemStack, String mob, int slot) {

        dataConfig.getConfigByName(mob).set("Item-List." + slot + ".item", itemStack);
        if (itemStack.getItemMeta().getDisplayName() != null)
            dataConfig.getConfigByName(mob).set("Item-List." + slot + ".name", itemStack.getItemMeta().getDisplayName());
        dataConfig.getConfigByName(mob).set("Item-List." + slot + ".chance", 0);
        dataConfig.getConfigByName(mob).set("Item-List." + slot + ".material", itemStack.getType().toString());
        dataConfig.getConfigByName(mob).set("Item-List." + slot + ".damage", itemStack.getDurability());
        dataConfig.getConfigByName(mob).set("Item-List." + slot + ".amount", itemStack.getAmount());


        dataConfig.save(mob);

        //create the item
        ItemStack item = createItem(mob, slot);

        //set the item in the slot
        advancedDropsInventories.get(mob).setItem(slot, item);
    }


    //REMOVE ITEM TO THE INVENTORIES LIST
    private void removeItem(String mob, int slot) {
        dataConfig.getConfigByName(mob).set("Item-List." + slot, null);
        dataConfig.save(mob);

        //remove from inventory
        advancedDropsInventories.get(mob).setItem(slot, null);
    }


    //GET THE MOBS NAME INVENTORY
    private String getMobInventory(Inventory inventory) {
        String mob = null;

        //iterate over all inventories
        for (String id : advancedDropsInventories.keySet())
            if (advancedDropsInventories.get(id).equals(inventory))
                mob = id;

        return mob;
    }


    @EventHandler
    public void onItemDropIntoInventory(InventoryClickEvent e) {

        ItemStack itemStack = e.getCurrentItem();
        Inventory inventory = e.getInventory();
        String mob = getMobInventory(inventory);

        if (getMobInventory(e.getClickedInventory()) != null) return;


        if (mob == null || itemStack == null || itemStack.getType().equals(Material.AIR)) return;

        e.setCancelled(true);

        if (!e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) return;

        //add the item to the list in the config and to the inventories
        addItem(itemStack, mob, inventory.firstEmpty());

    }


    private ItemStack createItem(String mob, int id) {
        ItemStack item;
        YamlConfiguration configuration = dataConfig.getConfigByName(mob);
        String path = "Item-List." + id;

        Material material = Material.valueOf(configuration.getString(path + ".material"));
        String damage = configuration.getString(path + ".damage");
        int chance = configuration.getInt(path + ".chance");
        int amount = configuration.getInt(path + ".amount");
        String name = "";
        if (dataConfig.getConfigByName(mob).get(path + ".name") != null)
            name = configuration.getString(path + ".name");

        String loreLine = config.getConfig().getString("Gui-Menu.Advanced-Drops.chance-lore-line");
        loreLine = stringUtils.replacePlaceholder(loreLine, "{chance}", chance + "");
        List<String> lore = new ArrayList<>();
        lore.add(loreLine);

        item = IInventoryUtil.createItem(amount, material, name, lore, new Short(damage), true);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, chance, true);
        item.setItemMeta(meta);

        return item;
    }


    @Override
    public void startMenus() {
        mobButtons = new HashMap<>();
        super.createMenus(config.getConfig().getString("Gui-Menu.menu-title"), 54);
        //add mob buttons
        for (InventoryButton button : mobButtons.values()) {
            mainMenu.setItem(button.getGuiIndex(), button.getButton());
        }
        for (Mob mob : Mob.values()) {
            if (mob.getEntityType() == null) continue;
            createInventory(mob.toString(), mob.toString());
        }
        //load advanced inventories
        loadAdvancedInventories();
    }


    @Override
    public void createInventory(String name, String role) {
        Inventory menu = Bukkit.createInventory(null, 54, name);
        if (dataConfig.getConfigByName(name).getList("Drops.Items") != null) {
            ItemStack[] contents = (dataConfig.getConfigByName(name).getList("Drops.Items")).toArray(new ItemStack[0]);
            menu.setContents(contents);
        }
        inventories.put(name, new HashMap<>());
        inventories.get(name).put(role, menu);
        //add buttons to the gui
        for (InventoryButton button : secondaryMenuButtons.values()) {
            if (button.getType().equals(ButtonType.TOGGLEABLE)) {

                ItemMeta meta = button.getButton().getItemMeta();
                if (dataConfig.getConfigByName(name).getBoolean("Vanilla-Drops")) {
                    meta.setLore(stringUtils.translateLore(config.getConfig().getStringList("Gui-Menu.vanilla-drops-button.lore-enabled")));
                } else
                    meta.setLore(stringUtils.translateLore(config.getConfig().getStringList("Gui-Menu.vanilla-drops-button.lore-disabled")));
                button.getButton().setItemMeta(meta);
            }


            if (button.getType().equals(ButtonType.ADVANCED_DROPS)) {

                ItemMeta meta = button.getButton().getItemMeta();
                if (dataConfig.getConfigByName(name).getBoolean("Use-Advanced-Drops")) {
                    meta.setLore(stringUtils.translateLore(config.getConfig().getStringList("Gui-Menu.advanced-drops-button.lore-enabled")));
                } else
                    meta.setLore(stringUtils.translateLore(config.getConfig().getStringList("Gui-Menu.advanced-drops-button.lore-disabled")));
                button.getButton().setItemMeta(meta);
            }

            inventories.get(name).get(role).setItem(button.getGuiIndex(), button.getButton());
        }
    }


    @Override
    public void createButtons() {

        int cont = 0;
        for (Mob mob : Mob.values()) {
            if (mob.getEntityType() == null) continue;
            String name = mob.toString();
            ///////////// 1.13
            Material material;
            try {
                material = Material.valueOf("SKULL_ITEM");
            } catch (IllegalArgumentException ignored) {
                material = Material.valueOf("SKELETON_SKULL");
            }
            ///////////// 1.13
            ItemStack head = new ItemStack(material, 1, (short) 3);
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            headMeta.setOwner("MHF_" + name);
            headMeta.setDisplayName(name);
            head.setItemMeta(headMeta);
            InventoryButton button = new InventoryButton(head);
            button.setGuiIndex(cont);
            mobButtons.put(mob.toString(), button);
            cont++;
        }

        int savePosition = config.getConfig().getInt("Gui-Menu.save-button.position");
        int closePosition = config.getConfig().getInt("Gui-Menu.close-button.position");
        int vanillaDropsPosition = config.getConfig().getInt("Gui-Menu.vanilla-drops-button.position");
        int backPosition = config.getConfig().getInt("Gui-Menu.back-button.position");
        int toggleAdvancedDropsPos = config.getConfig().getInt("Gui-Menu.advanced-drops-button.position");
        int advancedDropsOpenPos = config.getConfig().getInt("Gui-Menu.advanced-drops-button-open.position");
        int infoPos = config.getConfig().getInt("Gui-Menu.info-drops-button.position");


        Material saveMaterial = Material.getMaterial(config.getConfig().getString("Gui-Menu.save-button.material"));
        Material closeMaterial = Material.getMaterial(config.getConfig().getString("Gui-Menu.close-button.material"));
        Material vanillaDropsMaterial = Material.getMaterial(config.getConfig().getString("Gui-Menu.vanilla-drops-button.material"));
        Material backMaterial = Material.getMaterial(config.getConfig().getString("Gui-Menu.back-button.material"));
        Material toggleDrops = Material.getMaterial(config.getConfig().getString("Gui-Menu.advanced-drops-button.material"));
        Material openDrops = Material.getMaterial(config.getConfig().getString("Gui-Menu.advanced-drops-button-open.material"));
        Material infoMat = Material.getMaterial(config.getConfig().getString("Gui-Menu.info-drops-button.material"));


        Short saveDamage = new Short(config.getConfig().getString("Gui-Menu.save-button.damage"));
        Short closeDamage = new Short(config.getConfig().getString("Gui-Menu.close-button.damage"));
        Short vanillaDropsDamage = new Short(config.getConfig().getString("Gui-Menu.vanilla-drops-button.damage"));
        Short backDamage = new Short(config.getConfig().getString("Gui-Menu.back-button.damage"));
        Short toggleDropsDamage = new Short(config.getConfig().getString("Gui-Menu.advanced-drops-button.damage"));
        Short openDropsDamage = new Short(config.getConfig().getString("Gui-Menu.advanced-drops-button-open.damage"));
        Short infoDamage = new Short(config.getConfig().getString("Gui-Menu.info-drops-button.damage"));


        String saveName = config.getConfig().getString("Gui-Menu.save-button.name");
        String closeName = config.getConfig().getString("Gui-Menu.close-button.name");
        String vanillaDropsName = config.getConfig().getString("Gui-Menu.vanilla-drops-button.name");
        String backName = config.getConfig().getString("Gui-Menu.back-button.name");
        String toggleDropsName = config.getConfig().getString("Gui-Menu.advanced-drops-button.name");
        String openDropsName = config.getConfig().getString("Gui-Menu.advanced-drops-button-open.name");
        String infoName = config.getConfig().getString("Gui-Menu.info-drops-button.name");


        List<String> saveLore = config.getConfig().getStringList("Gui-Menu.save-button.lore");
        List<String> closeLore = config.getConfig().getStringList("Gui-Menu.close-button.lore");
        List<String> vanillaDropsLore = config.getConfig().getStringList("Gui-Menu.vanilla-drops-button.lore-enabled");
        List<String> backLore = config.getConfig().getStringList("Gui-Menu.back-button.lore");
        List<String> toggleDropsLore = config.getConfig().getStringList("Gui-Menu.advanced-drops-button.lore");
        List<String> openDropsLore = config.getConfig().getStringList("Gui-Menu.advanced-drops-button-open.lore");
        List<String> loreInfo = config.getConfig().getStringList("Gui-Menu.info-drops-button.lore");
        List<String> infoLore = new ArrayList<>();

        for (String loreLine : loreInfo) {
            loreLine = stringUtils.replacePlaceholder(loreLine, "{chance_increase}", "" + config.getConfig().getInt("Gui-Menu.Advanced-Drops.chance-increase-by-click"));
            loreLine = stringUtils.replacePlaceholder(loreLine, "{chance_decrease}", "" + config.getConfig().getInt("Gui-Menu.Advanced-Drops.chance-decrease-by-click"));
            infoLore.add(loreLine);
        }


        InventoryButton saveButton = new InventoryButton(saveMaterial, saveName, saveLore, saveDamage, ButtonType.SAVE);
        saveButton.setGuiIndex(savePosition);
        InventoryButton closeButton = new InventoryButton(closeMaterial, closeName, closeLore, closeDamage, ButtonType.CLOSE);
        closeButton.setGuiIndex(closePosition);
        InventoryButton vanillaDropsButton = new InventoryButton(vanillaDropsMaterial, vanillaDropsName, vanillaDropsLore, vanillaDropsDamage, ButtonType.TOGGLEABLE);
        vanillaDropsButton.setGuiIndex(vanillaDropsPosition);
        InventoryButton backButton = new InventoryButton(backMaterial, backName, backLore, backDamage, ButtonType.BACK);
        backButton.setGuiIndex(backPosition);

        InventoryButton toggleDropsButton = new InventoryButton(toggleDrops, toggleDropsName, toggleDropsLore, toggleDropsDamage, ButtonType.ADVANCED_DROPS);
        toggleDropsButton.setGuiIndex(toggleAdvancedDropsPos);

        InventoryButton openDropsButton = new InventoryButton(openDrops, openDropsName, openDropsLore, openDropsDamage, ButtonType.ADVANCED_MENU_OPEN);
        openDropsButton.setGuiIndex(advancedDropsOpenPos);

        InventoryButton infoButton = new InventoryButton(infoMat, infoName, infoLore, infoDamage, ButtonType.INFO);
        infoButton.setGuiIndex(infoPos);

        mainMenuButtons.put(ButtonType.SAVE.toString(), saveButton);
        mainMenuButtons.put(ButtonType.CLOSE.toString(), closeButton);

        secondaryMenuButtons.put(ButtonType.BACK.toString(), backButton);
        secondaryMenuButtons.put(ButtonType.TOGGLEABLE.toString(), vanillaDropsButton);

        secondaryMenuButtons.put(ButtonType.ADVANCED_DROPS.toString(), toggleDropsButton);
        secondaryMenuButtons.put(ButtonType.ADVANCED_MENU_OPEN.toString(), openDropsButton);

        secondaryMenuButtons.put(ButtonType.INFO.toString(), infoButton);

    }


    @EventHandler
    public void onClose(InventoryCloseEvent e) {

        Inventory inventory = e.getInventory();

        if (inventory == null) return;

        String name = e.getView().getTitle();


        if (!inventories.containsKey(name)) return;

        dataConfig.save(name);
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {

        ItemStack itemStack = e.getCurrentItem();
        Inventory inventory = e.getClickedInventory();

        if (itemStack == null || inventory == null) return;

        Player player = (Player) e.getWhoClicked();

        String title = e.getView().getTitle();

        //main menu click
        if (inventory.equals(mainMenu)) {
            //close button
            if (itemStack.equals(mainMenuButtons.get(ButtonType.CLOSE.toString()).getButton())) {
                player.closeInventory();
                e.setCancelled(true);
                return;
            }
            //save button
            if (itemStack.equals(mainMenuButtons.get(ButtonType.SAVE.toString()).getButton())) {
                for (Mob mob : Mob.values()) {
                    if (mob.getEntityType() == null) continue;
                    dataConfig.getConfigByName(mob.toString()).set("Drops.Items", getInventoryByName(mob.toString(), mob.toString()).getContents());
                    dataConfig.save(mob.toString());
                }
                //send save message
                BeastCore.getInstance().sms(player, config.getConfig().getString("Gui-Menu.drops-saved-message"));
                e.setCancelled(true);
                return;
            }
            //other button
            String name = itemStack.getItemMeta().getDisplayName();
            for (String mob : mobButtons.keySet()) {
                if (mobButtons.get(mob).getButton().getItemMeta().getDisplayName().equals(name)) {
                    e.setCancelled(true);
                    player.closeInventory();
                    player.openInventory(inventories.get(mob).get(mob));
                    return;
                }
            }
        }
        //other inventories
        if (inventories.containsKey(title)) {
            //back button
            if (itemStack.equals(secondaryMenuButtons.get(ButtonType.BACK.toString()).getButton())) {
                player.openInventory(mainMenu);
                e.setCancelled(true);
                return;
            }
            // vanilla drops button

            if (itemStack.getItemMeta() == null || itemStack.getItemMeta().getDisplayName() == null) return;

            if (itemStack.getItemMeta().getDisplayName().equals(secondaryMenuButtons.get(ButtonType.TOGGLEABLE.toString()).getButton().getItemMeta().getDisplayName())) {
                boolean enabled = dataConfig.getConfigByName(title).getBoolean("Vanilla-Drops");
                dataConfig.getConfigByName(title).set("Vanilla-Drops", !enabled);
                dataConfig.save(title);
                e.setCancelled(true);

                ItemMeta meta = secondaryMenuButtons.get(ButtonType.TOGGLEABLE.toString()).getButton().getItemMeta();

                if (dataConfig.getConfigByName(title).getBoolean("Vanilla-Drops")) {
                    meta.setLore(stringUtils.translateLore(config.getConfig().getStringList("Gui-Menu.vanilla-drops-button.lore-enabled")));
                } else {
                    meta.setLore(stringUtils.translateLore(config.getConfig().getStringList("Gui-Menu.vanilla-drops-button.lore-disabled")));
                }
                secondaryMenuButtons.get(ButtonType.TOGGLEABLE.toString()).getButton().setItemMeta(meta);

                inventory.setItem(secondaryMenuButtons.get(ButtonType.TOGGLEABLE.toString()).getGuiIndex(), secondaryMenuButtons.get(ButtonType.TOGGLEABLE.toString()).getButton());
                return;
            }


            //USE ADVANCED DROPS
            if (itemStack.getItemMeta().getDisplayName().equals(secondaryMenuButtons.get(ButtonType.ADVANCED_DROPS.toString()).getButton().getItemMeta().getDisplayName())) {


                boolean enabled = dataConfig.getConfigByName(title).getBoolean("Use-Advanced-Drops");
                dataConfig.getConfigByName(title).set("Use-Advanced-Drops", !enabled);
                dataConfig.save(title);
                e.setCancelled(true);

                ItemMeta meta = secondaryMenuButtons.get(ButtonType.ADVANCED_DROPS.toString()).getButton().getItemMeta();

                if (dataConfig.getConfigByName(title).getBoolean("Use-Advanced-Drops")) {
                    meta.setLore(stringUtils.translateLore(config.getConfig().getStringList("Gui-Menu.advanced-drops-button.lore-enabled")));
                } else {
                    meta.setLore(stringUtils.translateLore(config.getConfig().getStringList("Gui-Menu.advanced-drops-button.lore-disabled")));
                }
                secondaryMenuButtons.get(ButtonType.ADVANCED_DROPS.toString()).getButton().setItemMeta(meta);

                inventory.setItem(secondaryMenuButtons.get(ButtonType.ADVANCED_DROPS.toString()).getGuiIndex(), secondaryMenuButtons.get(ButtonType.ADVANCED_DROPS.toString()).getButton());


                e.setCancelled(true);
                return;
            }


            //OPEN ADVANCED DROPS INVENTORY
            if (itemStack.equals(secondaryMenuButtons.get(ButtonType.ADVANCED_MENU_OPEN.toString()).getButton())) {
                String mob = title;
                player.closeInventory();
                player.openInventory(advancedDropsInventories.get(mob));
                e.setCancelled(true);
            }
        }
    }


    private boolean isButton(ItemStack itemStack) {
        for (InventoryButton button : secondaryMenuButtons.values()) {
            if (button.getButton().equals(itemStack))
                return true;
        }
        return false;
    }


    @EventHandler
    public void onAdvancedInventoryClick(InventoryClickEvent e) {
        ItemStack itemStack = e.getCurrentItem();
        Inventory inventory = e.getClickedInventory();
        String mob = getMobInventory(inventory);


        //no inventory, no item, no mob then return
        if (inventory == null || itemStack == null || mob == null) return;

        //back button
        if (isButton(itemStack)) {
            if (secondaryMenuButtons.get(ButtonType.BACK.toString()).getButton().equals(itemStack))
                e.getWhoClicked().openInventory(inventories.get(mob).get(mob));
            e.setCancelled(true);
            return;
        }

        if (!e.getAction().equals(InventoryAction.PICKUP_ALL) && !e.getAction().equals(InventoryAction.PICKUP_HALF) && !e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY))
            return;

        int slot = e.getSlot();

        //cancel event always
        e.setCancelled(true);


        //remove item from gui
        if (e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
            removeItem(mob, slot);
            return;
        }


        //get item meta to add durability, used as the counter of the chance
        ItemMeta meta = itemStack.getItemMeta();
        int chance = meta.getEnchantLevel(Enchantment.DURABILITY);
        int newChance = chance;


        //increase the chance
        if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
            int addDurability = config.getConfig().getInt("Gui-Menu.Advanced-Drops.chance-increase-by-click");
            newChance = chance + addDurability;
            if (newChance > 100)
                newChance = 100;
        }

        //decrease the chance
        if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
            int addDurability = config.getConfig().getInt("Gui-Menu.Advanced-Drops.chance-decrease-by-click");
            newChance = chance - addDurability;
            if (newChance < 0)
                newChance = 0;
        }

        //add the new meta to the item
        meta.addEnchant(Enchantment.DURABILITY, newChance, true);

        //update
        advancedDropsInventories.get(mob).getItem(slot).setItemMeta(meta);
        updateItemChance(mob, slot);
    }


}
