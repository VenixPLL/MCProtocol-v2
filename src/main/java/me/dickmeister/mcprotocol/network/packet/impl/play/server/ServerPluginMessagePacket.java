package me.dickmeister.mcprotocol.network.packet.impl.play.server;


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
@Packet.PacketInfo(packetDirection = PacketDirection.CLIENTBOUND,connectionState = ConnectionState.PLAY)
public class ServerPluginMessagePacket extends Packet
{
    {
        this.setId(0x18);
    }

    private String channel;
    private PacketBuffer buffer;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeString(channel);
        packetBuffer.writeBytes(buffer);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        channel = packetBuffer.readStringFromBuffer(32767);
        final int i = packetBuffer.readableBytes();

        if (i >= 0 && i <= 32767)
            buffer = new PacketBuffer(packetBuffer.readBytes(i));
    }
}
