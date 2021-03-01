package me.dickmeister.mcprotocol.network.packet.impl.login.server;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.CLIENTBOUND, connectionState = ConnectionState.LOGIN)
public class ServerLoginDisconnectPacket extends Packet {

    private BaseComponent[] reason;

    {
        this.setId(0x00);
    }

    public ServerLoginDisconnectPacket(final String message) {
        reason = new BaseComponent[]{new TextComponent(message)};
    }

    public String getReason() {
        return reason.length > 0 ? reason[0].toPlainText() : "unknown reason";
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeString(ComponentSerializer.toString(reason));
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        reason = ComponentSerializer.parse(in.readStringFromBuffer(Short.MAX_VALUE));
    }
}
