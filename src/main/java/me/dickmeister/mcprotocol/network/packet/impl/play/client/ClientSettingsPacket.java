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
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Packet.PacketInfo(packetDirection = PacketDirection.SERVERBOUND,connectionState = ConnectionState.PLAY)
public class ClientSettingsPacket extends Packet
{
    {
        this.setId(0x04);
    }

    private String locale;
    private int viewDistance;
    private int chatMode;
    private boolean chatColors;
    private short skinParts;
    private int mainHand;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeString(locale);
        packetBuffer.writeByte(viewDistance);
        packetBuffer.writeVarIntToBuffer(chatMode);
        packetBuffer.writeBoolean(chatColors);
        packetBuffer.writeByte(skinParts);
        packetBuffer.writeVarIntToBuffer(mainHand);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        locale = packetBuffer.readStringFromBuffer(128);
        viewDistance = packetBuffer.readByte();
        chatMode = packetBuffer.readVarIntFromBuffer();
        chatColors = packetBuffer.readBoolean();
        skinParts = packetBuffer.readUnsignedByte();
        mainHand = packetBuffer.readVarIntFromBuffer();
    }
}
