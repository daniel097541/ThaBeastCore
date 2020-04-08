package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class BottleRecycleFeatureListener extends AbstractFeatureListener {

    public BottleRecycleFeatureListener(IConfig config) {
        super(config, FeatureType.BOTTLE_RECYCLE);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemConsume(PlayerItemConsumeEvent e) {
        if (isOn() && e.getPlayer().hasPermission(config.getConfig().getString("Bottle-Recycling.permission")) && !e.isCancelled()) {
            Player player = e.getPlayer();
            if (e.getItem().getType().getId() == 373) {
                Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(BeastCore.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        player.getInventory().removeItem(new ItemStack(Material.GLASS_BOTTLE));
                        if (config.getConfig().getBoolean("Bottle-Recycling.send-message"))
                            BeastCore.getInstance().sms(player, config.getConfig().getString("Bottle-Recycling.message"));
                    }
                }, 1L);
            }
        }
    }
}
