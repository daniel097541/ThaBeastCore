package info.beastsoftware.beastcore.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import gnu.trove.set.hash.THashSet;
import info.beastsoftware.hookcore.entity.BeastPlayer;
import org.bukkit.entity.Player;

import java.util.Set;

@Singleton
public class NVManagerImpl implements NVManager {


    private final Set<BeastPlayer> playerSet = new THashSet<>();


    @Inject
    public NVManagerImpl() {
    }

    @Override
    public Set<BeastPlayer> players() {
        return playerSet;
    }
}
