package info.beastsoftware.beastcore.test;

import info.beastsoftware.beastcore.struct.CommandType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

public class Tester implements ITester {

    public Tester() {
    }

    @Override
    public void testCommands(BeastPlayer player) {
        for (CommandType command : CommandType.values()) {
            player.performCommand(command.getName());
            player.sms("command success: " + command.toString());
        }
    }
}
