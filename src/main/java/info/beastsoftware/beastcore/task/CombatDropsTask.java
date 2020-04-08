package info.beastsoftware.beastcore.task;

import info.beastsoftware.beastcore.event.combat.DropsProtectionEndEvent;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Set;

public class CombatDropsTask extends BeastCoreCooldownTask {

    private final BeastPlayer killer;
    private final Set<Entity> drops;

    public CombatDropsTask(BeastPlayer killer, int time, Set<Entity> drops) {
        super(time);
        this.killer = killer;
        this.drops = drops;
    }


    @Override
    public void callEndEvent() {
        Bukkit.getPluginManager().callEvent(new DropsProtectionEndEvent(killer, drops));
    }
}
