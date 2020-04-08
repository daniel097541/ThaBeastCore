package info.beastsoftware.beastcore.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public interface StackedMob extends APIAccessor {

    String METADATA = "MERGER-META";

    LivingEntity getEntity();

    void setEntity(LivingEntity entity);

    UUID getIdentifier();

    boolean wasDestroyed();

    void setDestroyed();

    default boolean isInWorld(World world) {
        return this.getEntity().getWorld().equals(world);
    }

    default int isInRadius(Location location, int radius) {

        Location myLocation = this.getEntity().getLocation();
        int r = (int) myLocation.distance(location);

        if (r > radius) {
            return -1;
        }

        return radius;
    }

    default boolean isOfType(EntityType entityType) {
        return this.getType().equals(entityType);
    }

    default int getSize() {

        if (!this.getEntity().hasMetadata(METADATA)) {
            return 0;
        }

        return this.getEntity()
                .getMetadata(METADATA)
                .get(0)
                .asInt();
    }

    default void setSize(int size) {
        FixedMetadataValue value = new FixedMetadataValue(this.getBeastCore(), size);
        this.getEntity()
                .setMetadata(METADATA, value);
    }

    default void killOne() {
        this.decrementSize();

        if (this.getSize() < 1) {
            this.destroy();
        } else {
            this.spawnAgain();
        }
    }

    default void setName(String name) {
        this.getEntity().setCustomName(name);
        this.getEntity().setCustomNameVisible(true);
    }

    default LivingEntity createNewOne(int size) {
        EntityType type = this.getEntity().getType();
        Location location = this.getEntity().getLocation();
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
        entity.setMetadata(METADATA, new FixedMetadataValue(this.getBeastCore(), size));
        return entity;
    }

    default void spawnAgain() {
        LivingEntity entity = this.createNewOne(this.getSize());
        this.setEntity(entity);
    }

    default void incrementSize() {
        int size = this.getSize();
        size += 1;
        this.setSize(size);
    }

    default void decrementSize() {
        int size = this.getSize();
        size -= 1;
        this.setSize(size);
    }

    default void destroy() {
        LivingEntity entity = this.getEntity();
        entity.removeMetadata(METADATA, this.getBeastCore());
        entity.remove();
        this.setDestroyed();
    }

    default EntityType getType() {
        return this.getEntity().getType();
    }

    default boolean check() {
        return !this.getEntity().isDead() && this.getSize() >= 1;
    }

}
