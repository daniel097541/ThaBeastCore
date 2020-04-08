package info.beastsoftware.beastcore.feature.listener;

import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.feature.AbstractFeatureListener;
import info.beastsoftware.beastcore.struct.FeatureType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ServerProtectorFeatureListener extends AbstractFeatureListener {

    public ServerProtectorFeatureListener(IConfig config) {
        super(config, FeatureType.SERVER_PROTECTOR);
    }


    @EventHandler
    public void onPluginCommand(PlayerCommandPreprocessEvent e) {

        if (!isOn()) return;

        if (!allowCommand(e.getMessage()) && !e.getPlayer().hasPermission(config.getConfig().getString("Server-Protector.bypass-permission"))) {
            e.setCancelled(true);
            BeastCore.getInstance().sms(e.getPlayer(), config.getConfig().getString("Server-Protector.message"));
        }
        if (config.getConfig().getBoolean("Server-Protector.block-syntaxis") && e.getMessage().contains(":") && !e.getPlayer().hasPermission("btf.admin.bypass")) {
            e.setCancelled(true);
            BeastCore.getInstance().sms(e.getPlayer(), config.getConfig().getString("Server-Protector.message"));
        }
    }


    private boolean allowCommand(String command) {
        for (String cmd : config.getConfig().getStringList("Server-Protector.Blocked-Commands")) {
            if (command.startsWith(cmd))
                return false;
        }
        return true;
    }
}
