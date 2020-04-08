package info.beastsoftware.beastcore.feature.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import info.beastsoftware.beastcore.BeastCore;
import info.beastsoftware.beastcore.beastutils.config.IConfig;
import info.beastsoftware.beastcore.beastutils.config.IDataConfig;
import info.beastsoftware.beastcore.manager.IHookManager;
import info.beastsoftware.beastcore.struct.ButtonType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;

public class PacketsListener implements IPacketsListener {


    private final IHookManager hookManager;
    private final IDataConfig dataConfig;
    private final IConfig config;

    public PacketsListener(IHookManager hookManager, IDataConfig dataConfig, IConfig config) {
        this.hookManager = hookManager;
        this.dataConfig = dataConfig;
        this.config = config;
        this.register();
    }


    @Override
    public void register() {

        ProtocolManager protocolManager = hookManager.getProtocolLibHook().getProtocolManager();

        //invisible tnt, falling blocks and drops
        protocolManager.addPacketListener(new PacketAdapter(BeastCore.getInstance(), ListenerPriority.HIGHEST, PacketType.Play.Server.SPAWN_ENTITY) {
            @Override
            public void onPacketSending(PacketEvent event) {

                try {
                    // TNT spawn packet
                    if (!event.getPacketType().equals(PacketType.Play.Server.SPAWN_ENTITY)) return;

                    Player player = event.getPlayer();
                    Entity entity = event.getPacket().getEntityModifier(player.getWorld()).read(0);

                    if (!(entity instanceof TNTPrimed) && !(entity instanceof FallingBlock) && !(entity instanceof Item))
                        return;

                    if (!dataConfig.exists(player.getUniqueId().toString()))
                        return;

                    YamlConfiguration configuration = dataConfig.getConfigByName(player.getUniqueId().toString());

                    boolean invisibleTnT = configuration.getBoolean(ButtonType.TNT_TOGGLE.toString());
                    boolean invisibleSand = configuration.getBoolean(ButtonType.SAND_TOGGLE.toString());
                    boolean invisibleDrops = configuration.getBoolean(ButtonType.DROPS_TOGGLE.toString());


                    if (entity instanceof TNTPrimed && invisibleTnT)
                        event.setCancelled(true);

                    if (entity instanceof FallingBlock && invisibleSand)
                        event.setCancelled(true);

                    if (entity instanceof Item && invisibleDrops)
                        if (config.getConfig().getStringList("FPS-Booster.invisible-drops-list").contains(((Item) entity).getItemStack().getType().toString()))
                            event.setCancelled(true);
                } catch (NullPointerException e) {
                    return;
                }

            }
        });


        //invisible explosions
        protocolManager.addPacketListener(new PacketAdapter(BeastCore.getInstance(), ListenerPriority.HIGHEST, PacketType.Play.Server.EXPLOSION) {
            @Override
            public void onPacketSending(PacketEvent event) {

                Player player = event.getPlayer();

                if (!dataConfig.exists(player.getUniqueId().toString()))
                    return;

                YamlConfiguration configuration = dataConfig.getConfigByName(player.getUniqueId().toString());

                boolean invisibleExplosions = configuration.getBoolean(ButtonType.EXPLOSIONS_TOGGLE.toString());

                if (invisibleExplosions) event.setCancelled(true);
            }
        });


        //invisible particles
        protocolManager.addPacketListener(new PacketAdapter(BeastCore.getInstance(), ListenerPriority.HIGHEST, PacketType.Play.Server.WORLD_PARTICLES) {
            @Override
            public void onPacketSending(PacketEvent event) {

                Player player = event.getPlayer();

                if (!dataConfig.exists(player.getUniqueId().toString()))
                    return;

                YamlConfiguration configuration = dataConfig.getConfigByName(player.getUniqueId().toString());

                boolean invisibleParticles = configuration.getBoolean(ButtonType.PARTICLES_TOGGLE.toString());

                if (invisibleParticles) event.setCancelled(true);
            }
        });


    }
}
