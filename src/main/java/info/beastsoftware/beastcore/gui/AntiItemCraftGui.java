package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.AntiItemCraftOpenEvent;
import info.beastsoftware.beastcore.event.gui.AntiItemCraftReloadEvent;
import info.beastsoftware.beastcore.struct.ButtonType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AntiItemCraftGui extends AbstractSimpleGui {


    public AntiItemCraftGui(IConfig config) {
        super(config);
        instance = this;
        savePath = "Anti-Item-Craft.filtered-items";
        togglePath = "Anti-Item-Craft.enabled";
        savedMessagePath = "Anti-Item-Craft.menu.save-message";
        initGui();
    }


    private static AntiItemCraftGui instance;

    public static AntiItemCraftGui getInstance() {
        return instance;
    }


    public boolean isListed(Material material) {

        Inventory inventory = getMenu();

        for (ItemStack itemStack : inventory) {

            if (itemStack == null) continue;

            if (itemStack.getType().toString().equalsIgnoreCase(config.getConfig().getString("Anti-Item-Craft.menu.toggle-button.material")) ||
                    itemStack.getType().toString().equalsIgnoreCase(config.getConfig().getString("Anti-Item-Craft.menu.save-button.material"))
                    ||
                    itemStack.getType().toString().equalsIgnoreCase(config.getConfig().getString("Anti-Item-Craft.menu.close-button.material")))
                continue;

            if (itemStack.getType().toString().equalsIgnoreCase(material.toString()))
                return true;
        }

        return false;
    }

    @EventHandler
    public void onInventoryOpenEvent(AntiItemCraftOpenEvent event) {
        BeastPlayer player = event.getPlayer();
        player.openInventory(getMenu());
    }

    @Override
    public void createInventory() {
        menu = Bukkit.createInventory(null, config.getConfig().getInt("Anti-Item-Craft.menu.gui-size"), ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Anti-Item-Craft.menu.gui-title")));
    }

    @Override
    public void createButtons() {
        Material saveMaterial = Material.getMaterial(config.getConfig().getString("Anti-Item-Craft.menu.save-button.material"));
        Material closeMaterial = Material.getMaterial(config.getConfig().getString("Anti-Item-Craft.menu.close-button.material"));
        Material toggleMaterial = Material.getMaterial(config.getConfig().getString("Anti-Item-Craft.menu.toggle-button.material"));

        String saveName = config.getConfig().getString("Anti-Item-Craft.menu.save-button.name");
        String toggleName = config.getConfig().getString("Anti-Item-Craft.menu.toggle-button.name");
        String closeName = config.getConfig().getString("Anti-Item-Craft.menu.close-button.name");

        List<String> saveLore = config.getConfig().getStringList("Anti-Item-Craft.menu.save-button.lore");
        List<String> closeLore = config.getConfig().getStringList("Anti-Item-Craft.menu.close-button.lore");
        List<String> toggleLore;
        if (config.getConfig().getBoolean("Anti-Item-Craft.enabled"))
            toggleLore = config.getConfig().getStringList("Anti-Item-Craft.menu.toggle-button.lore-enabled");
        else toggleLore = config.getConfig().getStringList("Anti-Item-Craft.menu.toggle-button.lore-disabled");

        Short saveDamage = new Short(config.getConfig().getString("Anti-Item-Craft.menu.save-button.damage"));
        Short closeDamage = new Short(config.getConfig().getString("Anti-Item-Craft.menu.close-button.damage"));
        Short toggleDamage = new Short(config.getConfig().getString("Anti-Item-Craft.menu.toggle-button.damage"));

        saveButton = new InventoryButton(saveMaterial, saveName, saveLore, saveDamage, ButtonType.SAVE);
        saveButton.setGuiIndex(config.getConfig().getInt("Anti-Item-Craft.menu.save-button.position"));
        closeButton = new InventoryButton(closeMaterial, closeName, closeLore, closeDamage, ButtonType.CLOSE);
        closeButton.setGuiIndex(config.getConfig().getInt("Anti-Item-Craft.menu.close-button.position"));
        toggleButton = new InventoryButton(toggleMaterial, toggleName, toggleLore, toggleDamage, ButtonType.TOGGLE);
        toggleButton.setGuiIndex(config.getConfig().getInt("Anti-Item-Craft.menu.toggle-button.position"));
    }


    @EventHandler
    public void onReload(AntiItemCraftReloadEvent event){
        this.reload();
    }

}
