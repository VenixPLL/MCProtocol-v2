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
import me.dickmeister.mcprotocol.minecraft.world.vec.Position;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

/**
 * @author hp888 on 08.09.2020.
 * Edited by dickmeister on 07.01.2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.SERVERBOUND)
public final class ClientUpdateSignPacket extends Packet
{
    {
        this.setId(0x1C);
    }

    private Position position;
    private String[] components;//BaseComponent[][] components;

    @Override
    public void read(PacketBuffer in) throws Exception {
        this.position = in.readPosition();
        this.components = new String[4];//= new BaseComponent[4][];
        for (int i = 0; i < 4; ++i) {
            this.components[i] = in.readStringFromBuffer(384);//ComponentSerializer.parse(in.readStringFromBuffer(384));
        }
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writePosition(this.position);
        for (int i = 0; i < 4; ++i) {
            out.writeString(components[i]);//ComponentSerializer.toString(this.components[i]));
        }
    }
}
