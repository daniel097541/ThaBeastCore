package info.beastsoftware.beastcore.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface ICombatNPC {

    UUID getPlayerUUID();

    Location getLocation();

    List<ItemStack> getInventoryContents();

    int getTimeLeftToDie();

    void decreaseTimeToDie();

    default OfflinePlayer getOfflinePlayer(){
        return Bukkit.getOfflinePlayer(this.getPlayerUUID());
    }

    void spawn();

    void despawn();

    void updateName();

    void freezeTime();

    boolean isTimeFrozen();

    Entity getNPC();

    default void dropContents(){
        this.getInventoryContents()
                .stream()
                .filter(Objects::nonNull)
                .filter(item -> !item.getType().equals(Material.AIR))
                .forEach(item -> Objects.requireNonNull(this.getLocation().getWorld()).dropItem(this.getLocation(),item));
    }
}
