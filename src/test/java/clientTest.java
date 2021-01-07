import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.client.impl.MinecraftClient;
import me.dickmeister.mcprotocol.listeners.SessionListener;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.network.packet.impl.handshake.HandshakePacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.client.ClientLoginStartPacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginEncryptionRequestPacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginSetCompressionPacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginSuccessPacket;
import me.dickmeister.mcprotocol.network.packet.impl.play.client.ClientKeepAlivePacket;
import me.dickmeister.mcprotocol.network.packet.impl.play.server.ServerKeepAlivePacket;
import me.dickmeister.mcprotocol.util.PremiumUtil;
import net.chris54721.openmcauthenticator.exceptions.AuthenticationUnavailableException;
import net.chris54721.openmcauthenticator.exceptions.RequestException;

import java.net.Proxy;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class clientTest {

    public static void main(String[] args) throws AuthenticationUnavailableException, RequestException {
        new MCProtocol().initialize(true);

        MCProtocol.DEBUG = true;

        final PremiumUtil.PremiumSession premiumSessionsession = PremiumUtil.makeSession("email", "passwd");
        premiumSessionsession.authenticate(Proxy.NO_PROXY);

        System.out.println("Premium username: " + premiumSessionsession.getUsername());
        System.out.println("Premium UID: " + premiumSessionsession.getSelectedProfileUID().toString());

        final AtomicReference<UUID> uuid = new AtomicReference<>();

        final MinecraftClient client = new MinecraftClient();
        client.setSessionListener(new SessionListener() {
            @Override
            public void connected(Session session) {
                session.enableViaVersion(true);
                session.getClient().setProtocolVersion(47); //Target server protocol
                session.sendPacket(new HandshakePacket(340, "192.168.100.133", 25565, 2));
                session.sendPacket(new ClientLoginStartPacket("1381"));
                session.setConnectionState(ConnectionState.LOGIN);
                System.out.println("Connected!");
            }

            @Override
            public void disconnected(Session session) {
                System.out.println("Disconnected!");
            }

            @Override
            public void onPacketReceived(Session session, Packet packet) {
                if (packet instanceof ServerLoginSetCompressionPacket) {
                    session.setCompressionThreshold(((ServerLoginSetCompressionPacket) packet).getThreshold());
                    System.out.println("Compression set!");
                } else if (packet instanceof ServerKeepAlivePacket) {
                    session.sendPacket(new ClientKeepAlivePacket(((ServerKeepAlivePacket) packet).getPingId()));
                    System.out.println("Keepalive resent!");
                } else if (packet instanceof ServerLoginSuccessPacket) {
                    session.setConnectionState(ConnectionState.PLAY);
                    System.out.println("Success received!");
                    uuid.set(((ServerLoginSuccessPacket) packet).getUuid());
                } else if (packet instanceof ServerLoginEncryptionRequestPacket) {
                    System.out.println("Encryption received");
                    final boolean a = PremiumUtil.parseEncryption(session, (ServerLoginEncryptionRequestPacket) packet, premiumSessionsession.getAccessToken(), premiumSessionsession.getSelectedProfileUID(), Proxy.NO_PROXY);
                    System.out.println("Encryption status: " + a);
                }
            }
        });

        client.connect("192.168.100.133", 25565, Proxy.NO_PROXY);
    }

}
