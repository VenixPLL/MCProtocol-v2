package me.dickmeister.mcprotocol.network.packet.impl.handshake;

import lombok.*;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.HANDSHAKE, packetDirection = PacketDirection.SERVERBOUND)
@ToString
public class HandshakePacket extends Packet {

    private int protocolVersion;
    private String host;
    private int port;
    private int nextState;

    {
        this.setId(0x00);
    }

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        this.protocolVersion = buffer.readVarIntFromBuffer();
        this.host = buffer.readStringFromBuffer(64);
        this.port = buffer.readShort();
        this.nextState = buffer.readVarIntFromBuffer();
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeVarIntToBuffer(this.protocolVersion);
        buffer.writeString(this.host);
        buffer.writeShort(this.port);
        buffer.writeVarIntToBuffer(this.nextState);
    }
}
