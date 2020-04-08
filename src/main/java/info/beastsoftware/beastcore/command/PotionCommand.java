package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.IPlayerUtil;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class PotionCommand extends BeastCommand {
    public PotionCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.POTION, FeatureType.POTION);
        addAlias("pot");
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            for (String line : config.getConfig().getConfigurationSection("Commands.Potions.Pot-List").getKeys(false)) {
                //    BeastCore.getInstance().sms(player,config.getConfig().getString(stringUtils.replacePlaceholder(config.getConfig().getString("Commands.Potions.format-message"),"{potion_name}",line)));
                String message = config.getConfig().getString("Commands.Potions.format-message");
                message = stringUtils.replacePlaceholder(message, "{potion_name}", line);
                BeastCore.getInstance().sms(player, message);
            }
            return;
        }


        String potion = args[0];

        ///////// NOT LISTED
        if (!isListedPotion(potion)) {
            String message = config.getConfig().getString("Commands.Potions.potion-not-listed");
            message = stringUtils.replacePlaceholder(message, "{name}", potion);
            BeastCore.getInstance().sms(player, message);
            return;
        }


        potion = potion.toUpperCase();
        String path = "Commands.Potions.Pot-List." + potion;

        if (!player.hasPermission(config.getConfig().getString(path + ".permission"))) {
            BeastCore.getInstance().sms(player, config.getConfig().getString(path + ".no-permission-msg"));
            return;
        }

        int duration = config.getConfig().getInt(path + ".duration");
        int level = config.getConfig().getInt(path + ".level");

        IPlayerUtil.addPotionEffect(player, potion, duration, level);
        BeastCore.getInstance().sms(player, config.getConfig().getString(path + ".message"));
    }


    private boolean isListedPotion(String potion) {

        for (String pot : config.getConfig().getConfigurationSection("Commands.Potions.Pot-List").getKeys(false)) {
            if (pot.equalsIgnoreCase(potion)) {
                return PotionEffectType.getByName(potion) != null;
            }
        }

        return false;
    }
}
