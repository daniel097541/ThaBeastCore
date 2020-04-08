package info.beastsoftware.beastcore.manager;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class ProtocolLibHook implements IProtocolLibHook{

    public ProtocolManager getProtocolManager() {
        return ProtocolLibrary.getProtocolManager();
    }
}
