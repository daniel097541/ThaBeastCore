package info.beastsoftware.beastcore.event.grace;

import info.beastsoftware.beastcore.beastutils.event.command.GraceCommandEvent;
import org.bukkit.command.CommandSender;

public class GracePauseEvent extends GraceCommandEvent {
    public GracePauseEvent(CommandSender player) {
        super(player);
    }
}
