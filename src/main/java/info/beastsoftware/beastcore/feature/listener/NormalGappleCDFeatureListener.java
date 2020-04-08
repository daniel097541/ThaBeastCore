package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.cooldown.BeastCooldown;
import info.beastsoftware.beastcore.cooldown.ICooldown;
import info.beastsoftware.beastcore.event.CooldownEndEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.CooldownType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class NormalGappleCDFeatureListener extends AbstractFeatureListener {

    public NormalGappleCDFeatureListener(IConfig config) {
        super(config, FeatureType.NORMAL_GAPPLE_CD);
    }


    private final ICooldown beastCooldown = new BeastCooldown(CooldownType.GAPPLE_COOLDOWN);


    public boolean isOnCooldown(Player player){
        return beastCooldown.isOnCooldown(player.getName());
    }


    public int getCooldown(Player player){
        return beastCooldown.getCooldown(player.getName());
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerConsume(PlayerItemConsumeEvent e) {

        if (isOn() && !e.isCancelled()) {
            Player player = e.getPlayer();
            String cdFormatted = "";

            //excluded worlds
            if (isExcludedWorld(player.getWorld())) return;

            if (player.hasPermission(config.getConfig().getString("Normal-Gapple-BeastCooldown.bypass-permission")))
                return;

            if (e.getItem().getType().equals(Material.GOLDEN_APPLE) && e.getItem().getDurability() == 0) {
                if (!beastCooldown.isOnCooldown(player.getName())) {
                    beastCooldown.addCooldown(player.getName(), config.getConfig().getInt("Normal-Gapple-BeastCooldown.beastCooldown"));

                    String consumed =  config.getConfig().getString("Normal-Gapple-BeastCooldown.gapple-consumed");

                    if(consumed == null || consumed.length() <= 0 || consumed.equalsIgnoreCase("''") || consumed.equalsIgnoreCase("[]")){
                        return;
                    }

                    BeastCore.getInstance().sms(player,consumed);
                } else {
                    cdFormatted = String.valueOf(this.getCooldown(player));
                    String message = stringUtil.replacePlaceholder(config.getConfig().getString("Normal-Gapple-BeastCooldown.beastCooldown-message"), "{beastCooldown}", cdFormatted);
                    BeastCore.getInstance().sms(player, message);
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onCooldownEnded(CooldownEndEvent e) {
        if (isOn() && e.getCdType().equals(CooldownType.GAPPLE_COOLDOWN))
            BeastCore.getInstance().sms((Player) Bukkit.getServer().getOfflinePlayer(e.getPlayerName()), config.getConfig().getString("Normal-Gapple-BeastCooldown.beastCooldown-ended"));
    }

    private boolean isExcludedWorld(World world) {
        return config.getConfig().getStringList("Normal-Gapple-BeastCooldown.excluded-worlds").contains(world.getName());
    }
}
