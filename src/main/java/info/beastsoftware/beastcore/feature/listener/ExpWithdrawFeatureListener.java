package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IPlayerUtil;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ExpWithdrawFeatureListener extends AbstractFeatureListener {


    public ExpWithdrawFeatureListener(IConfig config) {
        super(config, FeatureType.EXP_WITHDRAW);
    }


    @EventHandler
    public void onBottleUse(PlayerInteractEvent e) {

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;

        ItemStack itemStack = e.getPlayer().getItemInHand();

        if (itemStack == null || itemStack.getType().equals(Material.AIR)) return;

        if (!isExpBottle(itemStack))
            return;

        //cancel event
        e.setCancelled(true);

        Player player = e.getPlayer();
        BeastPlayer beastPlayer = this.getPlayer(e.getPlayer());

        //do not allow money withdraw in printer mode
        if(this.getApi().isOnPrinterMode(beastPlayer)){
            beastPlayer.sms("&cYou cannot do that while in printer mode!");
            return;
        }


        //disabled feature
        if (!isOn()) {
            BeastCore.getInstance().sms(player, config.getConfig().getString("Settings.disabled-message"));
            return;
        }

        String type = getTypeFromBottle(itemStack);

        int amount = getAmountFromBottle(itemStack, type);

        if (type.equalsIgnoreCase("level"))
            player.setLevel(player.getLevel() + amount);

        else {
            IPlayerUtil.changePlayerExp(player, amount);
        }

        if (player.getItemInHand().getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        } else
            player.setItemInHand(null);
    }


    public boolean isExpBottle(ItemStack bottle) {
        String name = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Item.name"));
        Material material = Material.valueOf(config.getConfig().getString("Item.material"));
        Short damage = new Short(config.getConfig().getString("Item.damage"));


        if (bottle.getItemMeta() == null) return false;
        if (bottle.getItemMeta().getDisplayName() == null) return false;
        if (!bottle.getItemMeta().getDisplayName().equalsIgnoreCase(name)) return false;
        if (!bottle.getType().equals(material)) return false;
        if (bottle.getDurability() != damage) return false;
        ItemMeta meta = bottle.getItemMeta();
        return meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES) && meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS) && meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE);
    }

    private String getTypeFromBottle(ItemStack bottle) {
        if (bottle.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS))
            return "level";
        return "raw";
    }

    private int getAmountFromBottle(ItemStack bottle, String type) {
        if (type.equalsIgnoreCase("level"))
            return bottle.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        return bottle.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
    }

}
