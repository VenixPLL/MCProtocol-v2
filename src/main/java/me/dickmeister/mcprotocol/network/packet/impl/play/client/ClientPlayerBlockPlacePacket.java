package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.item.ItemStack;
import me.dickmeister.mcprotocol.minecraft.world.vec.Position;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.SERVERBOUND)
public class ClientPlayerBlockPlacePacket extends Packet {

    {
        this.setId(0x1F);
    }

    private Position blockPos;
    private byte face;
    private float cursorX,cursorY,cursorZ;

    public ClientPlayerBlockPlacePacket(Position blockPos, byte face, float cX, float cY, float cZ) {
        this.blockPos = blockPos;
        this.face = face;
        this.cursorX = cX;
        this.cursorY = cY;
        this.cursorZ = cZ;
    }

    private int hand;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writePosition(blockPos);
        packetBuffer.writeVarIntToBuffer(face);
        packetBuffer.writeVarIntToBuffer(hand);
        packetBuffer.writeFloat(cursorX);
        packetBuffer.writeFloat(cursorY);
        packetBuffer.writeFloat(cursorZ);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        blockPos = packetBuffer.readPosition();
        face = (byte) packetBuffer.readVarIntFromBuffer();
        hand = packetBuffer.readVarIntFromBuffer();
        cursorX = packetBuffer.readFloat();
        cursorY = packetBuffer.readFloat();
        cursorZ = packetBuffer.readFloat();
    }
}

