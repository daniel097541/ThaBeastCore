package info.beastsoftware.beastcore.command;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.cooldown.BeastCooldown;
import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.beastcore.util.IStringUtil;
import info.beastsoftware.beastcore.util.StringUtils;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

public abstract class BeastCommand extends BukkitCommand implements IBeastCommand {


    protected final IConfig config;
    protected final CommandType commandName;
    protected BeastCooldown beastCooldown;
    protected final BeastCore plugin;
    protected final IStringUtil stringUtils = new StringUtils();
    private final FeatureType featureType;
    BeastPlayer playerSender;

    @Override
    public void autoRegister() {

        //do not register the command
        if (!isOn()) return;

        try {
            SimplePluginManager pluginManager = (SimplePluginManager) plugin.getServer().getPluginManager();
            Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);

            SimpleCommandMap map = (SimpleCommandMap) field.get(pluginManager);

            this.register(map);
            field.setAccessible(false);

            map.register(commandName.getName(), this);
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }

        this.broadcastRegistered();
    }


    private Object getField(Object object, String field) {
        Class<?> clazz = object.getClass();
        Field objectField;
        Object result = null;

        try {
            objectField = clazz.getDeclaredField(field);
            assert objectField != null;
            objectField.setAccessible(true);


            result = objectField.get(object);

            objectField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void unregister() {
        Object result = getField(Bukkit.getServer().getPluginManager(), "commandMap");

        SimpleCommandMap commandMap = (SimpleCommandMap) result;
        this.unregister(commandMap);

        this.broadcastUnregistered();
    }

    public BeastCommand(BeastCore plugin, IConfig config, CommandType commandName, BeastCooldown beastCooldown, FeatureType featureType) {
        super(commandName.getName());
        this.config = config;
        this.commandName = commandName;
        this.beastCooldown = beastCooldown;
        this.plugin = plugin;
        this.featureType = featureType;
    }

    public BeastCommand(BeastCore plugin, IConfig config, CommandType commandName, FeatureType featureType) {
        super(commandName.getName());
        this.config = config;
        this.commandName = commandName;
        this.plugin = plugin;
        this.featureType = featureType;
    }


    public BeastCooldown getBeastCooldown() {
        return beastCooldown;
    }


    @Override
    public IConfig getConfig() {
        return config;
    }


    @Override
    public FeatureType getFeatureType() {
        return featureType;
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {

        //no permission
        if (!sender.hasPermission(config.getConfig().getString(commandName.getPermissionPath()))) {
            plugin.sms(sender, config.getConfig().getString(commandName.getNoPermissionMsgPath()));
            return false;
        }

        if (sender instanceof Player)
            playerSender = this.getPlayer((Player) sender);

        if (!isOn()) return false;

        //run command
        run(sender, args);
        return true;
    }


    @Override
    public void addAlias(String alias) {
        this.getAliases().add(alias);
    }
}
