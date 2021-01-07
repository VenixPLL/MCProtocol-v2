package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.SERVERBOUND, connectionState = ConnectionState.PLAY)
public class ClientKeepAlivePacket extends Packet {
    private long pingId;

    {
        this.setId(0x0B);
    }

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeLong(this.pingId);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        this.pingId = packetBuffer.readLong();
    }
}
