package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.annotations.features.Enchant;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class EnchantmentsFeatureListener extends AbstractFeatureListener {

    public EnchantmentsFeatureListener(IConfig config) {
        super(config, FeatureType.ENCHANTMENTS);
    }



    private boolean isEnchantBook(ItemStack itemStack){
        return itemStack.getType().equals(Material.ENCHANTED_BOOK);
    }

    private List<Enchantment> getAllowedEnchantments(ItemStack itemStack) {
        String path = "click-enchantments-allowed." + itemStack.getType();

        return this.config
                .getConfig()
                .getStringList(path)
                .stream()
                .map(Enchantment::getByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    private boolean areAllEnchantsAllowedInBook(List<Enchantment> allowed, List<Enchantment> fromBook){
        return allowed
                .containsAll(fromBook);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBookApply(InventoryClickEvent event){

        //cancelled or disabled
        if(event.isCancelled() || !this.isOn()) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack book = event.getCursor();
        ItemStack tool = event.getCurrentItem();

        String permission = config.getConfig().getString("click-enchant-permission");

        if(!player.hasPermission(permission)) return;

        //check items
        if(book == null || book.getType().equals(Material.AIR) || tool == null || tool.getType().equals(Material.AIR)) return;

        boolean isBook = isEnchantBook(book);
        List<Enchantment> enchantments = getAllowedEnchantments(tool);


        //not a book or not enchantments allowed for this tool
        if(!isBook || enchantments.isEmpty()) return;

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta)book.getItemMeta();
        Map<Enchantment, Integer> bookEnchants = Objects.requireNonNull(meta).getStoredEnchants();


        //not all enchants in the book are allowed for this tool
        if(!areAllEnchantsAllowedInBook(enchantments, new ArrayList(bookEnchants.keySet()))){
            String message = config.getConfig().getString("all-enchants-must-be-allowed-in-tool");
            StrUtils.sms(player, message);
            event.setCancelled(true);
            return;
        }




        //remove the book
        event.setCancelled(true);
        event.setCursor(null);

        //if we are here we have to apply the enchants to the item
        bookEnchants
                .forEach(tool::addUnsafeEnchantment);

    }


}
