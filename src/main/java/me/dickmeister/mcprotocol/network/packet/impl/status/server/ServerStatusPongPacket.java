package me.dickmeister.mcprotocol.network.packet.impl.status.server;

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
@Packet.PacketInfo(packetDirection = PacketDirection.CLIENTBOUND, connectionState = ConnectionState.STATUS)
public class ServerStatusPongPacket extends Packet {

    private long time;

    {
        this.setId(0x01);
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeLong(time);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        time = in.readLong();
    }
}
