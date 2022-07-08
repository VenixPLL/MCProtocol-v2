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

package me.dickmeister.mcprotocol.network.packet.impl.status.client;

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
@Packet.PacketInfo(packetDirection = PacketDirection.SERVERBOUND, connectionState = ConnectionState.STATUS)
public class ClientStatusPingPacket extends Packet {

    private long time;

    {
        this.setId(0x01);
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeLong(time);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        try {
            if (!in.isReadable()) {
                throw new Exception("EMPTY_PACKET_PING");
            }

            time = in.readLong();

            if (in.isReadable()) {
                throw new Exception("MALFORMED_PING");
            }
        } catch (final IndexOutOfBoundsException ex) {
            throw new Exception("INVALID_PING");
        }
    }

}
