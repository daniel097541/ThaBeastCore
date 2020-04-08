package info.beastsoftware.beastcore.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Location;

@Singleton
public class PvPManager implements IPvPManager {

    private final IHookManager hookManager;

    @Inject
    public PvPManager(IHookManager hookManager) {
        this.hookManager = hookManager;
    }


    @Override
    public boolean isPvPEnabledInLocation(Location location) {

        if(hookManager.isFactionsHooked()){
            if(this.getAtLocation(location).isSafezone()){
                return false;
            }
        }

        if(hookManager.isWorldGuardHooked()){
            return  !hookManager.getWorldGuardHook().isPvPDisabledHere(location);
        }


        return true;
    }
}
