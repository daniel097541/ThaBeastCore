package info.beastsoftware.beastcore.util;

import org.bukkit.command.PluginCommand;

public interface IReflectionManager {
    Object getPrivateField(Object object, String field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException;

    void unRegisterBukkitCommand(PluginCommand cmd);
}
