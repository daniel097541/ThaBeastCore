package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.cooldown.CommandCooldown;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.IStringUtil;
import info.beastsoftware.beastcore.util.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

public class CommandCooldownFeatureListener extends AbstractFeatureListener {


    private final CommandCooldown commandCooldown = new CommandCooldown();
    private final IStringUtil stringUtil = new StringUtils();


    public CommandCooldownFeatureListener(IConfig config) {
        super(config, FeatureType.COMMAND_COOLWON);
    }



    private Command getCommand(String command){

        try {
            SimplePluginManager pluginManager = (SimplePluginManager) Bukkit.getServer().getPluginManager();
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);

            SimpleCommandMap map = (SimpleCommandMap) field.get(pluginManager);

            for(Command cmd : map.getCommands()){

                if(cmd.getName().equalsIgnoreCase(command))
                    return cmd;

                for(String alias : cmd.getAliases()){
                    if(alias.equalsIgnoreCase(command))
                        return cmd;
                }

            }

        }
        catch (Exception e){
            return null;
        }

        return null;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {

        if(!isOn()) return;

        if (e.isCancelled()) return;

        String cmd = e.getMessage().split(" ")[0];
        cmd = cmd.replace("/", "");

        Command pluginCommand = getCommand(cmd);

        if (pluginCommand == null) return;



        for (String command : config.getConfig().getConfigurationSection("command-Cooldowns").getKeys(false)) {


            Command iterationCommand = getCommand(command);


            if (pluginCommand.equals(iterationCommand)) {

                //cancel command if is on beastCooldown
                if (commandCooldown.isOnCooldown(iterationCommand, e.getPlayer().getName())) {
                    String cooldown = stringUtil.formatCooldown(commandCooldown.getCooldown(iterationCommand, e.getPlayer().getName()));
                    String message = config.getConfig().getString("command-Cooldowns." + command + ".on-beastCooldown-message");
                    message = stringUtil.replacePlaceholder(message, "{cooldown_formatted}", cooldown);
                    BeastCore.getInstance().sms(e.getPlayer(), message);
                    e.setCancelled(true);
                    return;
                }

                if (e.getPlayer().hasPermission("command-Cooldowns." + command + ".bypass-permission"))
                    return;

                //add the beastCooldown
                commandCooldown.addCooldown(iterationCommand, e.getPlayer().getName(), config.getConfig().getInt("command-Cooldowns." + command + ".beastCooldown"));
                return;
            }

        }


    }


}
