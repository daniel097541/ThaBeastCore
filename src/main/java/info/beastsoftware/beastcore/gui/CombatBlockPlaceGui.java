package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.CombatGuiOpenEvent;
import info.beastsoftware.beastcore.struct.ButtonType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CombatBlockPlaceGui extends AbstractSimpleGui {
    private InventoryButton backButton;

    public CombatBlockPlaceGui(IConfig config) {
        super(config);
        instance = this;
        savePath = "Combat-Tag.Blocked-Items";
        initGui();
    }


    private static CombatBlockPlaceGui instance;


    public static CombatBlockPlaceGui getInstance() {
        return instance;
    }

    public boolean isBlockedBlock(Block block) {
        for (ItemStack itemStack : getMenu().getContents()) {

            if (itemStack == null) continue;

            if (isAButton(itemStack))
                continue;

            if (itemStack.getType().equals(block.getType())) {
                return true;
            }

        }

        return false;
    }


    @Override
    public void createInventory() {
        int size = config.getConfig().getInt("Combat-Tag.gui.size");
        String title = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Combat-Tag.gui.title"));
        menu = Bukkit.createInventory(null, size, title);
    }

    @Override
    public void createButtons() {
        String guiPath = "Combat-Tag.gui.";
        String guiButtons = guiPath + "Buttons.";

        String saveName = config.getConfig().getString(guiButtons + "Save-Button.name");
        Material saveMat = Material.getMaterial(config.getConfig().getString(guiButtons + "Save-Button.material"));
        Short saveDamage = new Short(config.getConfig().getInt(guiButtons + "Save-Button.damage") + "");
        List<String> saveLore = config.getConfig().getStringList(guiButtons + "Save-Button.disabled-lore");
        int savePos = config.getConfig().getInt(guiButtons + "Save-Button.position-in-gui");

        String backName = config.getConfig().getString(guiButtons + "Back-Button.name");
        Material backMat = Material.getMaterial(config.getConfig().getString(guiButtons + "Back-Button.material"));
        Short backDamage = new Short(config.getConfig().getInt(guiButtons + "Back-Button.damage") + "");
        List<String> backLore = config.getConfig().getStringList(guiButtons + "Back-Button.disabled-lore");
        int backPos = config.getConfig().getInt(guiButtons + "Back-Button.position-in-gui");

        saveButton = new InventoryButton(saveMat, saveName, saveLore, saveDamage, ButtonType.SAVE);
        saveButton.setGuiIndex(savePos);
        backButton = new InventoryButton(backMat, backName, backLore, backDamage, ButtonType.BACK);
        backButton.setGuiIndex(backPos);
    }

    @Override
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Inventory inventory = e.getClickedInventory();
        ItemStack itemStack = e.getCurrentItem();

        if (inventory == null || itemStack == null) return;

        if (!inventory.equals(menu)) return;

        BeastPlayer player = this.getPlayer((Player) e.getWhoClicked());
        e.setCancelled(true);

        //back button
        if (itemStack.equals(backButton.getButton())) {
            player.getBukkitPlayer().closeInventory();
            Bukkit.getPluginManager().callEvent(new CombatGuiOpenEvent(player));
            return;
        }

        //save button
        if (itemStack.equals(saveButton.getButton())) {
            saveContents(savePath);
        }


    }


    @Override
    public void initGui() {
        createInventory();
        createButtons();
        loadContents(savePath);
        menu.setItem(backButton.getGuiIndex(), backButton.getButton());
        menu.setItem(saveButton.getGuiIndex(), saveButton.getButton());
    }


    @Override
    public boolean isAButton(ItemStack itemStack) {
        return backButton.getButton().equals(itemStack) || saveButton.getButton().equals(itemStack);
    }

}
