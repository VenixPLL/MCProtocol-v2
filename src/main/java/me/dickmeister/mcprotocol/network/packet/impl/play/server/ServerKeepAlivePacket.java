package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.CLIENTBOUND,connectionState = ConnectionState.PLAY)
public class ServerKeepAlivePacket extends Packet
{
    {
        this.setId(0x1F);
    }

    private long pingId;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeLong(this.pingId);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        this.pingId = packetBuffer.readLong();
    }
}
