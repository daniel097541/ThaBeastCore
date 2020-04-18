package info.beastsoftware.beastcore.service;

import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.utils.StrUtils;
import info.beastsoftware.beastcore.entity.StackedMob;
import info.beastsoftware.beastcore.entity.StackedMobDiedResponse;
import info.beastsoftware.beastcore.entity.impl.StackedMobImpl;
import info.beastsoftware.beastcore.manager.MergedMobsManager;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class StackedMobsService {

    private final IConfig config;
    private final MergedMobsManager mergedMobsManager = new MergedMobsManager();


    public int getMaxAmount(EntityType entityType) {
        boolean perMobSizes = this.config.getConfig().getBoolean("Mob-Merger.per-mob-max-stack-sizes.enabled");
        //if per mob sizes enabled
        if (perMobSizes) {

            //if the mob is in the list, return this value, in other case use default stack size
            String path = "Mob-Merger.per-mob-max-stack-sizes.list";
            for (String section : this.config.getConfig().getConfigurationSection(path).getKeys(false)) {
                if (section.equalsIgnoreCase(entityType.toString())) {
                    return this.config.getConfig().getInt(path + "." + section);
                }
            }
        }
        //use default stack size
        return this.config.getConfig().getInt("Mob-Merger.max-stack-size");
    }

    public void updateCustomName(StackedMob stackedMob) {
        //set name
        String customName = "";
        if (Bukkit.getVersion().contains("1.7.")) return;
        customName = StrUtils.translate(replacePlaceholders(stackedMob.getSize(), stackedMob.getEntity().getType().toString()));

        stackedMob.setName(customName);
    }

    private String replacePlaceholders(int number, String type) {
        String name = this.config.getConfig().getString("Mob-Merger.custom-mob-name");
        if (name.contains("{number}")) {
            name = name.replace("{number}", "" + number);
        }
        if (name.contains("{mob_type}")) {
            name = name.replace("{mob_type}", type);
        }

        return name;
    }

    private int getRadiusToStack(){
        return 10;
    }

    public boolean add(LivingEntity entity) {

        int maxSize = this.getMaxAmount(entity.getType());
        int radius = this.getRadiusToStack();
        boolean wasStacked = false;

        StackedMob stackedMob = this.mergedMobsManager.getNearest(entity, radius, maxSize);
        if (Objects.nonNull(stackedMob)) {
            stackedMob.incrementSize();
            wasStacked = true;
        } else {
            stackedMob = new StackedMobImpl(entity, 1);
        }

        this.updateCustomName(stackedMob);
        this.mergedMobsManager.add(stackedMob);

        return wasStacked;
    }

    private boolean isFullStackKill(StackedMob stackedMob) {
        return config.getConfig().getBoolean("Mob-Merger.Kill-Full-Stack.enabled") && config.getConfig().getStringList("Mob-Merger.Kill-Full-Stack.damage-sources").contains(stackedMob.getEntity().getLastDamageCause().getCause().name());
    }

    private boolean canMultiplyDrops(StackedMob stackedMob) {
        return config.getConfig().getBoolean("Mob-Merger.Kill-Full-Stack.multiply-drops");
    }

    public StackedMobDiedResponse killAndRetrieveDrops(LivingEntity entity, List<ItemStack> originalDrops, int originalExp) {

        StackedMob stackedMob = this.mergedMobsManager.fromEntity(entity);
        List<ItemStack> drops = new ArrayList<>(originalDrops);
        int exp = originalExp;
        boolean stacked = false;

        if (Objects.nonNull(stackedMob)) {

            stacked = true;
            int size = stackedMob.getSize();

            if (this.isFullStackKill(stackedMob)) {
                this.mergedMobsManager.remove(stackedMob);
                stackedMob.destroy();

                //multiply drops
                if (this.canMultiplyDrops(stackedMob)) {
                    drops = new ArrayList<>();
                    for (ItemStack itemStack : originalDrops) {
                        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
                            continue;
                        }
                        //// CLONE AND ADD TO THE NEW LIST OF ITEMS
                        ItemStack cloned = itemStack.clone();
                        cloned.setAmount(itemStack.getAmount() * stackedMob.getSize());
                        drops.add(cloned);
                    }

                    exp = originalExp * size;
                }
            } else {
                stackedMob.killOne();
                //remove if was killed whole stack
                if (stackedMob.wasDestroyed()) {
                    this.mergedMobsManager.remove(stackedMob);
                } else {
                    this.updateCustomName(stackedMob);
                }
            }
        }

        return new StackedMobDiedResponse(drops, exp, stacked);
    }

    public void killAll() {
        Set<StackedMob> mobs = this.mergedMobsManager.getAll();
        mobs.forEach(StackedMob::destroy);
        this.mergedMobsManager.clear();
    }

    public int getAmountOfStacks() {
        return this.mergedMobsManager.getAll().size();
    }
}
