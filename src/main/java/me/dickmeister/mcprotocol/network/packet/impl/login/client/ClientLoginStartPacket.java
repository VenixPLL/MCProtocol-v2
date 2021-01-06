package me.dickmeister.mcprotocol.network.packet.impl.login.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.LOGIN,packetDirection = PacketDirection.SERVERBOUND)
public class ClientLoginStartPacket extends Packet {

    {
        this.setId(0x00);
    }

    private String username;

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        this.username = buffer.readStringFromBuffer(16);
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeString(this.username);
    }
}
