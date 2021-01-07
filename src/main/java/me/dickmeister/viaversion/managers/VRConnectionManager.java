package me.dickmeister.viaversion.managers;

import me.dickmeister.viaversion.netty.VRClientSideUserConnection;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.platform.ViaConnectionManager;

public class VRConnectionManager extends ViaConnectionManager {

    @Override
    public boolean isFrontEnd(UserConnection connection) {
        return !(connection instanceof VRClientSideUserConnection);
    }
}
