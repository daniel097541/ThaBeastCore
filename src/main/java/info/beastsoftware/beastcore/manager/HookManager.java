package info.beastsoftware.beastcore.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.hookcore.service.FactionsService;
import lombok.Getter;

import java.util.Objects;

@Singleton
@Getter
public class HookManager implements IHookManager {

    private final BeastCore plugin;
    private IProtocolLibHook protocolLibHook;
    private IEconomyHook economyHook;
    private IWorldGuardHook worldGuardHook;
    private IEssentialsHook essentialsHook;
    private final FactionsService factionsService;

    @Inject
    public HookManager(BeastCore plugin, FactionsService factionsService) {
        this.plugin = plugin;
        this.factionsService = factionsService;
        this.init();
    }


    private void setUpProtocolManager() {
        if (this.plugin.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
            this.protocolLibHook = new ProtocolLibHook();
        } else {
            this.protocolLibHook = null;
        }
    }


    private void setUpWorldGuard() {
        if (this.plugin.getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            this.worldGuardHook = new WorldGuardHook();
        } else {
            this.plugin.getLogger().info("You dont have WorldGuard installed!");
        }
    }

    private void setUpEssentialsHook() {
        if (this.plugin.getServer().getPluginManager().getPlugin("Essentials") != null) {
            this.plugin.getLogger().info("Hooking into essentials!");
            this.essentialsHook = new IEssentialsHook() {
            };
        } else {
            this.plugin.getLogger().info("You dont have Essentials installed!");
        }
    }


    @Override
    public void init() {
        this.setUpProtocolManager();
        this.economyHook = new EconomyHook(this.plugin);
        this.setUpWorldGuard();
        this.setUpEssentialsHook();
    }

    @Override
    public boolean isFactionsHooked() {
        return Objects.nonNull(this.factionsService.getManager());
    }

    @Override
    public FactionsService getFactionsService() {
        return this.factionsService;
    }

    @Override
    public boolean isProtocolLibHooked() {
        return this.protocolLibHook != null;
    }

    @Override
    public boolean isEconomyHooked() {
        return this.economyHook.isHooked();
    }

    @Override
    public boolean isWorldGuardHooked() {
        return this.worldGuardHook != null;
    }

    @Override
    public boolean isEssentialsHooked() {
        return Objects.nonNull(essentialsHook);
    }
}
