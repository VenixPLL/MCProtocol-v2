package me.dickmeister.viaversion.managers;

import me.dickmeister.viaversion.ViaClient;
import me.dickmeister.viaversion.netty.VRClientSideUserConnection;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.base.VersionProvider;

public class VRVersionProvider extends VersionProvider {

    @Override
    public int getServerProtocol(UserConnection connection) throws Exception {
        ViaClient client = ViaClient.getClient(connection.getId());
        if (connection instanceof VRClientSideUserConnection && client != null)
            return client.getProtocolConnection();
        return super.getServerProtocol(connection);
    }
}
