package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.SERVERBOUND,connectionState = ConnectionState.PLAY)
public class ClientPlayerPacket extends Packet
{
    {
        this.setId(0x0C);
    }

    private boolean onGround;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeBoolean(onGround);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        onGround = packetBuffer.readBoolean();
    }
}
