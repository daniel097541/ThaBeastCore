package info.beastsoftware.beastcore.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import gnu.trove.set.hash.THashSet;
import info.beastsoftware.beastcore.annotations.features.MobMerger;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.entity.StackedMob;

import java.util.Set;

@Singleton
public class MobMergerManagerImpl implements MobMergerManager {


    private final THashSet<StackedMob> stacks = new THashSet<>();
    private final IConfig config;

    @Inject
    public MobMergerManagerImpl(@MobMerger IConfig config) {
        this.config = config;
    }


    @Override
    public Set<StackedMob> getStacks() {
        return stacks;
    }

    @Override
    public IConfig getConfig() {
        return this.config;
    }
}
