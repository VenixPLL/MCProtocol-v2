package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

/**
 * @author Unix
 * @since 10.01.2021
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServerEntityHeadLookPacket extends Packet {
    {
        this.setId(0x36);
    }

    private int entityId;
    private float headYaw;

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        entityId = buffer.readVarIntFromBuffer();
        headYaw = buffer.readByte() * 360 / 256.0f;
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeVarIntToBuffer(entityId);
        buffer.writeByte((byte) (headYaw * 256 / 360));
    }
}