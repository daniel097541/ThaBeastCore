package info.beastsoftware.beastcore.struct;

import org.bukkit.entity.EntityType;

public enum Mob {

    GHAST(EntityType.GHAST),
    SPIDER(EntityType.SPIDER),
    ZOMBIE(EntityType.ZOMBIE),
    ZOMBIE_PIGMAN(EntityType.PIG_ZOMBIE),
    CAVESPIDER(EntityType.CAVE_SPIDER),
    GOLEM(EntityType.IRON_GOLEM),
    GUARDIAN(EntityType.fromName("GUARDIAN")),
    ENDERMITE(EntityType.ENDERMITE),
    PIG(EntityType.PIG),
    HORSE(EntityType.HORSE),
    CREEPER(EntityType.CREEPER),
    SKELETON(EntityType.SKELETON),
    ENDERMAN(EntityType.ENDERMAN),
    BLAZE(EntityType.BLAZE),
    WITCH(EntityType.WITCH),
    COW(EntityType.COW),
    VILLAGER(EntityType.VILLAGER),
    CHICKEN(EntityType.CHICKEN),
    SILVERFISH(EntityType.SILVERFISH),
    ENDER_DRAGON(EntityType.ENDER_DRAGON),
    WOLF(EntityType.WOLF),
    SLIME(EntityType.SLIME),
    MAGMA_CUBE(EntityType.MAGMA_CUBE),
    WITHER_SKELETON(EntityType.WITHER_SKULL),
    MUSHROOM_COW(EntityType.MUSHROOM_COW);


    private EntityType entityType;

    Mob(EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
