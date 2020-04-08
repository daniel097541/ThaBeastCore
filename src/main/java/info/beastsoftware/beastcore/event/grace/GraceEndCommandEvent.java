package info.beastsoftware.beastcore.event.grace;

import info.beastsoftware.beastcore.beastutils.event.command.GraceCommandEvent;
import org.bukkit.command.CommandSender;

public class GraceEndCommandEvent extends GraceCommandEvent {
    public GraceEndCommandEvent(CommandSender player) {
        super(player);
    }
}
