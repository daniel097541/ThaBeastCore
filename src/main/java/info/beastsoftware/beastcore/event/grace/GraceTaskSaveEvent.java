package info.beastsoftware.beastcore.event.grace;

import info.beastsoftware.beastcore.beastutils.event.cooldown.GraceTaskEvent;

public class GraceTaskSaveEvent extends GraceTaskEvent {
    public GraceTaskSaveEvent(int timeLeft) {
        super(timeLeft);
    }
}
