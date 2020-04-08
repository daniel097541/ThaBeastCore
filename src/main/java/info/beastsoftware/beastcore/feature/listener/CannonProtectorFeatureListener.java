package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastFaction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class CannonProtectorFeatureListener extends AbstractFeatureListener {

    private final String METADATAPATH = "cannon-protector-faction";

    public CannonProtectorFeatureListener(IConfig config) {
        super(config, FeatureType.CANNON_PROTECTOR);
    }


    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if (e.isCancelled() || !isOn() || !(e.getEntity() instanceof TNTPrimed)) return;

        BeastFaction fac = this.getAtLocation(e.getLocation());

        if (fac.isSystemFaction()) {
            return;
        }

        if (!e.getEntity().hasMetadata(METADATAPATH)) return;
        int entityId = e.getEntity().getMetadata(METADATAPATH).get(0).asInt();
        if (entityId == Integer.parseInt(fac.getId())) {
            e.blockList().clear();
        }

    }


    @EventHandler
    public void onTnTDispense(BlockDispenseEvent e) {
        if (e.isCancelled() || !isOn() || !e.getBlock().getType().equals(Material.DISPENSER) || !e.getItem().getType().equals(Material.TNT))
            return;

        Location location;
        BlockFace targetFace = ((org.bukkit.material.Dispenser) e.getBlock().getState().getData()).getFacing();
        location = e.getBlock().getRelative(targetFace).getLocation();

        int id = Integer.parseInt(this.getAtLocation(location).getId());
        if (id <= 0) return;
        e.setCancelled(true);

        takeTnT((Dispenser) e.getBlock().getState());

        location.setZ(location.getZ() + 0.5);
        location.setX(location.getX() + 0.5);

        Entity tnt = e.getBlock().getWorld().spawnEntity(location, EntityType.PRIMED_TNT);
        FixedMetadataValue meta = new FixedMetadataValue(BeastCore.getInstance(), id);
        tnt.setMetadata(METADATAPATH, meta);

    }

    private void takeTnT(Dispenser dispenser) {
        int i = dispenser.getInventory().first(Material.TNT);
        ItemStack item = dispenser.getInventory().getItem(i);
        if (item.getAmount() == 1) {
            dispenser.getInventory().getItem(i).setAmount(-1);
            return;
        }
        dispenser.getInventory().getItem(i).setAmount(item.getAmount() - 1);
    }

}
