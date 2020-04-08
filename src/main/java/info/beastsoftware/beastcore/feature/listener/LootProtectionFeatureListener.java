package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

public class LootProtectionFeatureListener extends AbstractFeatureListener {

    private final String METADATA = "LOOT-OWNER";

    public LootProtectionFeatureListener(IConfig config) {
        super(config, FeatureType.LOOT_PROTECTION);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKill(EntityDeathEvent event) {


        //off
        if (!isOn()) {
            return;
        }


        //the death is not a player
        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }


        Player player = (Player) entity;
        Entity killer = player.getKiller();

        //the killer is not a player
        if (!(killer instanceof Player)) {
            return;
        }


        //clear drops
        List<ItemStack> drops = new ArrayList<>(event.getDrops());
        event.getDrops().clear();


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, getLootTime());


        for (ItemStack itemStack : drops) {
            Item item = event.getEntity()
                    .getWorld()
                    .dropItemNaturally(event.getEntity().getLocation(), itemStack);
            item.setMetadata(METADATA, new FixedMetadataValue(this.getBeastCore(), killer.getUniqueId() + "/" + calendar.getTime().getTime()));
        }

        sendMessageProtected((Player) killer, getLootTime());
        Bukkit.getScheduler().runTaskLaterAsynchronously(getBeastCore(), () -> sendMessageNotProtected((Player) killer), getLootTime() * 20L);

    }

    private int getLootTime() {
        return config.getConfig().getInt("protection-time");
    }

    private String permission(){
        return this.config.getConfig().getString("bypass-permission");
    }

    @EventHandler
    public void onItemPickUp(PlayerPickupItemEvent event) {


        //off
        if (!isOn()) {
            return;
        }


        Player player = event.getPlayer();
        Item item = event.getItem();


        //has permission to bypass
        if(player.hasPermission(this.permission())){
            return;
        }


        //has metadata
        if (item.hasMetadata(METADATA)) {

            String metadata = item.getMetadata(METADATA).get(0).asString();
            String[] splited = metadata.split("/");

            UUID owner = UUID.fromString(splited[0]);
            long time = Long.parseLong(splited[1]);

            //is not the owner
            if (!owner.equals(player.getUniqueId())) {
                long now = new Date().getTime();

                //the time is not over yet, cancel event
                if (now < time) {
                    event.setCancelled(true);
                }
            }

        }
    }


    private void sendMessageProtected(Player player, int time) {
        String message = this.config.getConfig().getString("message-loot-protected");
        message = StrUtils.replacePlaceholder(message, "{time}", time + "");
        StrUtils.sms(player, message);
    }


    private void sendMessageNotProtected(Player player) {
        String message = this.config.getConfig().getString("message-loot-protection-ended");
        StrUtils.sms(player, message);
    }

}
