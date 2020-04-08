package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IInventoryUtil;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TickCounterCommand extends BeastCommand {


    public TickCounterCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.TICK_COUNTER, FeatureType.TICK_COUNTER);
    }

    @Override
    public void run(CommandSender sender,  String[] args) {


        String permission = config.getConfig().getString("Command.permission");
        String noPerm = config.getConfig().getString("Command.no-permission-message");
        String received = config.getConfig().getString("Messages.received-counter");


        if (!sender.hasPermission(permission)) {
            StrUtils.sms(sender, noPerm);
            return;
        }


        if (!(sender instanceof Player)) return;


        String mat = config.getConfig().getString("Item.material");
        String name = config.getConfig().getString("Item.name");
        List<String> lore = config.getConfig().getStringList("Item.lore");

        ItemStack itemStack = IInventoryUtil.createItem(1, Material.valueOf(mat), name, lore, true);
        playerSender.getInventory().addItem(itemStack);

        StrUtils.sms(sender, received);
    }
}
