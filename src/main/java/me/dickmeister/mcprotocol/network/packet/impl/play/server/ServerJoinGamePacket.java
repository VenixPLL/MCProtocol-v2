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
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.CLIENTBOUND, connectionState = ConnectionState.PLAY)
public class ServerJoinGamePacket extends Packet {
    private int entityId;
    private Gamemode gamemode;
    private Dimension dimension;
    private Difficulty difficulty;
    private int maxPlayers;
    private String levelType;
    private boolean reduced_debug;

    {
        this.setId(0x23);
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeInt(entityId);
        out.writeByte(gamemode.getId());

        out.writeInt(dimension.getId());

        out.writeByte(difficulty.getId());
        out.writeByte(maxPlayers);
        out.writeString(levelType);
        out.writeBoolean(reduced_debug);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        this.entityId = in.readInt();
        this.gamemode = Gamemode.getById(in.readUnsignedByte());

        dimension = Dimension.getById(in.readInt());

        difficulty = Difficulty.getById(in.readUnsignedByte());
        maxPlayers = in.readUnsignedByte();
        levelType = in.readStringFromBuffer(32767);
        reduced_debug = in.readBoolean();
    }
}
