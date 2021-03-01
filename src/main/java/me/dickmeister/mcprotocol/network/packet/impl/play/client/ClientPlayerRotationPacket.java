package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

/**
 * @author Unix
 * @since 10.01.2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY, packetDirection = PacketDirection.SERVERBOUND)
public class ClientPlayerRotationPacket extends Packet {
    {
        this.setId(0x0F);
    }

    private float yaw, pitch;
    private boolean onGround;

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        yaw = buffer.readFloat();
        pitch = buffer.readFloat();
        onGround = buffer.readBoolean();
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeFloat(yaw);
        buffer.writeFloat(pitch);
        buffer.writeBoolean(onGround);
    }
}