package info.beastsoftware.beastcore.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.massivecraft.massivecore.xlib.mongodb.util.Hash;
import info.beastsoftware.beastcore.task.CombatCooldownTask;

import java.util.HashMap;
import java.util.UUID;

@Singleton
public class CombatCooldownManager implements ICombatCooldownManager{

    private final HashMap<UUID, CombatCooldownTask> cooldowns = new HashMap<>();


    @Inject
    public CombatCooldownManager() {
    }

    @Override
    public HashMap<UUID, CombatCooldownTask> getCooldowns() {
        return this.cooldowns;
    }
}
