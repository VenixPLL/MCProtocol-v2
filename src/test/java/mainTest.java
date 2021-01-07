
import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.listeners.SessionListener;
import me.dickmeister.mcprotocol.minecraft.Difficulty;
import me.dickmeister.mcprotocol.minecraft.Dimension;
import me.dickmeister.mcprotocol.minecraft.Gamemode;
import me.dickmeister.mcprotocol.minecraft.status.PlayerInfo;
import me.dickmeister.mcprotocol.minecraft.status.ServerStatusInfo;
import me.dickmeister.mcprotocol.minecraft.status.VersionInfo;
import me.dickmeister.mcprotocol.minecraft.world.vec.Position;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.network.packet.impl.handshake.HandshakePacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.client.ClientLoginStartPacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginSetCompressionPacket;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginSuccessPacket;
import me.dickmeister.mcprotocol.network.packet.impl.play.client.ClientKeepAlivePacket;
import me.dickmeister.mcprotocol.network.packet.impl.play.server.*;
import me.dickmeister.mcprotocol.network.packet.impl.status.client.ClientStatusPingPacket;
import me.dickmeister.mcprotocol.network.packet.impl.status.client.ClientStatusRequestPacket;
import me.dickmeister.mcprotocol.network.packet.impl.status.server.ServerStatusPongPacket;
import me.dickmeister.mcprotocol.network.packet.impl.status.server.ServerStatusResponsePacket;
import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;
import me.dickmeister.mcprotocol.server.impl.MinecraftServer;
import me.dickmeister.mcprotocol.util.LogUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class mainTest {

    public static void main(String[] args){
        final long sT = System.currentTimeMillis();

        MCProtocol.DEBUG = true;
        final PacketRegistry packetRegistry = new MCProtocol().initialize(true);
        final MinecraftServer server = new MinecraftServer(packetRegistry);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> server.getSessionList().forEach(s -> {
            if(s.getConnectionState() == ConnectionState.PLAY) s.sendPacket(new ServerKeepAlivePacket(System.currentTimeMillis()));
        }),3L,3L, TimeUnit.SECONDS);

        server.setSessionListener(new SessionListener() {
            @Override
            public void connected(Session session) {
                LogUtil.log(this.getClass(),"%s Connected",session.getChannel().remoteAddress().toString());
                session.enableViaVersion(false);
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
                }else if(packet instanceof ClientLoginStartPacket){
                    final String username = ((ClientLoginStartPacket) packet).getUsername();

                    session.sendPacket(new ServerLoginSetCompressionPacket(256));
                    session.setCompressionThreshold(256);

                    session.sendPacket(new ServerLoginSuccessPacket(UUID.randomUUID(),((ClientLoginStartPacket) packet).getUsername()));
                    session.setConnectionState(ConnectionState.PLAY);
                    System.out.println(username);

                    session.sendPacket(new ServerJoinGamePacket(1, Gamemode.ADVENTURE, Dimension.OVERWORLD, Difficulty.PEACEFULL,1,"default_1_1",false));
                    session.sendPacket(new ServerSpawnPositionPacket(new Position(0,70,0)));
                    session.sendPacket(new ServerPlayerAbilitiesPacket(false,false,false,false,0.1f,0.1f));
                    session.sendPacket(new ServerPlayerPosLookPacket(new Position(0,70,0),180,0,0));
                }
            }

            @Override
            public void exceptionCaught(Session session, Throwable t) {
                t.printStackTrace();
            }
        });

        server.bind(25565,server.getSessionListener());
        final long eT = System.currentTimeMillis() - sT;
        System.out.println("Time: " + eT);
    }

}
