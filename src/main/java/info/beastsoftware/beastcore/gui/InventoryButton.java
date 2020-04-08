package info.beastsoftware.beastcore.gui;

import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.struct.ButtonType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryButton {

    private ItemStack button;
    private int guiIndex;
    private ButtonType type;


    public InventoryButton(Material material, String name, List<String> lore, Short damage, ButtonType type) {
        createButton(material, name, lore, damage, type);
    }

    public InventoryButton(Material material, String name, List<String> lore, ButtonType type) {
        createButton(material, name, lore, type);
    }

    public InventoryButton(Material material, String name, ButtonType type) {
        createButton(material, name, type);
    }

    public InventoryButton(ItemStack itemStack) {
        this.button = itemStack;
    }

    public void createButton(Material material, String name, List<String> lore, Short damage, ButtonType type) {
        button = IInventoryUtil.createItem(1, material, name, lore, damage, false);
        this.type = type;
        /*
        //translate the name to see colors
        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        //translate the lore to see colors
        meta.setLore(BeastCore.getInstance().getStringUtils().translateLore(lore));

        //add item meta to button and set damage
        button.setItemMeta(meta);
        button.setDurability(damage);

       */

        setType(type);
    }

    public void createButton(Material material, String name, List<String> lore, ButtonType type) {
        createButton(material, name, lore, new Short("0"), type);
    }

    public void createButton(Material material, String name, ButtonType type) {
        createButton(material, name, new ArrayList<>(), new Short("0"), type);
    }

    public ItemStack getButton() {
        return button;
    }

    public int getGuiIndex() {
        return guiIndex;
    }

    public void setGuiIndex(int guiIndex) {
        this.guiIndex = guiIndex;
    }

    public ButtonType getType() {
        return type;
    }

    public void setType(ButtonType type) {
        this.type = type;
    }
}
