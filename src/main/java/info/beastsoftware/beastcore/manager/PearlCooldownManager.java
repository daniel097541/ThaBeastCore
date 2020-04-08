package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.task.CooldownTask;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PearlCooldownManager implements ICooldownManager {
    private final Map<BeastPlayer, CooldownTask> pearlCooldowns = new ConcurrentHashMap<>();

    @Override
    public Map<BeastPlayer, CooldownTask> getCooldowns() {
        return pearlCooldowns;
    }
}
