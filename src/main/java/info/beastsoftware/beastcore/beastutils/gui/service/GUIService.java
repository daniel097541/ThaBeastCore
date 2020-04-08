package info.beastsoftware.beastcore.beastutils.gui.service;

import info.beastsoftware.beastcore.beastutils.gui.entity.IButton;
import info.beastsoftware.beastcore.beastutils.gui.entity.IPage;
import info.beastsoftware.beastcore.beastutils.gui.repository.IGUIRepository;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class GUIService implements IGUIService {

    private HashMap<Player, IGUIRepository> guiHash;

    public GUIService() {
        this.guiHash = new HashMap<>();
    }

    @Override
    public void createGui(Player owner, IGUIRepository iguiRepository) {
        this.guiHash.put(owner, iguiRepository);
    }

    @Override
    public Inventory getPageGuiInventory(Player owner, int page) {
        return guiHash.get(owner).getPage(page).getPageInventory();
    }

    @Override
    public void removeGui(Player owner) {
        this.guiHash.remove(owner);
    }

    @Override
    public boolean hasGui(Player owner) {
        return guiHash.containsKey(owner);
    }

    @Override
    public boolean isGui(Player owner, Inventory inventory) {
        return getPageIndexFromGui(owner, inventory) != -1;
    }

    @Override
    public boolean isGui(Inventory inventory) {
        for (IGUIRepository gui : guiHash.values())
            for (IPage page : gui.getPages())
                if (page.getPageInventory().equals(inventory))
                    return true;
        return false;
    }

    @Override
    public Player getOwnerFromGui(Inventory inventory) {
        for (Map.Entry<Player, IGUIRepository> entry : guiHash.entrySet())
            for (IPage page : entry.getValue().getPages())
                if (page.getPageInventory().equals(inventory))
                    return entry.getKey();
        return null;
    }

    @Override
    public int getPageIndexFromGui(Player owner, Inventory inventory) {
        for (Map.Entry<Player, IGUIRepository> entry : guiHash.entrySet())
            for (IPage page : entry.getValue().getPages())
                if (page.getPageInventory().equals(inventory))
                    return page.getPageIndex();
        return -1;
    }

    @Override
    public String getButtonRole(Player owner, int pageIndex, ItemStack clickedItem) {
        for (IButton button : guiHash.get(owner).getPage(pageIndex).getButtons())
            if (button.getItem().equals(clickedItem))
                return button.getButtonRole();
        return null;
    }

    @Override
    public IButton getButton(Player owner, int pageIndex, ItemStack itemStack) {

        for (IPage page : guiHash.get(owner).getPages())
            for (IButton button : page.getButtons())
                if (button.getItem().equals(itemStack))
                    return button;

        return null;
    }

    @Override
    public void replaceButton(Player owner, int pageIndex, IButton button) {
        guiHash.get(owner).getPage(pageIndex).replaceButton(button);
    }

    @Override
    public void reload() {
        for (Player owner : guiHash.keySet())
            removeGui(owner);
    }
}
