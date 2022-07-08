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
import me.dickmeister.mcprotocol.minecraft.item.ItemStack;
import me.dickmeister.mcprotocol.minecraft.world.vec.Position;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.SERVERBOUND)
public class ClientPlayerBlockPlacePacket extends Packet {

    {
        this.setId(0x1F);
    }

    private Position blockPos;
    private byte face;
    private float cursorX,cursorY,cursorZ;

    public ClientPlayerBlockPlacePacket(Position blockPos, byte face, float cX, float cY, float cZ) {
        this.blockPos = blockPos;
        this.face = face;
        this.cursorX = cX;
        this.cursorY = cY;
        this.cursorZ = cZ;
    }

    private int hand;

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writePosition(blockPos);
        packetBuffer.writeVarIntToBuffer(face);
        packetBuffer.writeVarIntToBuffer(hand);
        packetBuffer.writeFloat(cursorX);
        packetBuffer.writeFloat(cursorY);
        packetBuffer.writeFloat(cursorZ);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        blockPos = packetBuffer.readPosition();
        face = (byte) packetBuffer.readVarIntFromBuffer();
        hand = packetBuffer.readVarIntFromBuffer();
        cursorX = packetBuffer.readFloat();
        cursorY = packetBuffer.readFloat();
        cursorZ = packetBuffer.readFloat();
    }
}

