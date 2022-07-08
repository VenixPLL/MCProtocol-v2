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
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.container.WindowAction;
import me.dickmeister.mcprotocol.minecraft.item.ItemStack;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.SERVERBOUND)
public class ClientWindowActionPacket extends Packet
{
    {
        this.setId(0x07);
    }

    private int windowId;
    private short slot;
    private int button;
    private WindowAction mode;
    private int action;
    private ItemStack item;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeByte(windowId);
        packetBuffer.writeShort(slot);
        packetBuffer.writeByte(button);
        packetBuffer.writeShort(action);
        packetBuffer.writeVarIntToBuffer(mode.getId());
        packetBuffer.writeItemStackToBuffer(item);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        windowId = packetBuffer.readByte();
        slot = packetBuffer.readShort();
        button = packetBuffer.readByte();
        action = packetBuffer.readShort();
        mode = WindowAction.getActionById(packetBuffer.readVarIntFromBuffer());
        item = packetBuffer.readItemStackFromBuffer();
    }
}
