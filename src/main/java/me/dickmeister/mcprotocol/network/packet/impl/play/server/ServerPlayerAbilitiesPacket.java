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
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY, packetDirection = PacketDirection.CLIENTBOUND)
public class ServerPlayerAbilitiesPacket extends Packet {

    private boolean damage, flying, allowFlying, creative;
    private float flySpeed, walkSpeed;

    {
        this.setId(0x2C);
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        byte flags = 0;
        if (damage)
            flags = (byte) (flags | 0x1);

        if (flying)
            flags = (byte) (flags | 0x2);

        if (allowFlying)
            flags = (byte) (flags | 0x4);

        if (creative)
            flags = (byte) (flags | 0x8);

        out.writeByte(flags);
        out.writeFloat(flySpeed);
        out.writeFloat(walkSpeed);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        final byte flags = in.readByte();
        damage = ((flags & 0x1) > 0);
        flying = ((flags & 0x2) > 0);
        allowFlying = ((flags & 0x4) > 0);
        creative = ((flags & 0x8) > 0);
        flySpeed = in.readFloat();
        walkSpeed = in.readFloat();
    }
}
