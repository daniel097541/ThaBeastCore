package info.beastsoftware.beastcore.entity.impl;

import info.beastsoftware.beastcore.entity.StackedMob;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bukkit.entity.LivingEntity;

import java.util.UUID;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StackedMobImpl implements StackedMob {


    private final UUID id = UUID.randomUUID();

    private LivingEntity entity;
    private boolean destroyed = false;

    public StackedMobImpl(LivingEntity entity, int size) {
        this.entity = entity;
        this.setSize(size);
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
