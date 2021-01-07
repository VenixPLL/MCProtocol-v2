package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.Difficulty;
import me.dickmeister.mcprotocol.minecraft.Dimension;
import me.dickmeister.mcprotocol.minecraft.Gamemode;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.CLIENTBOUND)
public class ServerRespawnPacket extends Packet
{
    {
        this.setId(0x35);
    }

    private Dimension dimension;
    private Difficulty difficulty;
    private Gamemode gamemode;
    private String level_type;

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeInt(dimension.getId());
        out.writeByte(difficulty.getId());
        out.writeByte(gamemode.getId());
        out.writeString(level_type);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        dimension = Dimension.getById(in.readInt());
        difficulty = Difficulty.getById(in.readUnsignedByte());
        gamemode = Gamemode.getById(in.readUnsignedByte());
        level_type = in.readStringFromBuffer(24);
    }
}
