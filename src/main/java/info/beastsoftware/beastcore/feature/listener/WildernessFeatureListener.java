package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.task.IBroadcastableTask;
import info.beastsoftware.beastcore.beastutils.utils.ILocationUtil;
import info.beastsoftware.beastcore.event.wild.WildCommandEvent;
import info.beastsoftware.beastcore.event.wild.WildCooldownCancelEvent;
import info.beastsoftware.beastcore.event.wild.WildCooldownFinishEvent;
import info.beastsoftware.beastcore.event.wild.WildCooldownUpdateEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.task.WildBroadcastableTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WildernessFeatureListener extends AbstractFeatureListener {


    public WildernessFeatureListener(IConfig config) {
        super(config, FeatureType.WILDERNESS);
    }


    private final List<Player> noSuffocation = new ArrayList<>();
    private final HashMap<Player, IBroadcastableTask> cooldowns = new HashMap<>();


    @EventHandler
    public void onWildEvent(WildCommandEvent event) {
        Player player = event.getPlayer();
        int cooldown = event.getNoMoveCooldown();

        //call task nd send msg
        IBroadcastableTask cooldownTask = new WildBroadcastableTask(cooldown, player);
        cooldownTask.startTask();
        cooldowns.put(player, cooldownTask);
    }


    @EventHandler
    public void onWildCooldownUpdate(WildCooldownUpdateEvent event) {
        Player player = event.getPlayer();
        int cooldown = event.getTimeLeft();

        if (!config.getConfig().getBoolean("Command.Wilderness.countdown")) return;

        String message = config.getConfig().getString("Command.Wilderness.countdown-message");
        message = stringUtil.replacePlaceholder(message, "{seconds}", cooldown + "");
        BeastCore.getInstance().sms(player, message);
    }

    @EventHandler
    public void onWildCooldownCancel(WildCooldownCancelEvent event) {
        Player player = event.getPlayer();
        cooldowns.remove(player);
        String message = config.getConfig().getString("Command.Wilderness.cancelled-due-movement");
        BeastCore.getInstance().sms(player, message);
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!cooldowns.containsKey(player)) return;
        if (ILocationUtil.isSameLocation(event.getFrom(), event.getTo())) return;
        IBroadcastableTask cooldownTask = cooldowns.get(player);
        cooldownTask.cancelTask();
    }


    @EventHandler
    public void onWildCooldownFinish(WildCooldownFinishEvent event) {
        Player player = event.getPlayer();

        Location location = getSuitableLocation(player, 1);


        if (location == null) {
            BeastCore.getInstance().sms(player, config.getConfig().getString("Command.Wilderness.no-suitable-location"));
            return;
        }

        String message = config.getConfig().getString("Command.Wilderness.teleporting");
        location.getChunk().load();
        player.teleport(location);
        BeastCore.getInstance().sms(player, message);
        cooldowns.remove(player);
        preventSuffocation(player);
    }


    private void preventSuffocation(Player player) {
        noSuffocation.add(player);
        Bukkit.getScheduler().runTaskLater(BeastCore.getInstance(), () -> noSuffocation.remove(player), 300L);
    }


    private boolean isSuitable(Location location) {

        if (isFactionsHooked() && config.getConfig().getBoolean("Command.Wilderness.deny-teleport-to-factions") && !this.getAtLocation(location).isSystemFaction())
            return false;


        for (String mat : config.getConfig().getStringList("Command.Wilderness.blacklisted-blocks")) {
            for (int y = 0; y < 255; y++) {
                if (location.getWorld().getBlockAt(location.getBlockX(), y, location.getBlockZ()).getType().toString().equals(mat))
                    return false;
            }
        }

        for (String bio : config.getConfig().getStringList("Command.Wilderness.blacklisted-biomes")) {
            if (bio.equalsIgnoreCase(location.getWorld().getBiome(location.getBlockX(), location.getBlockZ()).toString())) {
                return false;
            }
        }

        return true;
    }

    private Location getSuitableLocation(Player player, int count) {

        int maxCount = config.getConfig().getInt("Command.Wilderness.max-tries");
        World world = player.getWorld();
        String path = "Command.Wilderness.Worlds." + world.getName();
        int min = config.getConfig().getInt(path + ".min-radius");
        int max = config.getConfig().getInt(path + ".max-radius");


        Location location = ILocationUtil.getRandomLocation(world, min, max, min, max);

        if (isSuitable(location)) {
            return location;
        }

        if (count >= maxCount)
            return null;

        return getSuitableLocation(player, count + 1);
    }


    @EventHandler
    public void onSuffocation(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();

        if (!noSuffocation.contains(player)) return;

        if (!event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) return;

        event.setCancelled(true);

        Location location = player.getLocation();

        location.setY(location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ()));

        player.teleport(location);
    }
}
