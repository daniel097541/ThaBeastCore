package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Diode;

import java.util.HashMap;
import java.util.List;

public class TickCounterFeatureListener extends AbstractFeatureListener {


    public TickCounterFeatureListener(IConfig config) {
        super(config, FeatureType.TICK_COUNTER);
    }

    private final HashMap<Player, Integer> ticks = new HashMap<>();


    private boolean isItem(ItemStack itemStack) {
        String mat = config.getConfig().getString("Item.material");
        String name = config.getConfig().getString("Item.name");
        List<String> lore = config.getConfig().getStringList("Item.lore");

        if (itemStack == null) return false;

        if (itemStack.getItemMeta() == null) return false;

        try {
            return itemStack.getType().toString().equalsIgnoreCase(mat)
                    && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(StrUtils.translate(name))
                    && itemStack.getItemMeta().getLore().equals(StrUtils.translateLore(lore));

        } catch (Exception e) {
            return false;
        }

    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {


        if (!isOn()) return;

        Player player = event.getPlayer();

        ItemStack itemStack = player.getItemInHand();

        if (itemStack == null || !isItem(itemStack))
            return;

        event.setCancelled(true);


        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            ticks.put(player, 0);
            String reset = config.getConfig().getString("Messages.selection-reset");
            StrUtils.sms(player, reset);
            return;
        }


        Block block = event.getClickedBlock();


        boolean isDiode = block.getType().toString().contains("DIODE");
        boolean isRepeater = block.getType().toString().contains("REPEATER");


        if (!isDiode && !isRepeater) return;

        Diode deiode1 = (Diode) block.getState().getData();


        if (!ticks.containsKey(player))
            ticks.put(player, 0);

        int delay = deiode1.getDelay();
        int total = ticks.get(player) + delay;


        ticks.put(player, total);


        String message = config.getConfig().getString("Messages.tick-message");
        message = StrUtils.replacePlaceholder(message, "{ticks}", total + "");
        message = StrUtils.replacePlaceholder(message, "{game_ticks}", (total * 2) + "");

        StrUtils.sms(player, message);

    }


}
