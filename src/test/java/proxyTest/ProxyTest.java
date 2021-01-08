package proxyTest;

import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.listeners.SessionListener;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.network.packet.impl.handshake.HandshakePacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.client.ClientLoginStartPacket;
import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;
import me.dickmeister.mcprotocol.server.impl.MinecraftServer;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class ProxyTest {

    public static void main(String[] args) {
        final PacketRegistry registry = new MCProtocol().initialize(true);

        MCProtocol.DEBUG = true;

        final AtomicReference<ServerConnector> connector = new AtomicReference<>();

        final MinecraftServer server = new MinecraftServer(registry);
        server.setSessionListener(new SessionListener() {
            @Override
            public void connected(Session session) {
                session.enableViaVersion(false);
                System.out.println("Connected to Proxy");
            }

            @Override
            public void disconnected(Session session) {
                System.out.println("Disconnected from Proxy");
                connector.get().client.close();
            }

            @Override
            public void onPacketReceived(Session session, Packet packet) {
                if(packet instanceof HandshakePacket){
                    session.setConnectionState(ConnectionState.valueOf(((HandshakePacket) packet).getNextState()));
                }else if(packet instanceof ClientLoginStartPacket){
                    System.out.println("Connecting...");
                    connector.set(new ServerConnector(session));
                    Executors.newSingleThreadExecutor().submit(() -> {
                        connector.get().connect("192.168.100.133",25565,((ClientLoginStartPacket) packet).getUsername());
                    });
                    System.out.println("Sent connection");
                }

                if(session.getConnectionState() == ConnectionState.PLAY){
                    connector.get().getClient().getSession().sendPacket(packet);
                }
            }

            @Override
            public void exceptionCaught(Session session, Throwable t) {
                t.printStackTrace();
            }
        });

        server.bind(25565, server.getSessionListener());
    }

}
