package info.beastsoftware.beastcore.command;


import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.event.gui.CombatGuiOpenEvent;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CombatCommand extends BeastCommand {

    public CombatCommand(BeastCore plugin, IConfig config) {
        super(plugin, config, CommandType.CT, FeatureType.COMBAT);
        addAlias("combat");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) return;


        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("gui") || args[0].equalsIgnoreCase("menu")) {

                if (!playerSender.hasPermission(config.getConfig().getString("Combat-Tag.command.gui-permission"))) {
                    playerSender.sms(config.getConfig().getString("Combat-Tag.command.No-Permission"));
                    return;
                }

                Bukkit.getPluginManager().callEvent(new CombatGuiOpenEvent(playerSender));
                return;
            }
        }


        //check if combat disabled
        if (!isOn()) {
            playerSender.sms(config.getConfig().getString("Combat-Tag.disabled-message"));
            return;
        }

        //check if is on beastCooldown
        if (!plugin.getApi().isPlayerInCombat(playerSender)) {
            playerSender.sms(config.getConfig().getString("Combat-Tag.not-in-combat"));
            return;
        }


        //send message with beastCooldown
        String message = config.getConfig().getString("Combat-Tag.message");
        message = stringUtils.replacePlaceholder(message, "{beastCooldown}", plugin.getApi().getCombatTimeOfPlayer(playerSender) + "");
        playerSender.sms(message);
    }
}
