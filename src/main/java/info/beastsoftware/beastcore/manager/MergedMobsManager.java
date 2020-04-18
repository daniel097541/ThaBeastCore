package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.entity.StackedMob;
import lombok.NoArgsConstructor;
import org.bukkit.entity.LivingEntity;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class MergedMobsManager {

    private final Map<String, Set<StackedMob>> mobsMap = new HashMap<>();


    public Set<StackedMob> getAll() {
        return this.mobsMap
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public void clear(){
        this.mobsMap.clear();
    }

    public void add(StackedMob mob) {
        String index = mob.getIndex();
        Set<StackedMob> mobs = this.mobsMap.get(index);
        if (Objects.isNull(mobs)) {
            mobs = new HashSet<>();
            this.mobsMap.put(index, mobs);
        }
        mobs.add(mob);
    }

    public void remove(StackedMob mob) {
        Set<StackedMob> mobs = this.mobsMap.get(mob.getIndex());
        if (Objects.nonNull(mobs)) {
            mobs.remove(mob);
        }
    }

    public StackedMob getNearest(LivingEntity entity, int radius, int maxSize) {
        String index = StackedMob.encodeIndex(entity.getType(), entity.getWorld().getName());
        Set<StackedMob> mobs = mobsMap.get(index);
        if (Objects.nonNull(mobs)) {
            return mobs
                    .stream()
                    .filter(m -> m.getSize() < maxSize)
                    .filter(m -> m.getEntity().getLocation().distance(entity.getLocation()) <= radius)
                    .min((m1, m2) -> (int) (m1.getEntity().getLocation().distance(entity.getLocation()) - m2.getEntity().getLocation().distance(entity.getLocation())))
                    .orElse(null);
        } else {
            return null;
        }
    }

    public StackedMob fromEntity(LivingEntity entity) {
        String index = StackedMob.encodeIndex(entity.getType(), entity.getWorld().getName());
        Set<StackedMob> mobs = mobsMap.get(index);
        if (Objects.nonNull(mobs)) {
            return mobs
                    .stream()
                    .filter(m -> m.getEntity().equals(entity))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}
