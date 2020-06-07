package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.beastutils.utils.ILocationUtil;
import info.beastsoftware.beastcore.cooldown.BeastCooldown;
import info.beastsoftware.beastcore.event.itemgiveevent.ChunkBustersGiveEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.manager.ChunkBusterTask;
import info.beastsoftware.beastcore.struct.CooldownType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.StringUtils;
import info.beastsoftware.hookcore.entity.*;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class ChunkBustersFeatureListener extends AbstractFeatureListener {

    private final StringUtils stringUtil;
    private final BeastCooldown beastCooldown;

    public ChunkBustersFeatureListener(IConfig config) {
        super(config, FeatureType.CHUNK_BUSTERS);
        this.stringUtil = new StringUtils();
        beastCooldown = new BeastCooldown(CooldownType.CHUNKBUSTER);
    }


    @EventHandler
    public void onItemGive(ChunkBustersGiveEvent event) {
        BeastPlayer player = event.getPlayer();
        int amount = event.getAmount();
        int radius = event.getRadius();

        String basePath = "Chunk-Busters.ChunkBuster.";

        //info for the chunkbuster
        Material buster = Material.getMaterial(config.getConfig().getString(basePath + "item-material"));
        String name = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString(basePath + "item-name"));
        List<String> lore = stringUtil.translateLore(config.getConfig().getStringList(basePath + "lore"));
        Short damage = Short.valueOf(config.getConfig().getString(basePath + "item-damage"));

        lore = stringUtil.replacePlaceholder(lore, "{radius}", radius + "");
        lore = stringUtil.replacePlaceholder(lore, "{radius}", radius + "");

        ItemStack chunkBuster = IInventoryUtil.createItem(amount, buster, name, lore, damage, true);
        chunkBuster.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, radius);

        //add item to the inventory
        player.getInventory().addItem(chunkBuster);
    }


    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        BeastPlayer player = this.getPlayer(e.getPlayer());

        BeastLocation location = new BeastLocationImpl(e.getBlockPlaced().getLocation());

        if (e.isCancelled()) return;

        if (!isChunkBuster(e.getItemInHand())) return;

        if (this.isFactionsHooked() && player.isLocationInOthersLand(location)) {
            e.setCancelled(true);
            return;
        }

        if (!isOn()) {
            BeastCore.getInstance().sms(e.getPlayer(), config.getConfig().getString("Chunk-Busters.disabled-message"));
            e.setCancelled(true);
            return;
        }

        if (config.getConfig().getBoolean("Chunk-Busters.Settings.deny-placing-outside-own-faction-land")) {

            if (this.isFactionsHooked() && player.isLocationInOthersLand(location)) {
                player.sms(config.getConfig().getString("Chunk-Busters.Settings.deny-placing-outside-own-faction-land-message"));
                e.setCancelled(true);
                return;
            }

        }


        if (beastCooldown.isOnCooldown(e.getPlayer().getName())) {
            String message = config.getConfig().getString("Chunk-Busters.Settings.place-beastCooldown-message");
            message = stringUtil.replacePlaceholder(message, "{beastCooldown}", stringUtil.formatCooldown(beastCooldown.getCooldown(e.getPlayer().getName())));
            BeastCore.getInstance().sms(e.getPlayer(), message);
            e.setCancelled(true);
            return;
        }

        beastCooldown.addCooldown(e.getPlayer().getName(), config.getConfig().getInt("Chunk-Busters.Settings.place-beastCooldown"));
        int radius = getRadius(e.getItemInHand());
        removeChunkBuster(e.getItemInHand());

        startChunkBusterAction(player, new BeastLocationImpl( e.getBlockPlaced().getLocation()), radius);
    }


    private void startChunkBusterAction(BeastPlayer player, BeastLocation location, int radius) {

        int doIt = config.getConfig().getInt("Chunk-Busters.Settings.explode-beastCooldown");

        List<Chunk> chunks = ILocationUtil.getChunkSquare(radius, location.getBukkitLocation());
        boolean denyBreakOthersLand = !config.getConfig().getBoolean("Chunk-Busters.Settings.break-other-factions-chunks-in-radius");

        BeastFaction faction = player.getMyFaction();

        if (denyBreakOthersLand) {
            chunks = chunks
                    .stream()
                    .filter(c -> new BeastChunkImpl(c).getFaction().getId().equals(faction.getId()))
                    .collect(Collectors.toList());
        }


        List<String> ignored = config.getConfig().getStringList("Chunk-Busters.Settings.ignored-blocks");
        String countDownMessage = config.getConfig().getString("Chunk-Busters.Settings.explode-beastCooldown-message");
        boolean withCountDown = config.getConfig().getBoolean("Chunk-Busters.Settings.broadcast-beastCooldown");
        String chunksExploded = config.getConfig().getString("Chunk-Busters.Settings.explode-message");


        new ChunkBusterTask(chunks, 10, ignored, player, countDownMessage, doIt, withCountDown, chunksExploded);


    }


    private void removeChunkBuster(ItemStack itemStack) {
        if (itemStack.getAmount() == 1)
            itemStack.setType(Material.AIR);
        else itemStack.setAmount(itemStack.getAmount() - 1);
    }


    private int getRadius(ItemStack chunkBuster) {
        return chunkBuster.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
    }

    private boolean isChunkBuster(ItemStack itemStack) {
        Material material = Material.getMaterial(config.getConfig().getString("Chunk-Busters.ChunkBuster.item-material"));
        short damage = Short.parseShort(config.getConfig().getString("Chunk-Busters.ChunkBuster.item-damage"));
        String name = ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Chunk-Busters.ChunkBuster.item-name"));
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null || itemStack.getItemMeta().getDisplayName() == null) return false;

        return meta.getDisplayName().equals(name) && itemStack.getDurability() == damage && itemStack.getType().equals(material);
    }

}
