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

package me.dickmeister.mcprotocol.network.packet.impl.login.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.LOGIN, packetDirection = PacketDirection.CLIENTBOUND)
public class ServerLoginSuccessPacket extends Packet {

    private UUID uuid;
    private String username;

    {
        this.setId(0x02);
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeString(uuid.toString());
        out.writeString(username);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        uuid = UUID.fromString(in.readStringFromBuffer(86));
        username = in.readStringFromBuffer(32);
    }
}
