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
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.CLIENTBOUND)
public class ServerPlayerAbilitiesPacket extends Packet {

    {
        this.setId(0x2C);
    }

    private boolean damage, flying, allowFlying, creative;
    private float flySpeed, walkSpeed;

    @Override
    public void write(PacketBuffer out) throws Exception {
        byte flags = 0;
        if (damage)
            flags = (byte) (flags | 0x1);

        if (flying)
            flags = (byte) (flags | 0x2);

        if (allowFlying)
            flags = (byte) (flags | 0x4);

        if (creative)
            flags = (byte) (flags | 0x8);

        out.writeByte(flags);
        out.writeFloat(flySpeed);
        out.writeFloat(walkSpeed);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        final byte flags = in.readByte();
        damage = ((flags & 0x1) > 0);
        flying = ((flags & 0x2) > 0);
        allowFlying = ((flags & 0x4) > 0);
        creative = ((flags & 0x8) > 0);
        flySpeed = in.readFloat();
        walkSpeed = in.readFloat();
    }
}
