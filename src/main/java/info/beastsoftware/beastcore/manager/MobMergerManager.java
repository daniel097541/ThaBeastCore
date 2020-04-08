package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.entity.APIAccessor;
import info.beastsoftware.beastcore.entity.StackedMob;
import info.beastsoftware.beastcore.entity.impl.StackedMobImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public interface MobMergerManager extends APIAccessor {


    Set<StackedMob> getStacks();

    IConfig getConfig();

    /**
     * @param entityType The type of the entity
     * @return The max size allowed for this entity
     */
    default int getMaxSize(EntityType entityType) {
        boolean perMobSizes = getConfig().getConfig().getBoolean("Mob-Merger.per-mob-max-stack-sizes.enabled");

        //if per mob sizes enabled
        if (perMobSizes) {

            //if the mob is in the list, return this value, in other case use default stack size
            String path = "Mob-Merger.per-mob-max-stack-sizes.list";
            for (String section : getConfig().getConfig().getConfigurationSection(path).getKeys(false)) {
                if (section.equalsIgnoreCase(entityType.toString())) {
                    return getConfig().getConfig().getInt(path + "." + section);
                }
            }
        }

        //use default stack size
        return getConfig().getConfig().getInt("Mob-Merger.max-stack-size");
    }

    /**
     * Creates a new stack from an entity just spawned and a predefined size
     *
     * @param entity The entity that just spawned
     * @param size   The initial size of the stack
     */
    default void addStack(LivingEntity entity, int size) {
        StackedMob stackedMob = new StackedMobImpl(entity, size);
        this.getStacks().add(stackedMob);

        this.updateCustomName(stackedMob);
    }


    /**
     * Updates the name of an stacked mob
     *
     * @param stackedMob The stacked mob whose we want to update
     */
    default void updateCustomName(StackedMob stackedMob) {
        //set name
        String customName = "";
        if (Bukkit.getVersion().contains("1.7.")) return;
        customName = StrUtils.translate(replacePlaceholders(stackedMob.getSize(), stackedMob.getEntity().getType().toString()));

        stackedMob.setName(customName);
    }


    default String replacePlaceholders(int number, String type) {
        String name = getConfig().getConfig().getString("Mob-Merger.custom-mob-name");
        if (name.contains("{number}")) {
            name = name.replace("{number}", "" + number);
        }
        if (name.contains("{mob_type}")) {
            name = name.replace("{mob_type}", type);
        }

        return name;
    }


    /**
     * Removes a stack from the set
     *
     * @param stackedMob The stack that we want to remove
     */
    default void remove(StackedMob stackedMob) {
        this.getStacks().remove(stackedMob);
    }

    /**
     * Kills one mob in the stack, if the resulting stack is empty, destroy it
     *
     * @param stackedMob The stack that we want to reduce
     */
    default void killOne(StackedMob stackedMob) {

        stackedMob.killOne();

        //remove if was killed whole stack
        if (stackedMob.wasDestroyed()) {
            this.remove(stackedMob);
        } else {
            this.updateCustomName(stackedMob);
        }

    }


    /**
     * Kills the whole stack
     *
     * @param stackedMob The stack we want to vanish 4ever
     */
    default void killWholeStack(StackedMob stackedMob) {
        this.remove(stackedMob);
        stackedMob.destroy();
    }

    /**
     * Kills all the stacks.
     */
    default void killAll() {
        for (Iterator<StackedMob> iterator = getStacks().iterator(); iterator.hasNext(); ) {
            iterator.next().destroy();
            iterator.remove();
        }
    }

    /**
     * @param entity The living entity you have
     * @return the stack associated to that entity, null if could not be found
     */
    default StackedMob getFromEntity(LivingEntity entity) {
        return this.getStacks()
                .stream()
                .filter(s -> s.getEntity().equals(entity))
                .findFirst()
                .orElse(null);
    }


    /**
     * Adds one new entity to the stack, aka increments the size in one.
     * If the stack reached its max size, creates a new one.
     *
     * @param stackedMob The stacked mob we want to increment
     */
    default void addOneToStack(StackedMob stackedMob) {

        int size = stackedMob.getSize();
        EntityType entityType = stackedMob.getType();
        int mazSize = this.getMaxSize(entityType);

        //spawns a new stack
        if (size >= mazSize) {
            LivingEntity newOne = stackedMob.createNewOne(1);
            this.addStack(newOne, 1);
        }

        //increments size of existing stack
        else {
            stackedMob.incrementSize();
            this.updateCustomName(stackedMob);
        }
    }


    /**
     * @param stackedMob The stack we want to see if could be incremented
     * @return True if the size of the stack has not reached the max
     */
    default boolean stackCanBeIncremented(StackedMob stackedMob) {
        return this.getMaxSize(stackedMob.getType()) > stackedMob.getSize();
    }


    /**
     * Looks for a near stack in a radius for that entity,
     * if there is no near stack, create a new one
     *
     * @param livingEntity
     * @param radius
     * @return
     */
    default boolean tryToStack(LivingEntity livingEntity, int radius) {

        World world = livingEntity.getWorld();
        Location location = livingEntity.getLocation();
        int lastRadius = Integer.MAX_VALUE;
        StackedMob nearest = null;
        EntityType type = livingEntity.getType();

        for (Iterator<StackedMob> iterator = this.getStacks().iterator(); iterator.hasNext(); ) {

            StackedMob stackedMob = iterator.next();

            //check if the stack is still correct
            if (!stackedMob.check()) {
                iterator.remove();
                continue;
            }


            //filter by world and if it could be incremented and if is of the same type
            if (stackedMob.isInWorld(world) && this.stackCanBeIncremented(stackedMob) && stackedMob.isOfType(type)) {

                int distance = stackedMob.isInRadius(location, radius);

                //is nearer than the last stack
                if (distance >= 0 && distance < lastRadius) {
                    lastRadius = distance;
                    nearest = stackedMob;

                    //its very near, break loop and continue doing shit
                    if (distance <= 3) {
                        break;
                    }

                }

            }
        }

        //increment stack and return true to cancel spawn event
        if (Objects.nonNull(nearest)) {
            this.addOneToStack(nearest);
            return true;
        }

        //create new stack return false to prevent event from being cancelled
        else {
            this.addStack(livingEntity, 1);
            return false;
        }
    }


}
