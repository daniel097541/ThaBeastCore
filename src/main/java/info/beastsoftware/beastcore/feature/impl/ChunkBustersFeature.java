package info.beastsoftware.beastcore.feature.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.annotations.features.ChunkBusters;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.command.ChunkBusterCommand;
import info.beastsoftware.beastcore.feature.AbstractFeature;
import info.beastsoftware.beastcore.feature.listener.ChunkBustersFeatureListener;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.FeatureType;

@Singleton
public class ChunkBustersFeature extends AbstractFeature {

    @Inject
    public ChunkBustersFeature(@ChunkBusters IConfig config, BeastCore plugin) {
        super(config, new ChunkBustersFeatureListener(config), new ChunkBusterCommand(plugin,config), FeatureType.CHUNK_BUSTERS);
    }
}
