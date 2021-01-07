package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Packet.PacketInfo(packetDirection = PacketDirection.SERVERBOUND,connectionState = ConnectionState.PLAY)
public class ClientSettingsPacket extends Packet
{
    {
        this.setId(0x04);
    }

    private String locale;
    private int viewDistance;
    private int chatMode;
    private boolean chatColors;
    private short skinParts;
    private int mainHand;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeString(locale);
        packetBuffer.writeByte(viewDistance);
        packetBuffer.writeVarIntToBuffer(chatMode);
        packetBuffer.writeBoolean(chatColors);
        packetBuffer.writeByte(skinParts);
        packetBuffer.writeVarIntToBuffer(mainHand);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        locale = packetBuffer.readStringFromBuffer(128);
        viewDistance = packetBuffer.readByte();
        chatMode = packetBuffer.readVarIntFromBuffer();
        chatColors = packetBuffer.readBoolean();
        skinParts = packetBuffer.readUnsignedByte();
        mainHand = packetBuffer.readVarIntFromBuffer();
    }
}
