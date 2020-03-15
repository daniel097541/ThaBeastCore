package info.beastsoftware.core.plugin.configs;

import info.beastsoftware.core.config.impl.CoreConfig;
import info.beastsoftware.core.plugin.configs.paths.MobMergerPath;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MobMergerConfig extends CoreConfig {

    @Inject
    public MobMergerConfig() {
        super("mob-merger", "config", new MobMergerPath());
    }
}
