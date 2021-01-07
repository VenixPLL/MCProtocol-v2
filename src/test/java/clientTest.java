import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.client.impl.MinecraftClient;
import me.dickmeister.mcprotocol.listeners.SessionListener;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.network.packet.impl.handshake.HandshakePacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.client.ClientLoginStartPacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginSetCompressionPacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginSuccessPacket;
import me.dickmeister.mcprotocol.network.packet.impl.play.client.ClientKeepAlivePacket;
import me.dickmeister.mcprotocol.network.packet.impl.play.server.ServerKeepAlivePacket;
import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;

import java.net.Proxy;

public class clientTest {

    public static void main(String[] args) {
        new MCProtocol().initialize(true);

        final MinecraftClient client = new MinecraftClient();
        client.setSessionListener(new SessionListener() {
            @Override
            public void connected(Session session) {
                session.enableViaVersion(true);
                session.getClient().setProtocolVersion(47);
                session.sendPacket(new HandshakePacket(340,"",25565,2));
                session.sendPacket(new ClientLoginStartPacket("McProtocol"));
                session.setConnectionState(ConnectionState.LOGIN);
                System.out.println("Connected!");
            }

            @Override
            public void disconnected(Session session) {
                System.out.println("Disconnected!");
            }

            @Override
            public void onPacketReceived(Session session, Packet packet) {
                if(packet instanceof ServerLoginSetCompressionPacket){
                    session.setCompressionThreshold(((ServerLoginSetCompressionPacket) packet).getThreshold());
                    System.out.println("Compression set!");
                }else if(packet instanceof ServerKeepAlivePacket){
                    session.sendPacket(new ClientKeepAlivePacket(((ServerKeepAlivePacket) packet).getPingId()));
                    System.out.println("Keepalive resent!");
                }else if(packet instanceof ServerLoginSuccessPacket){
                    session.setConnectionState(ConnectionState.PLAY);
                    System.out.println("Success received!");
                }
            }
        });

        client.connect("192.168.100.133",25565, Proxy.NO_PROXY);
    }

}
