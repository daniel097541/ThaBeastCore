package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.CropHopperGUIOpenEvent;
import info.beastsoftware.beastcore.struct.ButtonType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CropHoppersGui extends AbstractSimpleGui {
    public CropHoppersGui(IConfig config) {
        super(config);
        instance = this;
        togglePath = "Crop-Hoppers.enabled";
        savePath = "Crop-Hoppers.filtered-items";
        savedMessagePath = "Crop-Hoppers.save-message";
        initGui();
    }


    @EventHandler
    public void onOpen(CropHopperGUIOpenEvent event){
        event.getPlayer().openInventory(this.getMenu());
    }

    private static CropHoppersGui instance;


    public static CropHoppersGui getInstance() {
        return instance;
    }

    public boolean isItemContained(ItemStack itemStack){
        return menu.contains(itemStack.getType());
    }


    @Override
    public void createInventory() {
        menu = Bukkit.createInventory(null, config.getConfig().getInt("Crop-Hoppers.Menu.size"), ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Crop-Hoppers.Menu.title")));
    }

    @Override
    public void createButtons() {

        String path = "Crop-Hoppers.Menu.";
        Material saveMaterial = Material.getMaterial(config.getConfig().getString(path + "save-button.material"));
        Material closeMaterial = Material.getMaterial(config.getConfig().getString(path + "close-button.material"));
        Material toggleMaterial = Material.getMaterial(config.getConfig().getString(path + "toggle-button.material"));

        Short saveDamage = new Short(config.getConfig().getString(path + "save-button.damage"));
        Short closeDamage = new Short(config.getConfig().getString(path + "close-button.damage"));
        Short toggleDamage = new Short(config.getConfig().getString(path + "toggle-button.damage"));

        String saveName = config.getConfig().getString(path + "save-button.name");
        String closeName = config.getConfig().getString(path + "close-button.name");
        String toggleName = config.getConfig().getString(path + "toggle-button.name");

        List<String> saveLore = config.getConfig().getStringList(path + "save-button.lore");
        List<String> closeLore = config.getConfig().getStringList(path + "close-button.lore");
        List<String> toggleLore;
        if (config.getConfig().getBoolean("Crop-Hoppers.enabled"))
            toggleLore = config.getConfig().getStringList(path + "toggle-button.lore-enabled");
        else toggleLore = config.getConfig().getStringList(path + "toggle-button.lore-disabled");

        int savePos = config.getConfig().getInt(path + "save-button.position");
        int closePos = config.getConfig().getInt(path + "close-button.position");
        int togglePos = config.getConfig().getInt(path + "toggle-button.position");


        saveButton = new InventoryButton(saveMaterial, saveName, saveLore, saveDamage, ButtonType.SAVE);
        saveButton.setGuiIndex(savePos);
        closeButton = new InventoryButton(closeMaterial, closeName, closeLore, closeDamage, ButtonType.CLOSE);
        closeButton.setGuiIndex(closePos);
        toggleButton = new InventoryButton(toggleMaterial, toggleName, toggleLore, toggleDamage, ButtonType.TOGGLE);
        toggleButton.setGuiIndex(togglePos);

    }
}
