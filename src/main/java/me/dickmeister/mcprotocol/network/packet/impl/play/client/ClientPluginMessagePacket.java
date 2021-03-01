package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY, packetDirection = PacketDirection.SERVERBOUND)
public class ClientPluginMessagePacket extends Packet {

    {
        this.setId(0x09);
    }

    private String channel;
    private PacketBuffer buffer;

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        this.channel = buffer.readStringFromBuffer(32767);
        final int i = buffer.readableBytes();

        if (i >= 0 && i <= 32767)
            this.buffer = new PacketBuffer(buffer.readBytes(i));
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeString(this.channel);
        buffer.writeBytes(Unpooled.copiedBuffer(this.buffer));
    }
}