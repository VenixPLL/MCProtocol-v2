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
@AllArgsConstructor
@NoArgsConstructor
public class ServerEntityVelocityPacket extends Packet
{
    {
        this.setId(0x3E);
    }

    private int entityId;
    private double motionX, motionY, motionZ;

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        entityId = buffer.readVarIntFromBuffer();
        motionX = buffer.readShort() / 8000D;
        motionY = buffer.readShort() / 8000D;
        motionZ = buffer.readShort() / 8000D;
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeVarIntToBuffer(entityId);
        buffer.writeShort((int) (motionX * 8000));
        buffer.writeShort((int) (motionY * 8000));
        buffer.writeShort((int) (motionZ * 8000));
    }
}