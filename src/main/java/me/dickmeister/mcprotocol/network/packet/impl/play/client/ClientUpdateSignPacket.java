package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.world.vec.Position;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

/**
 * @author hp888 on 08.09.2020.
 * Edited by dickmeister on 07.01.2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.SERVERBOUND)
public final class ClientUpdateSignPacket extends Packet
{
    {
        this.setId(0x1C);
    }

    private Position position;
    private String[] components;//BaseComponent[][] components;

    @Override
    public void read(PacketBuffer in) throws Exception {
        this.position = in.readPosition();
        this.components = new String[4];//= new BaseComponent[4][];
        for (int i = 0; i < 4; ++i) {
            this.components[i] = in.readStringFromBuffer(384);//ComponentSerializer.parse(in.readStringFromBuffer(384));
        }
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writePosition(this.position);
        for (int i = 0; i < 4; ++i) {
            out.writeString(components[i]);//ComponentSerializer.toString(this.components[i]));
        }
    }
}
