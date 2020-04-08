package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class DebuffCommand extends BeastCommand {


    public DebuffCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.DEBUFF, FeatureType.DEBUFF);
    }

    @Override
    public void run(CommandSender sender, String[] args) {


        config
                .getConfig()
                .getStringList("removed-effects")
                .stream()
                .map(PotionEffectType::getByName)
                .filter(Objects::nonNull)
                .forEach(playerSender::removePotionEffect);


        String message = config.getConfig().getString("debuffed");
        playerSender.sms(message);

    }
}
