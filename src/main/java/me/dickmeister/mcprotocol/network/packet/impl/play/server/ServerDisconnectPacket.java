package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

@Getter
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY, packetDirection = PacketDirection.CLIENTBOUND)
public class ServerDisconnectPacket extends Packet {
    private BaseComponent[] reason;

    {
        this.setId(0x1A);
    }

    public ServerDisconnectPacket(String reason) {
        this.reason = new BaseComponent[]{new TextComponent(reason)};
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeString(ComponentSerializer.toString(reason));
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        reason = ComponentSerializer.parse(in.readStringFromBuffer(32767));
    }

    public String getReason() {
        return reason.length > 0 ? reason[0].toPlainText() : "unknown reason";
    }
}
