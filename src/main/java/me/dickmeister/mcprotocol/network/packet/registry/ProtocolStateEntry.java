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

package me.dickmeister.mcprotocol.network.packet.registry;

import lombok.RequiredArgsConstructor;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.packet.Packet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class ProtocolStateEntry {

    private final PacketRegistry registry;

    private final Map<Integer, Packet> CLIENT = new ConcurrentHashMap<>();
    private final Map<Integer, Packet> SERVER = new ConcurrentHashMap<>();

    /**
     * Getting a new packet instance
     *
     * @param id        ID of a Packet you want to get
     * @param direction Direction of the packet SERVERBOUND or CLIENTBOUND
     * @return Returns a new instance of a Packet.
     * @throws InstantiationException throws when a Packet object is missing @NoArgsConstructor
     */
    public final Packet getPacket(final int id, final PacketDirection direction) throws InstantiationException {

        Packet packet = null;

        switch (direction) {
            case CLIENTBOUND:
                packet = this.SERVER.get(id);
                break;
            case SERVERBOUND:
                packet = this.CLIENT.get(id);
                break;
        }

        return registry.newInstance(packet);
    }

    /**
     * Registering a packet to the Protocol
     *
     * @param packet    Packet to register
     * @param direction Direction of the packet SERVERBOUND or CLIENTBOUND
     */
    public final void registerPacket(final Packet packet, final PacketDirection direction) {
        final int id = packet.getId();
        switch (direction) {
            case SERVERBOUND:
                CLIENT.put(id, packet);
                break;
            case CLIENTBOUND:
                SERVER.put(id, packet);
                break;
        }
    }

}
