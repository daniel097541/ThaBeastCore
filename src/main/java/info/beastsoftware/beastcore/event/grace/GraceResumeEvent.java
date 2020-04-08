package info.beastsoftware.beastcore.event.grace;

import info.beastsoftware.beastcore.beastutils.event.command.GraceCommandEvent;
import org.bukkit.command.CommandSender;

public class GraceResumeEvent extends GraceCommandEvent {
    public GraceResumeEvent(CommandSender player) {
        super(player);
    }
}
