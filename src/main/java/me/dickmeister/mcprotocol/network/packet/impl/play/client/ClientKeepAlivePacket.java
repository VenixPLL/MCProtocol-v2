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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.SERVERBOUND, connectionState = ConnectionState.PLAY)
public class ClientKeepAlivePacket extends Packet {
    private long pingId;

    {
        this.setId(0x0B);
    }

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeLong(this.pingId);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        this.pingId = packetBuffer.readLong();
    }
}
