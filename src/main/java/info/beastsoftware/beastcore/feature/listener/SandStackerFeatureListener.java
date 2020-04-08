package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ItemSpawnEvent;

import java.util.ArrayList;
import java.util.List;

public class SandStackerFeatureListener extends AbstractFeatureListener {



    public SandStackerFeatureListener(IConfig config) {
        super(config, FeatureType.SAND_STACKER);
        materials = config.getConfig().getStringList("Sand-Stacking-Fix.blocks");
        if (materials.contains("ENCHANT_TABLE"))
            materials.add("ENCHANTMENT_TABLE");

    }


    private List<String> materials = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSandStack(ItemSpawnEvent e) {
        try {
            if (isOn()) {
                Block bellow = e.getEntity().getLocation().getBlock().getRelative(BlockFace.SELF);
                if (materials.contains(bellow.getType().toString())) {
                    if ((e.getEntity().getItemStack().getType() == Material.SAND) || (e.getEntity().getItemStack().getType() == Material.GRAVEL)) {
                        Location l = e.getEntity().getLocation();
                        e.getEntity().getWorld().getBlockAt(l.getBlockX(), l.getBlockY() + 1, l.getBlockZ()).setType(e.getEntity().getItemStack().getType());
                        e.setCancelled(true);
                    }
                }
            }
        }
        catch (Exception ex){
            Bukkit.getConsoleSender().sendMessage("Error in SandStackerFeature >> ");
            ex.printStackTrace();
        }
    }

}
