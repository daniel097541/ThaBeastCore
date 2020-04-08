package info.beastsoftware.beastcore.util;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IEconomyUtil {

    void giveMoney(Player player, double money);

    void giveMoney(OfflinePlayer offlinePlayer, double money);

    double getItemPriceEssentials(ItemStack itemStack);

    double getItemPriceShopGuiPlus(ItemStack itemStack, Player player);

    double getItemPriceDefault(ItemStack itemStack);

    void hookIntoShopGuiPlus();

    void hookIntoEssentials();

    boolean hasEnoughtMoney(Player player, double money);

    void takeMoney(Player player, double money);

    double getPriceBuyShopGuiPlus(ItemStack itemStack, Player player);

}
