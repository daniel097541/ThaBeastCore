package info.beastsoftware.beastcore.beastutils.gui.entity;

import org.bukkit.inventory.ItemStack;

public interface IButton {

    int getPosition();

    void setPosition(int position);

    String getButtonRole();

    ItemStack getItem();

    void setItem(ItemStack item);
}
