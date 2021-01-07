package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.container.WindowAction;
import me.dickmeister.mcprotocol.minecraft.item.ItemStack;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.SERVERBOUND)
public class ClientWindowActionPacket extends Packet
{
    {
        this.setId(0x07);
    }

    private int windowId;
    private short slot;
    private int button;
    private WindowAction mode;
    private int action;
    private ItemStack item;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeByte(windowId);
        packetBuffer.writeShort(slot);
        packetBuffer.writeByte(button);
        packetBuffer.writeShort(action);
        packetBuffer.writeVarIntToBuffer(mode.getId());
        packetBuffer.writeItemStackToBuffer(item);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        windowId = packetBuffer.readByte();
        slot = packetBuffer.readShort();
        button = packetBuffer.readByte();
        action = packetBuffer.readShort();
        mode = WindowAction.getActionById(packetBuffer.readVarIntFromBuffer());
        item = packetBuffer.readItemStackFromBuffer();
    }
}
