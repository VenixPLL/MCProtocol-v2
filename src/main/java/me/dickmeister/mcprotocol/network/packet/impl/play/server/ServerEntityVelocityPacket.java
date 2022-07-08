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
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

/**
 * @author Unix
 * @since 10.01.2021
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServerEntityVelocityPacket extends Packet
{
    {
        this.setId(0x3E);
    }

    private int entityId;
    private double motionX, motionY, motionZ;

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        entityId = buffer.readVarIntFromBuffer();
        motionX = buffer.readShort() / 8000D;
        motionY = buffer.readShort() / 8000D;
        motionZ = buffer.readShort() / 8000D;
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeVarIntToBuffer(entityId);
        buffer.writeShort((int) (motionX * 8000));
        buffer.writeShort((int) (motionY * 8000));
        buffer.writeShort((int) (motionZ * 8000));
    }
}