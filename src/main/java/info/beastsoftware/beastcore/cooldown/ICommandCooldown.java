package info.beastsoftware.beastcore.cooldown;

import org.bukkit.command.Command;

public interface ICommandCooldown {

    void addCooldown(Command command, String player, int cooldown);

    void endCooldown(Command command, String player);

    boolean isOnCooldown(Command command, String player);

    void decrementCooldowns();

    int getCooldown(Command command, String player);

    void reload();

}
