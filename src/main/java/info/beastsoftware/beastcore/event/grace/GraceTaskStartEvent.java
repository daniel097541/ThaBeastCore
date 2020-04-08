package info.beastsoftware.beastcore.event.grace;

import info.beastsoftware.beastcore.beastutils.event.cooldown.GraceTaskEvent;

public class GraceTaskStartEvent extends GraceTaskEvent {
    public GraceTaskStartEvent(int timeLeft) {
        super(timeLeft);
    }
}
