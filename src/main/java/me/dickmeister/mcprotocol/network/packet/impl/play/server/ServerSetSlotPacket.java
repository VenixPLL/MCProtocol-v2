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
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.item.ItemStack;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

/**
 * @author Unix
 * @since 09.01.2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.CLIENTBOUND,connectionState = ConnectionState.PLAY)
public class ServerSetSlotPacket extends Packet
{
    {
        this.setId(0x16);
    }

    private int windowId, slot;
    private ItemStack itemStack;

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        windowId = buffer.readUnsignedByte();
        slot = buffer.readShort();
        itemStack = buffer.readItemStackFromBuffer();
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeByte(windowId);
        buffer.writeShort(slot);
        buffer.writeItemStackToBuffer(itemStack);
    }
}