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
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY, packetDirection = PacketDirection.CLIENTBOUND)
public class ServerPlayerPosLookPacket extends Packet {

    private Position pos;
    private float yaw;
    private float pitch;
    private int flags;
    private int teleportID;

    {
        this.setId(0x2F);
    }

    public ServerPlayerPosLookPacket(Position pos, float yaw, float pitch, int flags) {
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = pitch;
        this.flags = flags;
    }


    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeDouble(pos.getX());
        out.writeDouble(pos.getY());
        out.writeDouble(pos.getZ());
        out.writeFloat(yaw);
        out.writeFloat(pitch);
        out.writeByte(flags);

        out.writeVarIntToBuffer(teleportID);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        final double x = in.readDouble();
        final double y = in.readDouble();
        final double z = in.readDouble();
        pos = new Position(x, y, z);
        yaw = in.readFloat();
        pitch = in.readFloat();
        flags = in.readUnsignedByte();

        teleportID = in.readVarIntFromBuffer();
    }
}
