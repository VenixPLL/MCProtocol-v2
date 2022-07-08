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

package me.dickmeister.mcprotocol.network.packet;

import lombok.Data;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Data
public abstract class Packet {

    /**
     * Packet unique ID.
     */
    private int id = -1;

    /**
     * Reading from packet.
     *
     * @param buffer PacketBuffer to read from
     * @throws Exception
     */
    public abstract void read(final PacketBuffer buffer) throws Exception;

    /**
     * Writing to packet.
     *
     * @param buffer PacketBuffer to write to
     * @throws Exception
     */
    public abstract void write(final PacketBuffer buffer) throws Exception;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface PacketInfo {
        ConnectionState connectionState();

        PacketDirection packetDirection();
    }
}
