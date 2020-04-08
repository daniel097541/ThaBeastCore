package info.beastsoftware.beastcore.event.grace;

import info.beastsoftware.beastcore.beastutils.event.command.GraceCommandEvent;
import org.bukkit.command.CommandSender;

public class GraceDisplayCommandEvent extends GraceCommandEvent {
    public GraceDisplayCommandEvent(CommandSender player) {
        super(player);
    }
}
