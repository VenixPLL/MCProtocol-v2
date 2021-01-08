package proxyTest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.dickmeister.mcprotocol.client.impl.MinecraftClient;
import me.dickmeister.mcprotocol.listeners.SessionListener;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.network.packet.impl.handshake.HandshakePacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.client.ClientLoginStartPacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginSetCompressionPacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginSuccessPacket;

import java.net.Proxy;

@RequiredArgsConstructor
@Getter
public class ServerConnector {

    private final Session owner;
    public MinecraftClient client;

    public void connect(final String host,final int port,final String username){
        client = new MinecraftClient();
        client.setSessionListener(new SessionListener() {
            @Override
            public void connected(Session session) {
                session.enableViaVersion(true);
                session.getClient().setProtocolVersion(47);
                session.sendPacket(new HandshakePacket(340,"",25565,2));
                session.sendPacket(new ClientLoginStartPacket(username));
                session.setConnectionState(ConnectionState.LOGIN);
                System.out.println("Connected to server");
            }

            @Override
            public void disconnected(Session session) {
                System.out.println("Disconnected from server");
            }

            @Override
            public void onPacketReceived(Session session, Packet packet) {
                if(packet instanceof ServerLoginSuccessPacket){
                    owner.setConnectionState(ConnectionState.PLAY);
                    session.setConnectionState(ConnectionState.PLAY);
                }else if(packet instanceof ServerLoginSetCompressionPacket){
                    session.setCompressionThreshold(((ServerLoginSetCompressionPacket) packet).getThreshold());
                    return;
                }

                owner.sendPacket(packet);
            }

            @Override
            public void exceptionCaught(Session session, Throwable t) {
                super.exceptionCaught(session, t);
                t.printStackTrace();
                owner.close();
            }
        });
        client.connect(host,port, Proxy.NO_PROXY);
    }

}
