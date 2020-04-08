package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AntiItemPlaceFeatureListener extends AbstractFeatureListener {

    private Material material;

    public AntiItemPlaceFeatureListener(IConfig config) {
        super(config, FeatureType.ANTI_ITEM_PLACE);

        ///////////// 1.13
        try {
            material = Material.valueOf("BOAT");
        } catch (IllegalArgumentException ignored) {
            material = Material.LEGACY_BOAT;
        }
        ///////////// 1.13
    }


    @EventHandler
    public void onBoatPlace(PlayerInteractEvent e) {

        if (!isOn()) return;

        if (!e.getPlayer().hasPermission(config.getConfig().getString("Anti-Boat-Place.bypass-permission"))) {
            if (getPlayer(e.getPlayer()).isInOthersLand() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getPlayer().getItemInHand().getType().equals(material)) {
                e.setCancelled(true);
                BeastCore.getInstance().sms(e.getPlayer(), config.getConfig().getString("Anti-Boat-Place.message"));
            }
        }
    }
}
