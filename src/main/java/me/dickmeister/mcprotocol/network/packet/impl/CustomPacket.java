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

package me.dickmeister.mcprotocol.network.packet.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

/**
 * Class designed to replace not implemented Packets.
 */
@AllArgsConstructor
@NoArgsConstructor
public class CustomPacket extends Packet {

    @Setter
    @Getter
    private byte[] data;

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        final int size = buffer.readableBytes();
        this.data = new byte[size];

        buffer.readBytes(this.data);
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeBytes(this.data);
    }
}