package info.beastsoftware.core.plugin.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import info.beastsoftware.core.config.IConfig;
import info.beastsoftware.core.plugin.BeastCorePlugin;
import info.beastsoftware.core.plugin.binder.annotation.MobMerger;
import info.beastsoftware.core.plugin.configs.MobMergerConfig;

public class BeastCoreModule extends AbstractModule {


    private final BeastCorePlugin plugin;

    public BeastCoreModule(BeastCorePlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    protected void configure() {
        this.bind(BeastCorePlugin.class).toInstance(this.plugin);
        this.bind(IConfig.class).annotatedWith(MobMerger.class).to(MobMergerConfig.class);
    }

    public Injector createInjector(){
        return Guice.createInjector(this);
    }

}
