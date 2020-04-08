package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.event.grace.*;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.task.GraceTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class GraceFeatureListener extends AbstractFeatureListener {

    private final GraceTask graceTask;

    public GraceFeatureListener(IConfig config) {
        super(config, FeatureType.GRACE);
        this.graceTask = new GraceTask(config, BeastCore.getInstance());
    }

    public boolean isOnGrace(){
        return this.graceTask.isRunning();
    }


    public int getTimeLeft(){
        return this.graceTask.getTimeLeft();
    }


    @EventHandler
    public void onTnT(EntityExplodeEvent e) {
        if (!isOn()) return;
        if (!graceTask.isRunning()) return;
        if (e.isCancelled()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onGraceResume(GraceResumeEvent event) {
        CommandSender sender = event.getPlayer();


        //////// ALREADY STARTED
        if (graceTask.isRunning()) {
            BeastCore.getInstance().sms(sender, config.getConfig().getString("Grace-Period.already-enabled"));
            return;
        }


        ////// RESUME/START THE TASK
        graceTask.resume();
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Grace-Period.grace-enabled-message")));
    }

    @EventHandler
    public void onGracePause(GracePauseEvent event) {
        CommandSender sender = event.getPlayer();

        //////// ALREADY CANCELLED
        if (!graceTask.isRunning()) {
            BeastCore.getInstance().sms(sender, config.getConfig().getString("Grace-Period.not-enabled-message"));
            return;
        }


        ////// PAUSE THE TASK
        graceTask.pause();
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Grace-Period.end-message")));
    }

    @EventHandler
    public void onGraceDisplay(GraceDisplayCommandEvent event) {
        CommandSender sender = event.getPlayer();

        //////// CANCELLED
        if (!graceTask.isRunning()) {
            BeastCore.getInstance().sms(sender, config.getConfig().getString("Grace-Period.not-enabled-message"));
            return;
        }

        ////// DISPLAY GRACE
        String message = config.getConfig().getString("Grace-Period.message");
        message = stringUtil.replacePlaceholder(message, "{time_left}", stringUtil.formatCooldown(getTimeLeft()));
        BeastCore.getInstance().sms(sender, message);
    }


    private void save() {
        this.graceTask.saveTimeLeft();
        this.graceTask.saveTaskStatus();
    }

    @EventHandler
    public void onSaveTaskEvent(GraceTaskSaveEvent event) {
        this.save();
    }

    @EventHandler
    public void onGraceRestart(GraceTaskRestartEvent event){
        this.graceTask.resetGraceTime();
        this.graceTask.resume();
        this.graceTask.saveTimeLeft();
        this.graceTask.saveTaskStatus();
    }

    @EventHandler
    public void onEndTaskEvent(GraceTaskEndEvent event) {
        this.graceTask.pause();
        this.graceTask.saveTaskStatus();
        this.graceTask.saveTimeLeft();
        Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Grace-Period.end-message")));
    }


    private boolean isTnTPlaceDisabled(){
        return this.config.getConfig().getBoolean("Grace-Period.deny-tnt-place");
    }

    private String getTnTPlaceDenyMessage(){
        return this.config.getConfig().getString("Grace-Period.deny-tnt-place-message");
    }

    @EventHandler
    public void onTnTPlace(BlockPlaceEvent event){

        if(!this.isOn() || !this.isOnGrace()) {
            return;
        }

        Block block = event.getBlock();
        Player player = event.getPlayer();

        //is tnt
        if(block.getType().equals(Material.TNT) && this.isTnTPlaceDisabled()){
            event.setCancelled(true);
            String message = getTnTPlaceDenyMessage();
            StrUtils.sms(player, message);
        }
    }

}
