package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.item.ItemStack;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.SERVERBOUND)
public class ClientCreativeInventoryActionPacket extends Packet
{
    {
        this.setId(0x1B);
    }

    private int slot;
    private ItemStack item;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeShort(slot);
        packetBuffer.writeItemStackToBuffer(item);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        slot = packetBuffer.readShort();
        item = packetBuffer.readItemStackFromBuffer();
    }
}
