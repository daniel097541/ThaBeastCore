package info.beastsoftware.beastcore.entity.impl;

import info.beastsoftware.beastcore.entity.StackedMob;
import lombok.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Base64;
import java.util.UUID;

@EqualsAndHashCode
@ToString
@Getter
public class StackedMobImpl implements StackedMob {

    private final UUID id = UUID.randomUUID();
    private LivingEntity entity;
    private boolean destroyed = false;
    private final String index;

    public StackedMobImpl(LivingEntity entity, int size) {
        this.entity = entity;
        this.setSize(size);
        this.index = StackedMob.encodeIndex(entity.getType(), entity.getWorld().getName());
    }

    @Override
    public boolean wasDestroyed() {
        return destroyed;
    }

    @Override
    public void setDestroyed() {
        this.destroyed = true;
    }

    @Override
    public UUID getIdentifier() {
        return id;
    }

    @Override
    public LivingEntity getEntity() {
        return entity;
    }

    @Override
    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }
}
