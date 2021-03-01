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
public class ServerEntityLookPacket extends Packet
{
    {
        this.setId(0x28);
    }

    private int entityId;
    private float yaw, pitch;
    private boolean onGround;

    @Override
    public void read(PacketBuffer buffer) throws Exception {}

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeVarIntToBuffer(entityId);
        buffer.writeByte((byte) (yaw * 256 / 360));
        buffer.writeByte((byte) (pitch * 256 / 360));
        buffer.writeBoolean(onGround);
    }
}