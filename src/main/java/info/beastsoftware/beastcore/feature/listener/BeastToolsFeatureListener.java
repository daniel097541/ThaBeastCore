package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.cooldown.BeastCooldown;
import info.beastsoftware.beastcore.event.LightningThrowEvent;
import info.beastsoftware.beastcore.event.itemgiveevent.BeastToolGiveEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.CooldownType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.StringUtils;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BeastToolsFeatureListener extends AbstractFeatureListener {

    private final StringUtils stringUtil;
    private final BeastCooldown beastCooldown;

    public BeastToolsFeatureListener(IConfig config) {
        super(config, FeatureType.BEAST_TOOLS);
        this.stringUtil = new StringUtils();
        this.beastCooldown = new BeastCooldown(CooldownType.WAND);
    }


    @EventHandler
    public void onToolGiveEvent(BeastToolGiveEvent event) {
        BeastPlayer player = event.getPlayer();
        int amount = event.getAmount();
        giveTool(player, amount);
    }

    private void giveTool(BeastPlayer player, int amount) {

        //if player has empty slot
        if (hasEmptySlot(player.getBukkitPlayer())) {
            int firstEmpty = player.getInventory().firstEmpty();
            player.getInventory().setItem(firstEmpty, createTool(amount));
            return;
        }

        //if player has not an empty slot
        player.getBukkitLocation().getWorld().dropItem(player.getLocation().getBukkitLocation(), createTool(amount));

    }


    private boolean hasEmptySlot(Player player) {
        for (ItemStack item : player.getInventory()) {
            if (item == null || item.getType().equals(Material.AIR))
                return true;
        }
        return false;
    }

    private ItemStack createTool(int amount) {

        Material material = Material.getMaterial(config.getConfig().getString("Lightning-Tools.Tool.Material"));
        List<String> lore = config.getConfig().getStringList("Lightning-Tools.Tool.Lore");
        List<String> transLore = new ArrayList<>();

        for (String s : lore) {
            transLore.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        String name = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Lightning-Tools.Tool.Name"));

        ItemStack tool = new ItemStack(material, amount);
        ItemMeta meta = tool.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(transLore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        tool.setItemMeta(meta);

        return tool;
    }


    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {

        if (event.isCancelled()) return;

        if (!isOn()) return;

        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        ItemStack tool = player.getItemInHand();

        if (!isAllowedTarget(entity) || !isLighningTool(tool)) {
            return;
        }

        if (isOnCooldown(player)) {
            sendMessageCooldown(player);
            return;
        }

        Bukkit.getPluginManager().callEvent(new LightningThrowEvent(player, entity));
    }


    private void sendMessageCooldown(Player player) {
        String message = config.getConfig().getString("Lightning-Tools.BeastCooldown.on-beastCooldown-message");
        if (message.contains("{beastCooldown}"))
            message = message.replace("{beastCooldown}", stringUtil.formatCooldown(beastCooldown.getCooldown(player.getName())));
        BeastCore.getInstance().sms(player, message);
    }


    private boolean isAllowedTarget(Entity entity) {
        return config
                .getConfig()
                .getStringList("Lightning-Tools.Tool.Allowed-Entities-List")
                .contains(entity.getType().toString());
    }


    private boolean isOnCooldown(Player player) {
        return beastCooldown
                .isOnCooldown(player.getName());
    }


    @EventHandler
    public void onToolUse(EntityDamageByEntityEvent e) {

        if (e.isCancelled()) return;

        if (!(e.getDamager() instanceof Player)) return;

        Player damager = (Player) e.getDamager();


        //disabled
        if (!isOn()) {
            BeastCore.getInstance().sms(damager, config.getConfig().getString("Lightning-Tools.disabled-message"));
            return;
        }



        //check if lightning wand
        if (!isLighningTool(damager.getItemInHand()))
            return;


        //not allowed target = do nothing
        if (!isAllowedTarget(e.getEntity()))
            return;

        //check beastCooldown
        if (isOnCooldown(damager)) {
            sendMessageCooldown(damager);
            return;
        }

        Bukkit.getPluginManager().callEvent(new LightningThrowEvent(damager, e.getEntity()));
    }



    @EventHandler
    public void onLightning(LightningThrowEvent event){

        Player damager = event.getThrower();
        Entity target = event.getTarget();

        //strike
        beastCooldown.addCooldown(damager.getName(), config.getConfig().getInt("Lightning-Tools.BeastCooldown.beastCooldown-time"));
        target.getWorld().strikeLightning(target.getLocation());
    }




    private boolean isLighningTool(ItemStack tool) {
        return tool.getType().equals(Material.getMaterial(config.getConfig().getString("Lightning-Tools.Tool.Material")))
                && tool.getItemMeta().hasItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                && tool.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Lightning-Tools.Tool.Name")));
    }

}
