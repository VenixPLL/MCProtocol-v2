
import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.listeners.SessionListener;
import me.dickmeister.mcprotocol.minecraft.status.PlayerInfo;
import me.dickmeister.mcprotocol.minecraft.status.ServerStatusInfo;
import me.dickmeister.mcprotocol.minecraft.status.VersionInfo;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.network.packet.impl.handshake.HandshakePacket;
import me.dickmeister.mcprotocol.network.packet.impl.status.client.ClientStatusPingPacket;
import me.dickmeister.mcprotocol.network.packet.impl.status.client.ClientStatusRequestPacket;
import me.dickmeister.mcprotocol.network.packet.impl.status.server.ServerStatusPongPacket;
import me.dickmeister.mcprotocol.network.packet.impl.status.server.ServerStatusResponsePacket;
import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;
import me.dickmeister.mcprotocol.server.impl.MinecraftServer;
import me.dickmeister.mcprotocol.util.LogUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class mainTest {

    public static void main(String[] args){
        MCProtocol.DEBUG = true;
        final PacketRegistry packetRegistry = new MCProtocol().initialize();
        final MinecraftServer server = new MinecraftServer(packetRegistry);
        server.setSessionListener(new SessionListener() {
            @Override
            public void connected(Session session) {
                LogUtil.log(this.getClass(),"%s Connected",session.getChannel().remoteAddress().toString());
            }

            @Override
            public void disconnected(Session session) {
                LogUtil.log(this.getClass(),"%s Disconnected",session.getChannel().remoteAddress().toString());
            }

            @Override
            public void onPacketReceived(Session session, Packet packet) {
                LogUtil.log(this.getClass(),"%s Received %s",session.getChannel().remoteAddress().toString(),packet.getClass().getSimpleName());
                if(packet instanceof HandshakePacket){
                    session.setConnectionState(ConnectionState.valueOf(((HandshakePacket) packet).getNextState()));
                }else if(packet instanceof ClientStatusRequestPacket){
                    final VersionInfo versionInfo = new VersionInfo("Nie pamietam",999);
                    final PlayerInfo playerInfo = new PlayerInfo(2,250);
                    final ServerStatusInfo statusInfo = new ServerStatusInfo(versionInfo,playerInfo,new BaseComponent[]{new TextComponent("&cRuhanie?\n&cRuhanienko")},null);
                    session.sendPacket(new ServerStatusResponsePacket(statusInfo));

                    session.disconnect("");
                }else if(packet instanceof ClientStatusPingPacket){
                    session.sendPacket(new ServerStatusPongPacket(System.currentTimeMillis()));
                    session.disconnect("");
                }
            }

            @Override
            public void exceptionCaught(Session session, Throwable t) {
                t.printStackTrace();
            }
        });

        server.bind(25565,server.getSessionListener());
    }

}
