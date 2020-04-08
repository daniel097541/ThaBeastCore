package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.cooldown.BeastCooldown;
import info.beastsoftware.beastcore.cooldown.ICooldown;
import info.beastsoftware.beastcore.event.CooldownEndEvent;
import info.beastsoftware.beastcore.event.cooldown.EnderPearlCooldownEndEvent;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.manager.ICooldownManager;
import info.beastsoftware.beastcore.manager.PearlCooldownManager;
import info.beastsoftware.beastcore.struct.CooldownType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.task.CooldownTask;
import info.beastsoftware.beastcore.task.PearlCooldownTask;
import info.beastsoftware.beastcore.util.StringUtils;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EPCooldownFeatureListener extends AbstractFeatureListener{


    public EPCooldownFeatureListener(IConfig config) {
        super(config, FeatureType.ENDER_PEARL_COOLDOWN);
    }


    private final ICooldownManager pearlCooldownManager = new PearlCooldownManager();

    private final StringUtils stringUtil = new StringUtils();



    public boolean isOnCooldown(BeastPlayer player){
        return this.pearlCooldownManager.isOnCooldown(player);
    }


    public int getCooldown(BeastPlayer player){
        return this.pearlCooldownManager.getLeft(player);
    }

    public void addCooldown(BeastPlayer player, int time){
        this.pearlCooldownManager.add(player, new PearlCooldownTask(time, player));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEnderPearl(PlayerInteractEvent e) {

        if (!isOn()) return;

        if (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))) return;

        BeastPlayer player = this.getPlayer(e.getPlayer());


        if (!player.getItemInHand().getType().equals(Material.ENDER_PEARL)) return;




        //excluded world option
        if (isExcludedWorld(e.getPlayer().getWorld())) return;

        if (e.getPlayer().hasPermission(config.getConfig().getString("EnderPearl-BeastCooldown.bypass-permission")))
            return;

        if (isOnCooldown(player)) {
            String message = config.getConfig().getString("EnderPearl-BeastCooldown.on-beastCooldown-message");
            message = stringUtil.replacePlaceholder(message, "{beastCooldown}", getCooldown(player) + "");
            BeastCore.getInstance().sms(e.getPlayer(), message);
            e.setCancelled(true);
            return;
        }

        this.addCooldown(player, config.getConfig().getInt("EnderPearl-BeastCooldown.beastCooldown"));

    }


    @EventHandler
    public void onEPCooldownEnd(EnderPearlCooldownEndEvent event){

        if(!this.isOn()){
            return;
        }


        BeastPlayer player = event.getPlayer();
        this.pearlCooldownManager.remove(player);

        if(player != null){
            String message = config.getConfig().getString("EnderPearl-BeastCooldown.beastCooldown-ended");
            player.sms(message);
        }
    }

    private boolean isExcludedWorld(World world) {
        return config.getConfig().getStringList("EnderPearl-BeastCooldown.excluded-worlds").contains(world.getName());
    }


}
