package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class VoidFallFeatureListener extends AbstractFeatureListener {


    public VoidFallFeatureListener(IConfig config) {
        super(config, FeatureType.VOID_FALL);
    }

    @EventHandler
    public void onPlayerFallVoid(PlayerMoveEvent e) {
        if (isOn() && e.getTo().getY() < 0 && isAllowedWorld(e.getPlayer().getWorld().getName())) {
            Location location = getLocationFromConfig();
            e.getPlayer().teleport(location);
        }
    }

    private boolean isAllowedWorld(String world) {
        return !config.getConfig().getStringList("Anti-Void-Falling.denied-worlds").contains(world);
    }


    private Location getLocationFromConfig() {

        World world = Bukkit.getWorld(config.getConfig().getString("Anti-Void-Falling.spawn.world"));
        double x = config.getConfig().getDouble("Anti-Void-Falling.spawn.x");
        double y = config.getConfig().getDouble("Anti-Void-Falling.spawn.y");
        double z = config.getConfig().getDouble("Anti-Void-Falling.spawn.z");
        Location location = new Location(world, x, y, z);

        //check if direction is not null and set the direction to the location
        Vector direction = config.getConfig().getVector("Anti-Void-Falling.spawn.direction");
        if (direction != null) {
            location.setDirection(direction);
        }
        return location;
    }


}
