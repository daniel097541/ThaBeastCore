package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.event.itemgiveevent.HarvesterHoeGiveEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class HarvesterHoesFeatureListener extends AbstractFeatureListener {

    public HarvesterHoesFeatureListener(IConfig config) {
        super(config, FeatureType.HARVESTER_HOES);
    }

    private boolean isSugarCane(Block block) {
        if (this.isLegacy()) {
            return block.getType().equals(Material.getMaterial("SUGAR_CANE_BLOCK"));
        } else return block.getType().equals(Material.SUGAR_CANE);
    }

    private boolean isHarvesterHoe(ItemStack tool) {
        if (Objects.nonNull(tool) && !tool.getType().equals(Material.AIR)) {
            ItemStack hoe = createHoe();
            if (hoe.getType().equals(tool.getType()) && tool.hasItemMeta()) {
                if (hoe.getItemMeta().getDisplayName().equals(tool.getItemMeta().getDisplayName())) {
                    return tool.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_MOBS);
                }
            }
        }
        return false;
    }

    private ItemStack createHoe() {
        Material material = Material.getMaterial(this.config.getConfig().getString("item.material"));
        String name = this.config.getConfig().getString("item.name");
        List<String> lore = this.config.getConfig().getStringList("item.lore");
        ItemStack hoe = IInventoryUtil.createItem(1, material, name, lore, Short.valueOf("0"), true);
        hoe.addUnsafeEnchantment(Enchantment.LOOT_BONUS_MOBS, 1);
        return hoe;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onGive(HarvesterHoeGiveEvent event) {
        BeastPlayer player = event.getPlayer();
        player.getInventory().addItem(this.createHoe());
        player.sms(config.getConfig().getString("messages.received-hoe"));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (this.isSugarCane(block)) {
            BeastPlayer p = this.getPlayer(e.getPlayer());
            if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
                ItemStack tool = p.getItemInHand();
                if (isHarvesterHoe(tool)) {
                    e.setCancelled(true);
                    List<Block> blocks = getBlocksOfSC(block);
                    for (ListIterator iterator = blocks.listIterator(blocks.size()); iterator.hasPrevious(); ) {
                        final Block listElement = (Block) iterator.previous();
                        listElement.setType(Material.AIR);
                    }
                    p.getInventory().addItem(new ItemStack(Material.SUGAR_CANE, blocks.size()));
                }
            }
        }
    }

    private List<Block> getBlocksOfSC(Block block) {
        List<Block> blocks = new ArrayList<>();
        blocks.add(block);
        Location checkLoc = block.getLocation().clone().add(0, 1, 0);
        while (isSugarCane(checkLoc.getBlock())) {
            blocks.add(checkLoc.getBlock());
            checkLoc.add(0, 1, 0);
        }
        return blocks;
    }

}
