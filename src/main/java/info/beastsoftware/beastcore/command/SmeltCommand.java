package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.struct.CommandType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


public class SmeltCommand extends BeastCommand {
    public SmeltCommand(BeastCore plugin, IConfig config, CommandType commandName) {
        super(plugin, config, commandName, null);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;

        Player player = (Player) sender;

        Material gold = Material.GOLD_ORE;
        Material iron = Material.IRON_ORE;
        ItemStack itemStack = player.getItemInHand();

        //if not gold or iron ore return
        if (!(itemStack.getType().equals(gold) || itemStack.getType().equals(iron))) {
            plugin.sms(player, config.getConfig().getString("Commands.Smelt.not-an-ore"));
            return;
        }

        //smelt ores in an async runnable
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                if (itemStack.getType().equals(Material.GOLD_ORE))
                    itemStack.setType(Material.GOLD_INGOT);
                if ((itemStack.getType().equals(Material.IRON_ORE)))
                    itemStack.setType(Material.IRON_INGOT);
                plugin.sms(player, config.getConfig().getString("Commands.Smelt.message"));
            }
        }, 1L);

    }
}
