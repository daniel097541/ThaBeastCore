package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.Printer;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.PrinterCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.PrinterFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.printer.IPrinterManager;
import info.beastsoftware.beastcore.struct.FeatureType;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;


@Singleton
public class PrinterFeature extends AbstractFeature {


    @Inject
    public PrinterFeature(@Printer IConfig config,
                          IHookManager hookManager,
                          IPrinterManager printerManager,
                          BeastCore plugin) {
        super(config, new PrinterFeatureListener(config,hookManager.getEconomyHook(), printerManager), new PrinterCommand(plugin,config,hookManager.getEconomyHook(), printerManager), FeatureType.PRINTER);

        if(!hookManager.isEconomyHooked()){
            this.disableAll();
        }
    }

    public boolean isOnPrinter(BeastPlayer player) {
        return ((PrinterFeatureListener) listener).isOnPrinter(player);
    }
}
