package info.beastsoftware.beastcore.beastutils.utils;

import info.beastsoftware.beastcore.beastutils.gui.entity.IButton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class IInventoryUtil {

    //empty slot check
    public static boolean hasEmptySlot(Inventory inventory) {
        for (ItemStack itemStack : inventory) {
            if (itemStack == null || itemStack.getType().equals(Material.AIR))
                return true;
        }
        return false;
    }


    //stackeable slot check
    public static int hasEmptySlot(Inventory inventory, ItemStack item) {

        int slot = 0;
        for (ItemStack itemStack : inventory) {

            if (itemStack == null) continue;

            if (!itemStack.getType().equals(item.getType()) || itemStack.getAmount() >= 64) {
                slot++;
                continue;
            }

            ItemMeta meta = item.getItemMeta();
            ItemMeta meta2 = itemStack.getItemMeta();

            if (!meta.getDisplayName().equalsIgnoreCase(meta2.getDisplayName()) || !meta.getLore().containsAll(meta2.getLore())) {
                slot++;
                continue;
            }

            if (meta.getItemFlags().containsAll(meta2.getItemFlags()))
                return slot;

        }
        return -1;
    }


    //add item into inv
    public static void addItem(ItemStack itemStack, Player player, boolean empty, int number, int slot) {
        //add to first slot free
        if (empty) {
            itemStack.setAmount(number);
            player.getInventory().setItem(player.getInventory().firstEmpty(), itemStack);
        }

        //look for stacks of items
        else {
            int amount = player.getInventory().getItem(slot).getAmount() + number;
            if (amount > 64) {
                player.getInventory().getItem(slot).setAmount(64);
                itemStack.setAmount(amount - 64);
                player.getWorld().dropItem(player.getLocation(), itemStack);
            } else player.getInventory().getItem(slot).setAmount(amount);
        }
    }

    //create items
    public static ItemStack createItem(int number, Material material, String name, List<String> lore, Short damage, boolean itemflags) {
        ItemStack item = new ItemStack(material, number);
        item.setDurability(damage);

        ItemMeta meta = item.getItemMeta();
        List<String> loreTrans = StrUtils.translateLore(lore);
        meta.setLore(loreTrans);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        if (itemflags && !Bukkit.getBukkitVersion().contains("1.7"))
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createItem(int number, Material material, String name, List<String> lore, boolean itemflags) {
        return createItem(number, material, name, lore, Short.valueOf("0"), itemflags);
    }


    public static boolean isEmpty(Inventory inventory) {
        for (ItemStack itemStack : inventory)
            if (itemStack != null) return false;

        return true;
    }


    public static int getAmountOfBlocks(Inventory inventory, Material material) {
        int amount = 0;

        for (ItemStack itemStack : inventory) {

            if (itemStack == null) continue;
            if (itemStack.getType().equals(material))
                amount += itemStack.getAmount();

        }

        return amount;
    }


    public static void removeItemFromInventory(Inventory inventory, Material material, int amount) {

        int slot = 0;


        for (ItemStack itemStack : inventory) {

            if (amount <= 0) return;

            if (itemStack == null || !itemStack.getType().equals(material)) {
                slot++;
                continue;
            }

            //remove the amount and return
            if (itemStack.getAmount() > amount) {
                itemStack.setAmount(itemStack.getAmount() - amount);
                return;
            }

            //same amount remove item
            if (itemStack.getAmount() == amount) {
                inventory.setItem(slot, null);
                return;
            }

            //less amount, remove and continue loop
            amount -= itemStack.getAmount();
            inventory.setItem(slot, null);
            slot++;
        }

    }

    public static boolean canAddItem(Material material, int amount, Inventory inventory) {

        int added = 0;

        for (ItemStack itemStack : inventory) {

            if (itemStack == null) return true;

            if (!itemStack.getType().equals(material)) continue;

            if (added >= amount) return true;

            if (itemStack.getAmount() + amount < 64) return true;

            added += 64 - itemStack.getAmount();
        }

        return added >= amount;
    }


    public static void addButtonsToInventory(List<IButton> buttons, Inventory inventory) {
        for (IButton button : buttons)
            inventory.setItem(button.getPosition(), button.getItem());
    }


    public static boolean materialCanBeDisplayed(Material material) {
        Inventory testInv = Bukkit.createInventory(null, 54);
        ItemStack itemStack = new ItemStack(material);
        testInv.setItem(0, itemStack);
        return testInv.getItem(0) != null;
    }


    public static void fillEmptySlots(Inventory inventory, ItemStack itemStack) {
        int cont = 0;
        for (ItemStack slot : inventory) {
            if (slot == null || slot.getType().equals(Material.AIR))
                inventory.setItem(cont, itemStack);

            cont++;
        }
    }


}
