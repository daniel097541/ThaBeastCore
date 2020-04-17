package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.ThrowableEggsGiveEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getServer;

public class ThrowableCeggsFeatureListener extends AbstractFeatureListener {


    public ThrowableCeggsFeatureListener(IConfig config) {
        super(config, FeatureType.THROWABLE_CEGGS);
    }

    public String Translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private Material getCeggMaterial() {
        Material material = Material.getMaterial("MONSTER_EGG");

        if (material == null) {
            material = Material.getMaterial("CREEPER_SPAWN_EGG");
        }
        return material;
    }

    private int getExplosionSize() {
        Integer size = this.config.getConfig().getInt("explosion-size");
        if (Objects.isNull(size)) {
            size = 6;
        }
        return size;
    }

    @EventHandler
    public void onCegg(PlayerInteractEvent e) {
        try {
            Player p = e.getPlayer();


            Material material = this.getCeggMaterial();

            ItemStack egg = new ItemStack(material, 1, EntityType.CREEPER.getTypeId());
            ItemMeta eggMeta = egg.getItemMeta();
            eggMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("cegg.name")));
            ArrayList<String> isl = new ArrayList<>();
            Iterator<String> iterator = ((List) config.getConfig().getStringList("cegg.lore").stream().map(as -> ChatColor.translateAlternateColorCodes('&', as)).collect(Collectors.toList())).iterator();
            while (iterator.hasNext()) {
                String as = iterator.next();
                isl.add(ChatColor.translateAlternateColorCodes('&', as));
            }
            eggMeta.addEnchant(Enchantment.LUCK, 1, true);
            eggMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
            eggMeta.setLore(isl);
            egg.setItemMeta(eggMeta);
            if (e.getAction() == Action.RIGHT_CLICK_AIR) {
                if (p.getItemInHand().getItemMeta().equals(eggMeta)) {
                    int amount = p.getItemInHand().getAmount();
                    if (amount > 1) {
                        p.getItemInHand().setAmount(amount - 1);
                        final Item grenade = p.getWorld().dropItem(p.getEyeLocation(), egg);
                        grenade.setVelocity(p.getEyeLocation().getDirection());
                        grenade.setCustomName(Translate("&6Flying Cegg!!!!"));
                        getServer().getScheduler().scheduleSyncDelayedTask(BeastCore.getInstance(), () -> {
                            try {
                                Location location = grenade.getLocation();
                                Creeper creeper = (Creeper) location.getWorld().spawnEntity(location, EntityType.CREEPER);
                                Bukkit.getScheduler().runTaskLater(BeastCore.getInstance(), () -> {
                                    location.getWorld().createExplosion(location, this.getExplosionSize());
                                    creeper.remove();
                                }, 20L);
                                grenade.remove();
                            } catch (Exception ev) {
                                ev.printStackTrace();
                            }
                        }, (20 * config.getConfig().getInt("explosion-delay")));
                    } else if (amount == 1) {
                        final Item grenade = p.getWorld().dropItem(p.getEyeLocation(), egg);
                        grenade.setVelocity(p.getEyeLocation().getDirection());
                        grenade.setCustomName(Translate("&6Flying Cegg!!!!"));
                        getServer().getScheduler().scheduleSyncDelayedTask(BeastCore.getInstance(), () -> {
                            try {
                                Location location = grenade.getLocation();
                                Creeper creeper = (Creeper) location.getWorld().spawnEntity(location, EntityType.CREEPER);
                                Bukkit.getScheduler().runTaskLater(BeastCore.getInstance(), () -> {
                                    location.getWorld().createExplosion(location, this.getExplosionSize());
                                    creeper.remove();
                                }, 20L);
                                grenade.remove();
                            } catch (Exception ev) {
                                ev.printStackTrace();
                            }
                        }, (20 * config.getConfig().getInt("explosion-delay")));
                        p.setItemInHand(new ItemStack(Material.AIR));
                    }
                }
            } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand() != null && p.getItemInHand().hasItemMeta() &&
                    p.getItemInHand().getItemMeta().equals(eggMeta)) {
                e.setCancelled(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        Block b = loc.getBlock().getRelative(0, -1, 0);
        b.getType().equals(Material.GLOWSTONE);
    }

    @EventHandler
    public void onpickup(PlayerPickupItemEvent e) {
        try {
            if (e.getItem().getCustomName().equals(ChatColor.translateAlternateColorCodes('&', "&6Flying Cegg!!!!")))
                e.setCancelled(true);
        } catch (NullPointerException nullPointerException) {
        }
    }

    @EventHandler
    public void onHopperPickup(InventoryPickupItemEvent e) {

        Material material = this.getCeggMaterial();

        ItemStack egg = new ItemStack(material, 1, EntityType.CREEPER.getTypeId());
        ItemMeta eggMeta = egg.getItemMeta();
        eggMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("cegg.name")));
        ArrayList<String> isl = new ArrayList<>();
        Iterator<String> iterator = ((List) config.getConfig().getStringList("cegg.lore").stream().map(as -> ChatColor.translateAlternateColorCodes('&', as)).collect(Collectors.toList())).iterator();
        while (iterator.hasNext()) {
            String as = iterator.next();
            isl.add(ChatColor.translateAlternateColorCodes('&', as));
        }
        eggMeta.addEnchant(Enchantment.LUCK, 1, true);
        eggMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        eggMeta.setLore(isl);
        egg.setItemMeta(eggMeta);
        if (e.getInventory().getType().equals(InventoryType.HOPPER) &&
                e.getItem().getItemStack().equals(egg))
            e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGive(ThrowableEggsGiveEvent event) {
        BeastPlayer player = event.getPlayer();
        int amount = event.getAmount();
        ItemStack egg = this.craftItem(amount);
        player.getInventory().addItem(egg);
    }


    private ItemStack craftItem(int amount) {
        ItemStack egg = new ItemStack(getCeggMaterial(), 1, EntityType.CREEPER.getTypeId());
        ItemMeta eggMeta = egg.getItemMeta();
        eggMeta.setDisplayName(
                ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("cegg.name")));
        ArrayList<String> isl = new ArrayList<>();
        Iterator<String> iterator = ((List) config.getConfig().getStringList("cegg.lore").stream().map(as -> ChatColor.translateAlternateColorCodes('&', as)).collect(Collectors.toList())).iterator();
        while (iterator.hasNext()) {
            String as = iterator.next();
            isl.add(ChatColor.translateAlternateColorCodes('&', as));
        }
        eggMeta.setLore(isl);
        eggMeta.addEnchant(Enchantment.LUCK, 1, true);
        eggMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        egg.setAmount(amount);
        egg.setItemMeta(eggMeta);
        return egg;
    }

}
