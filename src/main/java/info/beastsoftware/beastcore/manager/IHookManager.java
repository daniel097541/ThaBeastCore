package info.beastsoftware.beastcore.manager;

import info.beastsoftware.hookcore.service.FactionsService;

public interface IHookManager {


    void init();

    boolean isFactionsHooked();

    FactionsService getFactionsService();

    boolean isProtocolLibHooked();

    IProtocolLibHook getProtocolLibHook();

    boolean isEconomyHooked();

    IEconomyHook getEconomyHook();

    INBTHook getNBTHook();

    boolean isWorldGuardHooked();

    IWorldGuardHook getWorldGuardHook();

    boolean isEssentialsHooked();

    IEssentialsHook getEssentialsHook();

}
