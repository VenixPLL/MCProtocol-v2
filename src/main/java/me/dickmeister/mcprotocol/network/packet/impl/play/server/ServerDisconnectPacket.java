package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

@Data
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.CLIENTBOUND)
public class ServerDisconnectPacket extends Packet {
    {
        this.setId(0x1A);
    }

    private BaseComponent[] reason;

    public ServerDisconnectPacket(String reason){
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
