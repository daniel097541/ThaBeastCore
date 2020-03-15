package info.beastsoftware.core.plugin;

import com.google.inject.Injector;
import info.beastsoftware.core.plugin.binder.BeastCoreModule;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeastCorePlugin extends JavaPlugin {


    private final BeastCoreModule module = new BeastCoreModule(this);


    @Override
    public void onEnable() {
        // Plugin startup logic
        Injector injector = this.module.createInjector();
        injector.injectMembers(this);
    }




}
