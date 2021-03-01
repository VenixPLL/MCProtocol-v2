package me.dickmeister.mcprotocol.network.packet.impl.play.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.entity.player.PlayerState;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

/**
 * @author Unix
 * @since 10.01.2021
 */

@Data
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY, packetDirection = PacketDirection.SERVERBOUND)
public class ClientPlayerStatePacket extends Packet
{
    {
        this.setId(0x15);
    }

    private int entityId;
    private PlayerState state;
    private int jumpBoost;

    public ClientPlayerStatePacket(int entityId, PlayerState state) {
        this(entityId, state, 0);
    }

    public ClientPlayerStatePacket(int entityId, PlayerState state, int jumpBoost) {
        this.entityId = entityId;
        this.state = state;
        this.jumpBoost = jumpBoost;
    }

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        entityId = buffer.readVarIntFromBuffer();
        state = PlayerState.of(buffer.readVarIntFromBuffer());
        jumpBoost = buffer.readVarIntFromBuffer();
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeVarIntToBuffer(entityId);
        buffer.writeVarIntToBuffer(state.getId());
        buffer.writeVarIntToBuffer(jumpBoost);
    }
}