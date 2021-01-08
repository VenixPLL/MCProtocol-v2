package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import io.netty.buffer.Unpooled;
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
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.SERVERBOUND)
public class ClientPluginMessagePacket extends Packet
{
    {
        this.setId(0x09);
    }

    private String channel;
    private PacketBuffer buffer;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeString(channel);
        packetBuffer.writeBytes(Unpooled.copiedBuffer(buffer));
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        channel = packetBuffer.readStringFromBuffer(32767);
        final int i = packetBuffer.readableBytes();

        if (i >= 0 && i <= 32767)
            buffer = new PacketBuffer(packetBuffer.readBytes(i));
    }
}
