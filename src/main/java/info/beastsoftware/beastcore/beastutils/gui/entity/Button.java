package info.beastsoftware.beastcore.beastutils.gui.entity;

import org.bukkit.inventory.ItemStack;

public class Button implements IButton {


    private int position;
    private String buttonRole;
    private ItemStack item;


    public Button(int position, String buttonRole, ItemStack item) {
        this.position = position;
        this.buttonRole = buttonRole;
        this.item = item;
    }


    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String getButtonRole() {
        return buttonRole;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void setItem(ItemStack item) {
        this.item = item;
    }


}
