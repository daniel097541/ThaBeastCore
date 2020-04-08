package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;

public class FlyingPearlFeatureListener extends AbstractFeatureListener {


    public FlyingPearlFeatureListener(IConfig config) {
        super(config, FeatureType.FLYING_PEARL);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleportByPearl(PlayerTeleportEvent event) {

        if (!isOn() || event.isCancelled()) return;

        if (!event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) return;

        Player player = event.getPlayer();

        if (player.isFlying()) {
            event.setCancelled(true);
            BeastCore.getInstance().sms(player, config.getConfig().getString("No-Flying-Pearl.message"));
        }
    }

}
