/*
 * MCProtocol-v2
 * Copyright (C) 2022.  VenixPLL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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