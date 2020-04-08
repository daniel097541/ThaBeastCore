package info.beastsoftware.beastcore.beastutils.gui.service;

import info.beastsoftware.beastcore.beastutils.gui.entity.IButton;
import info.beastsoftware.beastcore.beastutils.gui.repository.IGUIRepository;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IGUIService {

    void createGui(Player owner, IGUIRepository gui);

    Inventory getPageGuiInventory(Player owner, int page);

    void removeGui(Player owner);

    boolean hasGui(Player owner);

    boolean isGui(Player owner, Inventory inventory);

    boolean isGui(Inventory inventory);

    Player getOwnerFromGui(Inventory inventory);

    int getPageIndexFromGui(Player owner, Inventory inventory);

    String getButtonRole(Player owner, int pageIndex, ItemStack clickedItem);

    IButton getButton(Player owner, int pageIndex, ItemStack itemStack);

    void replaceButton(Player owner, int pageIndex, IButton newButton);

    void reload();
}
