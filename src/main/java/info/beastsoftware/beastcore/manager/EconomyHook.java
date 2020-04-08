package info.beastsoftware.beastcore.manager;

import info.beastsoftware.beastcore.BeastCore;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class EconomyHook implements IEconomyHook {

    private Economy economy;
    private final BeastCore plugin;

    public EconomyHook(BeastCore plugin) {
        this.plugin = plugin;
        setUp();
    }


    @Override
    public void setUp() {
        if (this.plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            this.plugin.getServer().getLogger().info("Vault not found!");
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return;
        economy = rsp.getProvider();
    }

    @Override
    public boolean isHooked() {
        return this.economy != null;
    }

    @Override
    public Economy getEconomy() {
        return this.economy;
    }
}
