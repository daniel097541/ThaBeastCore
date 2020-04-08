package info.beastsoftware.beastcore.event.combat;

import info.beastsoftware.beastcore.entity.ICombatNPC;
import info.beastsoftware.beastcore.event.VoidEvent;

public abstract class CombatLogEvent extends VoidEvent {

    private final ICombatNPC npc;


    public CombatLogEvent(ICombatNPC npc) {
        this.npc = npc;
    }


    public ICombatNPC getNpc() {
        return npc;
    }
}
