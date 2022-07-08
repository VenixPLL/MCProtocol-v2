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

import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY, packetDirection = PacketDirection.SERVERBOUND)
public class ClientPluginMessagePacket extends Packet {

    {
        this.setId(0x09);
    }

    private String channel;
    private PacketBuffer buffer;

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        this.channel = buffer.readStringFromBuffer(32767);
        final int i = buffer.readableBytes();

        if (i >= 0 && i <= 32767)
            this.buffer = new PacketBuffer(buffer.readBytes(i));
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeString(this.channel);
        buffer.writeBytes(Unpooled.copiedBuffer(this.buffer));
    }
}