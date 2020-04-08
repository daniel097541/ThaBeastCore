package info.beastsoftware.beastcore.event.combat;

import info.beastsoftware.beastcore.entity.ICombatNPC;

public class CombatPussyMustDieEvent extends CombatLogEvent {

    public CombatPussyMustDieEvent(ICombatNPC npc) {
        super(npc);
    }
}
