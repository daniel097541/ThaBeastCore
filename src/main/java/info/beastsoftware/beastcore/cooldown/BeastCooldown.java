package info.beastsoftware.beastcore.cooldown;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.event.CooldownEndEvent;
import info.beastsoftware.beastcore.struct.CooldownType;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Iterator;

public class BeastCooldown implements ICooldown, Runnable {

    private final CooldownType cooldownType;
    private HashMap<String, Integer> cooldowns;

    public BeastCooldown(CooldownType cooldownType) {
        cooldowns = new HashMap<>();
        this.cooldownType = cooldownType;
        Bukkit.getScheduler().runTaskTimerAsynchronously(BeastCore.getInstance(), this, 20, 20);
    }

    @Override
    public void run() {
        decrementCooldowns();
    }

    @Override
    public void decrementCooldowns() {
        for (Iterator<String> iterator = cooldowns.keySet().iterator(); iterator.hasNext(); ) {
            String player = iterator.next();
            cooldowns.put(player, cooldowns.get(player) - 1);
            if (cooldowns.get(player) <= 0) {
                iterator.remove();
            }
        }
    }

    @Override
    public void addCooldown(String player, int cooldown) {
        cooldowns.put(player, cooldown);
    }

    @Override
    public void endCooldown(String player) {
        cooldowns.remove(player);
        Bukkit.getServer().getPluginManager().callEvent(new CooldownEndEvent(player, cooldownType));
    }

    @Override
    public boolean isOnCooldown(String player) {
        return cooldowns.containsKey(player);
    }

    @Override
    public int getCooldown(String player) {
        if (isOnCooldown(player))
            return cooldowns.get(player);
        return 0;
    }

    @Override
    public void reload() {
        cooldowns = new HashMap<>();
    }
}
