package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.world.vec.Position;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.SERVERBOUND)
public class ClientPlayerPosLookPacket extends Packet
{
    {
        this.setId(0x0E);
    }

    private Position position;
    private float yaw, pitch;
    private boolean onGround;

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeDouble(position.getX());
        out.writeDouble(position.getY());
        out.writeDouble(position.getZ());
        out.writeFloat(yaw);
        out.writeFloat(pitch);
        out.writeBoolean(onGround);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        final double x = in.readDouble();
        final double y = in.readDouble();
        final double z = in.readDouble();
        position = new Position(x, y ,z);
        yaw = in.readFloat();
        pitch = in.readFloat();
        onGround = in.readBoolean();
    }
}
