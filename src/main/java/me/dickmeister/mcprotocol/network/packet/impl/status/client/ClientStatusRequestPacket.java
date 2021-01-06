package me.dickmeister.mcprotocol.network.packet.impl.status.client;

import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;


@Packet.PacketInfo(packetDirection = PacketDirection.SERVERBOUND, connectionState = ConnectionState.STATUS)
public class ClientStatusRequestPacket extends Packet {
    {
        this.setId(0x00);
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        if (!in.isReadable()) {
            return;
        }
        throw new Exception();
    }
}
