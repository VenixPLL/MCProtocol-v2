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
import me.dickmeister.mcprotocol.minecraft.Difficulty;
import me.dickmeister.mcprotocol.minecraft.Dimension;
import me.dickmeister.mcprotocol.minecraft.Gamemode;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.CLIENTBOUND, connectionState = ConnectionState.PLAY)
public class ServerJoinGamePacket extends Packet {
    private int entityId;
    private Gamemode gamemode;
    private Dimension dimension;
    private Difficulty difficulty;
    private int maxPlayers;
    private String levelType;
    private boolean reduced_debug;

    {
        this.setId(0x23);
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeInt(entityId);
        out.writeByte(gamemode.getId());

        out.writeInt(dimension.getId());

        out.writeByte(difficulty.getId());
        out.writeByte(maxPlayers);
        out.writeString(levelType);
        out.writeBoolean(reduced_debug);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        this.entityId = in.readInt();
        this.gamemode = Gamemode.getById(in.readUnsignedByte());

        dimension = Dimension.getById(in.readInt());

        difficulty = Difficulty.getById(in.readUnsignedByte());
        maxPlayers = in.readUnsignedByte();
        levelType = in.readStringFromBuffer(32767);
        reduced_debug = in.readBoolean();
    }
}
