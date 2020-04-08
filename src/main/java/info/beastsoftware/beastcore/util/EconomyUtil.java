package info.beastsoftware.beastcore.util;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Worth;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class EconomyUtil implements IEconomyUtil {


    private Economy economy;
    private Worth essentialsWorth;

    public EconomyUtil(Economy economy) {
        this.economy = economy;
    }

    @Override
    public void hookIntoEssentials() {
        Essentials essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        if (essentials == null) {
            Bukkit.getLogger().info("Essentials is not installed in your server, hook is auto-disabling!");
            return;
        }
        essentialsWorth = essentials.getWorth();
    }

    @Override
    public boolean hasEnoughtMoney(Player player, double money) {
        return economy.getBalance(player) >= money;
    }

    @Override
    public void takeMoney(Player player, double money) {
        economy.withdrawPlayer(player, money);
    }

    @Override
    public double getPriceBuyShopGuiPlus(ItemStack itemStack, Player player) {
        return ShopGuiPlusApi.getItemStackPriceBuy(player, itemStack);
    }

    @Override
    public void hookIntoShopGuiPlus() {
        Plugin shopGuiPlugin = Bukkit.getPluginManager().getPlugin("ShopGUIPlus");
        if (shopGuiPlugin == null) {
            Bukkit.getLogger().info("ShopGuiPlus is not installed in your server, hook is auto-disabling!");
        }
    }


    @Override
    public double getItemPriceDefault(ItemStack itemStack) {
        double price;
        int amount = itemStack.getAmount();
        IConfig config = BeastCore.getInstance().getApi().getPricesConfig();


        //config prices and default prices
        Set<String> items = config.getConfig().getConfigurationSection("Item-Worths").getKeys(false);

        //item not specified
        if (!items.contains(itemStack.getType().toString())) {
            return 0.0;
        }

        String type = null;

        //item contained, look for the price
        for (String item : items) {
            if (item.equalsIgnoreCase(itemStack.getType().toString())) {

                short damage = Short.parseShort(config.getConfig().getString("Item-Worths." + item + ".damage"));

                //if damage of the item is not the same, continue looking for
                if (damage != itemStack.getDurability())
                    continue;

                type = item;
                break;
            }
        }

        //the damage is not the same, return 0
        if (type == null)
            return 0.0;

        //set the price and return
        price = amount * config.getConfig().getDouble("Item-Worths." + type + ".price");
        return price;
    }


    @Override
    public void giveMoney(Player player, double money) {
        economy.depositPlayer(player, money);
    }

    @Override
    public void giveMoney(OfflinePlayer offlinePlayer, double money) {
        economy.depositPlayer(offlinePlayer, money);
    }

    @Override
    public double getItemPriceEssentials(ItemStack itemStack) {
        if(essentialsWorth == null){
            this.hookIntoEssentials();
        }

        if (itemStack == null) return 0;
        if (essentialsWorth.getPrice(itemStack) == null) return 0;
        return itemStack.getAmount() * essentialsWorth.getPrice(itemStack).doubleValue();
    }

    @Override
    public double getItemPriceShopGuiPlus(ItemStack itemStack, Player player) {
        return ShopGuiPlusApi.getItemStackPriceSell(player, itemStack);
    }


}
