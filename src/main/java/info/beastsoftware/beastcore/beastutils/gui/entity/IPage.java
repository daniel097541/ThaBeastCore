package info.beastsoftware.beastcore.beastutils.gui.entity;

import org.bukkit.inventory.Inventory;

import java.util.List;

public interface IPage {
    int getPageIndex();

    Inventory getPageInventory();

    List<IButton> getButtons();

    void replaceButton(IButton button);
}
