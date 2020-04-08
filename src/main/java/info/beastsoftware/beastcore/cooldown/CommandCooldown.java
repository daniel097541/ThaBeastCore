package info.beastsoftware.beastcore.cooldown;

import info.beastsoftware.beastcore.BeastCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;

import java.util.HashMap;
import java.util.Iterator;

public class CommandCooldown implements Runnable, ICommandCooldown {

    private final HashMap<Command, HashMap<String, Integer>> cooldowns = new HashMap<>();


    public CommandCooldown() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(BeastCore.getInstance(), this, 20, 20);
    }

    @Override
    public void addCooldown(Command command, String player, int cooldown) {
        if (!cooldowns.containsKey(command)) {
            cooldowns.put(command, new HashMap<>());
        }
        cooldowns.get(command).put(player, cooldown);
    }


    @Override
    public boolean isOnCooldown(Command command, String player) {
        return cooldowns.containsKey(command) && cooldowns.get(command).containsKey(player);
    }

    @Override
    public void endCooldown(Command command, String player) {
        if (!cooldowns.containsKey(command))
            return;
        if (!cooldowns.get(command).containsKey(player))
            return;
        cooldowns.get(command).put(player, 0);
    }

    @Override
    public void decrementCooldowns() {
        if (cooldowns.isEmpty()) return;

        for (Iterator<Command> iterator = cooldowns.keySet().iterator(); iterator.hasNext(); ) {
            Command command = iterator.next();

            if (cooldowns.get(command).isEmpty()) {
                iterator.remove();
                continue;
            }

            for (Iterator<String> iter = cooldowns.get(command).keySet().iterator(); iter.hasNext(); ) {
                String player = iter.next();
                cooldowns.get(command).put(player, cooldowns.get(command).get(player) - 1);
                if (cooldowns.get(command).get(player) <= 0) {
                    iter.remove();
                }

            }
        }
    }

    @Override
    public int getCooldown(Command command, String player) {
        return cooldowns.get(command).get(player);
    }

    @Override
    public void reload() {
        cooldowns.clear();
    }

    @Override
    public void run() {
        decrementCooldowns();
    }
}
