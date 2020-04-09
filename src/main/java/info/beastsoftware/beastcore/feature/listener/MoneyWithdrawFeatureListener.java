package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.event.itemgiveevent.BankNoteGiveEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.EconomyUtil;
import info.beastsoftware.beastcore.util.StringUtils;
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

import java.util.List;
import java.util.Objects;

public class MoneyWithdrawFeatureListener extends AbstractFeatureListener {


    private EconomyUtil economyUtil;


    public MoneyWithdrawFeatureListener(IConfig config, IHookManager hookManager) {
        super(config, FeatureType.MONEY_WITHDRAW);
        this.economyUtil = new EconomyUtil(hookManager.getEconomyHook().getEconomy());
    }




    private ItemStack createPaper(int amount, Player player) {
        Material material = Material.getMaterial(config.getConfig().getString("Item.material"));
        Short damage = Short.valueOf(config.getConfig().getInt("Item.damage") + "");
        String name = config.getConfig().getString("Item.name");
        name = stringUtil.replacePlaceholder(name, "{player}", player.getName());
        List<String> lore = config.getConfig().getStringList("Item.lore");
        lore = stringUtil.replacePlaceholder(lore, "{amount}", amount + "");
        lore = stringUtil.replacePlaceholder(lore, "{player}", player.getName());
        ItemStack itemStack = IInventoryUtil.createItem(1, material, name, lore, damage, true);
        itemStack.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, amount);
        return itemStack;
    }

    @EventHandler
    public void onBankNoteGive(BankNoteGiveEvent event) {
        BeastPlayer player = event.getPlayer();
        int amount = event.getAmount();
        player.getInventory().addItem(createPaper(amount, player.getBukkitPlayer()));
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        if (!isOn()) return;

        ItemStack itemStack = e.getPlayer().getItemInHand();

        if (itemStack == null) return;

        if (!isBankNote(itemStack)) return;

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;

        e.setCancelled(true);

        BeastPlayer player = this.getPlayer(e.getPlayer());

        //do not allow money withdraw in printer mode
        if(this.getApi().isOnPrinterMode(getPlayer(e.getPlayer()))){
            player.sms("&cYou cannot do that while in printer mode!");
            return;
        }

        if (e.getPlayer().getItemInHand().getAmount() == 1)
            e.getPlayer().setItemInHand(null);
        else
            e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);


        int amount = 0;

        for (String line : itemStack.getItemMeta().getLore()) {
            try {
                amount = Integer.parseInt((Objects.requireNonNull(replacer(line)).replaceAll("[\\D]", "")));
            } catch (Exception ignored) {
            }
        }

        economyUtil.giveMoney(e.getPlayer(), amount);

        String message = config.getConfig().getString("message-deposit-money");
        StringUtils stringUtils = new StringUtils();
        message = stringUtils.replacePlaceholder(message, "{amount}", amount + "");
        BeastCore.getInstance().sms(e.getPlayer(), message);

    }

    private String replacer(String line) {
        String replaced = ChatColor.stripColor(line);
        if (replaced == null) return null;
        for (int i = 0; i < 10; i++) {
            replaced = replaced.replaceAll("&" + i, "");
        }
        return replaced;
    }


    private boolean isBankNote(ItemStack itemStack) {

        Material material = Material.valueOf(config.getConfig().getString("Item.material"));
        short damage = Short.valueOf(config.getConfig().getInt("Item.damage") + "");
        List<String> lore = config.getConfig().getStringList("Item.lore");

        if (!itemStack.hasItemMeta()) return false;

        if (!itemStack.getItemMeta().hasEnchant(Enchantment.DAMAGE_ARTHROPODS)) return false;
        StringUtils str = new StringUtils();

        lore = str.replacePlaceholder(lore, "{amount}", itemStack.getItemMeta().getEnchantLevel(Enchantment.DURABILITY) + "");

        lore = str.translateLore(lore);


        if (itemStack.getItemMeta() == null) return false;
        //   if(!lore.equals(itemStack.getItemMeta().getLore())) return false;
        if (itemStack.getItemMeta().getDisplayName() == null) return false;
        if (!itemStack.getType().equals(material)) return false;
        if (itemStack.getDurability() != damage) return false;
        ItemMeta meta = itemStack.getItemMeta();
        return meta.hasItemFlag(ItemFlag.HIDE_ATTRIBUTES) && meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS) && meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE);
    }


}
