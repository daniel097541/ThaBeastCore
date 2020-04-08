package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.cooldown.BeastCooldown;
import info.beastsoftware.beastcore.cooldown.ICooldown;
import info.beastsoftware.beastcore.event.CooldownEndEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.CooldownType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class GappleCDFeatureListener extends AbstractFeatureListener {

    private final ICooldown beastCooldown = new BeastCooldown(CooldownType.GAPPLE_COOLDOWN);


    public GappleCDFeatureListener(IConfig config) {
        super(config, FeatureType.GAPPLE_CD);
    }


    public boolean isPlayerOnCooldown(BeastPlayer player){
        return beastCooldown.isOnCooldown(player.getName());
    }

    public int getCooldown(BeastPlayer player){
        return beastCooldown.getCooldown(player.getName());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerConsume(PlayerItemConsumeEvent e) {

        if (isOn() && !e.isCancelled()) {
            BeastPlayer player = this.getPlayer(e.getPlayer());

            //excluded world
            if (isExcludedWorld(player.getBukkitLocation().getWorld())) return;

            if (player.hasPermission(config.getConfig().getString("Gapple-BeastCooldown.bypass-permission"))) return;

            Material mat113 = null;

            Material itemMet = e.getItem().getType();

            try {
                mat113 = Material.valueOf("ENCHANTED_GOLDEN_APPLE");
            } catch (Exception ignored) {

            }


            if ((mat113 != null && itemMet.equals(mat113)) || (itemMet.equals(Material.GOLDEN_APPLE) && e.getItem().getDurability() == 1)) {
                if (!beastCooldown.isOnCooldown(player.getName())) {
                    beastCooldown.addCooldown(player.getName(), config.getConfig().getInt("Gapple-BeastCooldown.beastCooldown"));
                    String consumed = config.getConfig().getString("Gapple-BeastCooldown.gapple-consumed");
                    if(consumed == null || consumed.length() <= 0 || consumed.equalsIgnoreCase("''") || consumed.equalsIgnoreCase("[]")){
                        return;
                    }
                    player.sms(consumed);
                } else {
                    String message = stringUtil.replacePlaceholder(config.getConfig().getString("Gapple-BeastCooldown.beastCooldown-message"), "{beastCooldown}", String.valueOf(this.getCooldown(player)));
                    player.sms(message);
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onCooldownEnded(CooldownEndEvent e) {
        if (isOn() && e.getCdType().equals(CooldownType.GAPPLE_COOLDOWN))
            BeastCore.getInstance().sms((Player) Bukkit.getServer().getOfflinePlayer(e.getPlayerName()), config.getConfig().getString("Gapple-BeastCooldown.beastCooldown-ended"));
    }


    private boolean isExcludedWorld(World world) {
        return config.getConfig().getStringList("Gapple-BeastCooldown.excluded-worlds").contains(world.getName());
    }
}
