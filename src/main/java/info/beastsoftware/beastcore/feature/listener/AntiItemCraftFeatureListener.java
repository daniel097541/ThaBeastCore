package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.gui.AntiItemCraftGui;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AntiItemCraftFeatureListener extends AbstractFeatureListener {
    public AntiItemCraftFeatureListener(IConfig config) {
        super(config, FeatureType.ANTI_ITEM_CRAFT);
    }

    @EventHandler
    public void onItemCraft(PrepareItemCraftEvent e) {

        if(!isOn()) return;

        if (e.getRecipe() == null) return;

        if (e.getRecipe().getResult() == null) return;

        if (e.getInventory() == null) return;

        if (AntiItemCraftGui.getInstance().isListed(e.getRecipe().getResult().getType())) {
            String type = e.getRecipe().getResult().getType().name();
            e.getInventory().setResult(new ItemStack(Material.AIR));
            String message = config.getConfig().getString("Anti-Item-Craft.message");
            message = StrUtils.replacePlaceholder(message, "{item}", type);

            List<HumanEntity> viewer = e.getViewers();
            if (viewer.isEmpty()) return;
            Player player = (Player) viewer.get(0);
            StrUtils.sms(player, message);
        }
    }

}
