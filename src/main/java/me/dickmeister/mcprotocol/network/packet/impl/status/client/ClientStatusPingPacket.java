package me.dickmeister.mcprotocol.network.packet.impl.status.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.SERVERBOUND,connectionState = ConnectionState.STATUS)
public class ClientStatusPingPacket extends Packet{

    {
        this.setId(0x01);
    }

    private long time;

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeLong(time);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        try {
            if (!in.isReadable()) {
                throw new Exception("EMPTY_PACKET_PING");
            }

            time = in.readLong();

            if (in.isReadable()) {
                throw new Exception("MALFORMED_PING");
            }
        } catch (final IndexOutOfBoundsException ex) {
            throw new Exception("INVALID_PING");
        }
    }

}
