package info.beastsoftware.beastcore.manager;

import net.milkbowl.vault.economy.Economy;

public interface IEconomyHook {

    void setUp();

    boolean isHooked();

    Economy getEconomy();

}
