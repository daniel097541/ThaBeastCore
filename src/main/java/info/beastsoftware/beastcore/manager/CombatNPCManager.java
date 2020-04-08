package info.beastsoftware.beastcore.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Combat;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.entity.CombatNPC;
import info.beastsoftware.beastcore.entity.ICombatNPC;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class CombatNPCManager implements ICombatNPCManager {


    private final List<ICombatNPC> npcs = new ArrayList<>();
    private final IConfig config;

    @Inject
    public CombatNPCManager(BeastCore plugin, @Combat IConfig config) {
        this.config = config;
        Bukkit
                .getScheduler()
                .scheduleAsyncRepeatingTask(
                        plugin,
                        this::decreaseTimeToDie,
                        0L,
                        20L
                );
    }


    @Override
    public void addPussy(BeastPlayer player, int timeToDie) {

        UUID uuid = player.getUuid();
        Location location = player.getLocation().getBukkitLocation();
        Inventory inventory = player.getInventory();
        String nameFormat = config.getConfig().getString("Combat-Tag.combat-npc-name");
        List<ItemStack> contents = Arrays.stream(player.getInventory().getArmorContents()).collect(Collectors.toList());
        contents.addAll(Arrays.asList(inventory.getContents()));

        ICombatNPC combatNPC = new CombatNPC(nameFormat, uuid, location, contents, timeToDie);
        npcs.add(combatNPC);
    }

    @Override
    public ICombatNPC getNPCOfPlayer(OfflinePlayer player) {
        UUID uuid = player.getUniqueId();
        return this.getAllNPCs()
                .parallelStream()
                .filter(npc -> npc.getPlayerUUID().equals(uuid))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean isAPussy(OfflinePlayer player) {
        UUID uuid = player.getUniqueId();

        return this.getAllNPCs()
                .parallelStream()
                .anyMatch(npc -> npc.getPlayerUUID().equals(uuid));
    }

    @Override
    public List<ICombatNPC> getAllNPCs() {
        return this.npcs;
    }

    @Override
    public void removeNPC(ICombatNPC npc) {
        this.npcs.remove(npc);
    }

}
