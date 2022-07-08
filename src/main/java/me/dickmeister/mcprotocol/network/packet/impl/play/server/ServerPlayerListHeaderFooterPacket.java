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
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.CLIENTBOUND)
public class ServerPlayerListHeaderFooterPacket extends Packet
{
    {
        this.setId(0x4A);
    }

    private BaseComponent[] header, footer;

    public ServerPlayerListHeaderFooterPacket(String header, String footer){
        this.header = new BaseComponent[]{new TextComponent(header)};
        this.footer = new BaseComponent[]{new TextComponent(footer)};
    }

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeString(ComponentSerializer.toString(header));
        packetBuffer.writeString(ComponentSerializer.toString(footer));
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        header = ComponentSerializer.parse(packetBuffer.readStringFromBuffer(32767));
        footer = ComponentSerializer.parse(packetBuffer.readStringFromBuffer(32767));
    }
}
