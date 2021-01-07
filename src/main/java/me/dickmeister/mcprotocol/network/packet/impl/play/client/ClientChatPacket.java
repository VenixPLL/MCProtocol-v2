package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.SERVERBOUND,connectionState = ConnectionState.PLAY)
public class ClientChatPacket extends Packet
{
    {
        this.setId(0x02);
    }

    private String message;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeString(message);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        message = packetBuffer.readStringFromBuffer(100);
    }
}
