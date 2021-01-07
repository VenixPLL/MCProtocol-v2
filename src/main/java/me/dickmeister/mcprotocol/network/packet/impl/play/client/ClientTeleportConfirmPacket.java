package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientTeleportConfirmPacket extends Packet
{
    {
        this.setId(0x00);
    }

    private int teleportID;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeVarIntToBuffer(teleportID);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        teleportID = packetBuffer.readVarIntFromBuffer();
    }
}
