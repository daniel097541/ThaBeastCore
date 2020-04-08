package info.beastsoftware.beastcore.beastutils.gui.entity;

import org.bukkit.inventory.Inventory;

import java.util.List;

public class Page implements IPage {


    private int pageIndex;
    private Inventory pageInventory;
    private List<IButton> buttonList;


    public Page(int pageIndex, Inventory pageInventory, List<IButton> buttonList) {
        this.pageIndex = pageIndex;
        this.pageInventory = pageInventory;
        this.buttonList = buttonList;
    }


    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public Inventory getPageInventory() {
        return pageInventory;
    }

    @Override
    public List<IButton> getButtons() {
        return buttonList;
    }

    @Override
    public void replaceButton(IButton button) {
        pageInventory.setItem(button.getPosition(), button.getItem());
    }
}
