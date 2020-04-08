package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.HopperFilterGUIOpenEvent;
import info.beastsoftware.beastcore.event.gui.HopperFilterReloadEvent;
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

public class HopperFilterGui extends AbstractSimpleGui {


    public HopperFilterGui(IConfig config) {
        super(config);
        instance = this;
        savePath = "Hopper-Filter.filtered-items";
        togglePath = "Hopper-Filter.enabled";
        savedMessagePath = "Hopper-Filter.menu.save-message";
        initGui();
    }


    private static HopperFilterGui instance;


    public static HopperFilterGui getInstance() {
        return instance;
    }


    @EventHandler
    public void onOpen(HopperFilterGUIOpenEvent event){
        BeastPlayer player = event.getPlayer();
        player.openInventory(getMenu());
    }


    @EventHandler
    public void onReload(HopperFilterReloadEvent event){
        this.reload();
    }

    public boolean isListed(Material material) {

        Inventory inventory = getMenu();

        for (ItemStack itemStack : inventory) {

            if (itemStack == null) continue;

            if (itemStack.getType().toString().equalsIgnoreCase(config.getConfig().getString("Hopper-Filter.menu.toggle-button.material")) ||
                    itemStack.getType().toString().equalsIgnoreCase(config.getConfig().getString("Hopper-Filter.menu.save-button.material")) ||
                    itemStack.getType().toString().equalsIgnoreCase(config.getConfig().getString("Hopper-Filter.menu.close-button.material")))
                continue;

            if (itemStack.getType().toString().equalsIgnoreCase(material.toString()))
                return true;
        }

        return false;
    }

    @Override
    public void createInventory() {
        menu = Bukkit.createInventory(null, config.getConfig().getInt("Hopper-Filter.menu.gui-size"), ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Hopper-Filter.menu.gui-title")));
    }

    @Override
    public void createButtons() {
        Material saveMaterial = Material.getMaterial(config.getConfig().getString("Hopper-Filter.menu.save-button.material"));
        Material closeMaterial = Material.getMaterial(config.getConfig().getString("Hopper-Filter.menu.close-button.material"));
        Material toggleMaterial = Material.getMaterial(config.getConfig().getString("Hopper-Filter.menu.toggle-button.material"));

        String saveName = config.getConfig().getString("Hopper-Filter.menu.save-button.name");
        String toggleName = config.getConfig().getString("Hopper-Filter.menu.toggle-button.name");
        String closeName = config.getConfig().getString("Hopper-Filter.menu.close-button.name");

        List<String> saveLore = config.getConfig().getStringList("Hopper-Filter.menu.save-button.lore");
        List<String> closeLore = config.getConfig().getStringList("Hopper-Filter.menu.close-button.lore");
        List<String> toggleLore;
        if (config.getConfig().getBoolean("Hopper-Filter.enabled"))
            toggleLore = config.getConfig().getStringList("Hopper-Filter.menu.toggle-button.lore-enabled");
        else toggleLore = config.getConfig().getStringList("Hopper-Filter.menu.toggle-button.lore-disabled");

        Short saveDamage = new Short(config.getConfig().getString("Hopper-Filter.menu.save-button.damage"));
        Short closeDamage = new Short(config.getConfig().getString("Hopper-Filter.menu.close-button.damage"));
        Short toggleDamage = new Short(config.getConfig().getString("Hopper-Filter.menu.toggle-button.damage"));

        saveButton = new InventoryButton(saveMaterial, saveName, saveLore, saveDamage, ButtonType.SAVE);
        saveButton.setGuiIndex(config.getConfig().getInt("Hopper-Filter.menu.save-button.position"));
        closeButton = new InventoryButton(closeMaterial, closeName, closeLore, closeDamage, ButtonType.CLOSE);
        closeButton.setGuiIndex(config.getConfig().getInt("Hopper-Filter.menu.close-button.position"));
        toggleButton = new InventoryButton(toggleMaterial, toggleName, toggleLore, toggleDamage, ButtonType.TOGGLE);
        toggleButton.setGuiIndex(config.getConfig().getInt("Hopper-Filter.menu.toggle-button.position"));
    }


}
