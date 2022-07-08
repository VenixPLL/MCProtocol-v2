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

package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.world.vec.Position;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

/**
 * @author Unix
 * @since 10.01.2021
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServerEntityPositionPacket extends Packet
{
    {
        this.setId(0x26);
    }

    private int entityId;
    private Position position;
    private boolean onGround;

    public ServerEntityPositionPacket(int entityId, Position position, Position prevPosition, boolean onGround) {
        this.entityId = entityId;
        this.position = new Position((position.getX() * 32 - prevPosition.getX() * 32) * 128, (position.getY() * 32 - prevPosition.getY() * 32) * 128, (position.getZ() * 32 - prevPosition.getZ() * 32) * 128);
        this.onGround = onGround;
    }


    @Override
    public void read(PacketBuffer buffer) throws Exception {}

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeVarIntToBuffer(entityId);
        buffer.writeShort((int) (this.position.getX()));
        buffer.writeShort((int) (this.position.getY()));
        buffer.writeShort((int) (this.position.getZ()));
        buffer.writeBoolean(onGround);
    }
}