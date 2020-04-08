package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.command.IBeastCommand;
import info.beastsoftware.beastcore.struct.CommandType;

public interface ICommandManager {

    void load();

    IBeastCommand getCommand(CommandType type);

}
