package info.beastsoftware.beastcore.test;

import info.beastsoftware.beastcore.entity.APIAccessor;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

public interface ITester extends APIAccessor {

    void testCommands(BeastPlayer player);

}
