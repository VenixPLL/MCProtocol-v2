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
import me.dickmeister.mcprotocol.minecraft.world.vec.Position;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.util.StringUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Unix
 * @since 19.08.2020
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY, packetDirection = PacketDirection.CLIENTBOUND)
public class ServerUpdateSignPacket extends Packet
{
    {
        this.setId(0x1C);
    }

    private Position position;
    private List<BaseComponent[]> lines;

    public ServerUpdateSignPacket(Position position, String[] lines) {
        this.position = position;
        this.lines = new ArrayList<>();
        for (String line : lines) {
            this.lines.add(new BaseComponent[] { new TextComponent(StringUtil.fixColor(line)) });
        }
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writePosition(position);
        for (BaseComponent[] line : lines)
            out.writeString(ComponentSerializer.toString(line));
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        position = in.readPosition();
        lines = new ArrayList<>();

        for (int i = 0; i < 4; ++i)
            lines.add(ComponentSerializer.parse(in.readStringFromBuffer(Short.MAX_VALUE)));
    }
}
