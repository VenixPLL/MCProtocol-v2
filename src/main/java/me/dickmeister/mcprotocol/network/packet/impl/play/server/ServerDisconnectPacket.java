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

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

@Getter
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY, packetDirection = PacketDirection.CLIENTBOUND)
public class ServerDisconnectPacket extends Packet {
    private BaseComponent[] reason;

    {
        this.setId(0x1A);
    }

    public ServerDisconnectPacket(String reason) {
        this.reason = new BaseComponent[]{new TextComponent(reason)};
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeString(ComponentSerializer.toString(reason));
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        reason = ComponentSerializer.parse(in.readStringFromBuffer(32767));
    }

    public String getReason() {
        return reason.length > 0 ? reason[0].toPlainText() : "unknown reason";
    }
}
