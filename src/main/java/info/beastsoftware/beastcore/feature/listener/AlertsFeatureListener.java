package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.Alerts;
import info.beastsoftware.beastcore.struct.BeastRoles;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.struct.Role;
import info.beastsoftware.hookcore.entity.BeastFaction;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class AlertsFeatureListener extends AbstractFeatureListener {


    public AlertsFeatureListener(IConfig config, IDataConfig dataConfig, FeatureType featureType) {
        super(config, dataConfig, featureType);
    }


    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        if (e.isCancelled()) return;

        Entity entity = e.getEntity();
        BeastFaction faction = this.getAtLocation(e.getLocation());
        if (entity instanceof TNTPrimed) {
            sendAlert(Alerts.TNT_EXPLODE, e.getLocation(), faction, null);
        }
        if (entity instanceof Creeper) {
            sendAlert(Alerts.CREEPER_EXPLODE, e.getLocation(), faction, null);
        }
    }

    @EventHandler
    public void onCreeperPlace(PlayerInteractEvent e) {
        if (e.isCancelled()) return;
        if (!isOn()) return;
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        BeastFaction faction = this.getAtLocation(e.getClickedBlock().getLocation());
        BeastPlayer player = this.getPlayer(e.getPlayer());


        ItemStack item = e.getItem();
        if (item == null) return;
        ///////////// 1.13
        Material material;
        try {
            material = Material.valueOf("MONSTER_EGG");
        } catch (IllegalArgumentException ignored) {
            material = Material.CREEPER_SPAWN_EGG;
        }
        ///////////// 1.13
        if (!item.getType().equals(material)) return;
        if (item.getDurability() != new Short("50")) return;
        sendAlert(Alerts.CREEPER_PLACE, e.getClickedBlock().getLocation(), faction, player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) return;
        if (!isOn()) return;
        Block block = e.getBlockPlaced();
        BeastFaction faction = this.getAtLocation(block.getLocation());
        BeastPlayer player = this.getPlayer(e.getPlayer());
        if (!block.getType().equals(Material.TNT)) return;
        sendAlert(Alerts.TNT_PLACE, block.getLocation(), faction, player);
    }

    @EventHandler
    public void onSpawnersBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;

        if (!isOn()) return;

        ///////////// 1.13
        Material material;
        try {
            material = Material.valueOf("MOB_SPAWNER");
        } catch (IllegalArgumentException ignored) {
            material = Material.SPAWNER;
        }
        ///////////// 1.13

        if (!e.getBlock().getType().equals(material)) return;
        if (e.isCancelled()) return;
        BeastFaction faction = this.getAtLocation(e.getBlock().getLocation());
        BeastPlayer player = this.getPlayer(e.getPlayer());
        sendAlert(Alerts.SPAWNER_BREAK, e.getBlock().getLocation(), faction, player);
    }


    @EventHandler
    public void onSpawnersPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) return;
        if (!isOn()) return;
        ///////////// 1.13
        Material material;
        try {
            material = Material.valueOf("MOB_SPAWNER");
        } catch (IllegalArgumentException ignored) {
            material = Material.SPAWNER;
        }
        ///////////// 1.13
        if (!e.getBlock().getType().equals(material)) return;
        if (e.isCancelled()) return;
        BeastFaction faction = this.getAtLocation(e.getBlock().getLocation());
        BeastPlayer player = this.getPlayer(e.getPlayer());

        sendAlert(Alerts.SPAWNER_PLACE, e.getBlock().getLocation(), faction, player);
    }


    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {

        if (!isOn()) return;

        BeastPlayer player = this.getPlayer(e.getPlayer());

        if (!player.hasFaction()) {
            return;
        }

        BeastFaction faction = player.getMyFaction();

        Bukkit.getScheduler().runTaskLater(BeastCore.getInstance(), () -> sendAlert(Alerts.PLAYER_LOGING, null, faction, player), 20L);
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent e) {
        if (!isOn()) return;

        BeastPlayer player = this.getPlayer(e.getPlayer());

        if (!player.hasFaction()) {
            return;
        }

        BeastFaction faction = player.getMyFaction();

        sendAlert(Alerts.PLAYER_LOGOUT, null, faction, player);
    }

    @EventHandler
    public void onPlayerDead(PlayerDeathEvent e) {
        if (!isOn()) return;

        BeastPlayer beastPlayer = this.getPlayer(e.getEntity());

        if (!beastPlayer.hasFaction()) return;

        BeastFaction faction = beastPlayer.getMyFaction();

        if (!BeastCore.getInstance().getApi().isPlayerInCombat(this.getPlayer(e.getEntity())))
            return;


        sendAlert(Alerts.DEATH_IN_COMBAT, null, faction, beastPlayer);
    }


    private void sendAlert(Alerts alert, Location location, BeastFaction faction, BeastPlayer player) {

        if (!isOn()) return;

        String id = faction.getId();
        String PATH = "Faction-Alerts.messages.";
        String message = config.getConfig().getString(PATH + alert.toString());

        if (location != null) {
            String loc = location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
            message = StrUtils.replacePlaceholder(message, "{location}", loc);
            if (this.getAtLocation(location).isSystemFaction()) {
                return;
            }
        }

        if (player != null) {
            message = StrUtils.replacePlaceholder(message, "{player}", player.getName());
            if (!player.hasFaction()) {
                return;
            }
        }


        if (!dataConfig.exists(id))
            return;

        if (dataConfig.getConfigByName(id).getBoolean(Role.ADMIN.toString() + "." + alert.toString())) {
            faction.broadcastMessageToAllPlayersWithRole(message, BeastRoles.ADMIN);
            faction.broadcastMessageToAllPlayersWithRole(message, BeastRoles.LEADER);
        }


        if (dataConfig.getConfigByName(id).getBoolean(Role.COLEADER.toString() + "." + alert.toString())) {
            faction.broadcastMessageToAllPlayersWithRole(message, BeastRoles.COLEADER);
        }

        if (dataConfig.getConfigByName(id).getBoolean(Role.MOD.toString() + "." + alert.toString())) {
            faction.broadcastMessageToAllPlayersWithRole(message, BeastRoles.MOD);
        }
        if (dataConfig.getConfigByName(id).getBoolean(Role.MEMBER.toString() + "." + alert.toString())) {
            faction.broadcastMessageToAllPlayersWithRole(message, BeastRoles.MEMBER);
        }
    }

}
