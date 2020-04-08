package info.beastsoftware.beastcore.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;

@Singleton
@Getter
public class HomesManagerImpl implements HomesManager {
    private final IHookManager hookManager;

    @Inject
    public HomesManagerImpl(IHookManager hookManager) {
        this.hookManager = hookManager;
    }
}
