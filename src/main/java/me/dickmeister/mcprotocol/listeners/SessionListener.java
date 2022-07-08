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

package me.dickmeister.mcprotocol.listeners;

import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.Packet;

public abstract class SessionListener {

    /**
     * Connected event fired on channel connection
     *
     * @param session Session on which the event was fired.
     */
    public void connected(final Session session) {
    }

    /**
     * Disconnected event fired on channel disconnection
     *
     * @param session Session on which the event was fired.
     */
    public void disconnected(final Session session) {
    }

    /**
     * Packet event fired on packet receive (Dependent on PacketDirection)
     *
     * @param session Session on which the event was fired.
     * @param packet  Packet received
     */
    public void onPacketReceived(final Session session, final Packet packet) {
    }

    /**
     * Exception event fired on any exception within Netty itself, on force close, timeout etc.
     *
     * @param session Session on which the event was fired
     * @param throwable       Throwable thrown
     */
    public void exceptionCaught(final Session session, final Throwable throwable) {
    }

}
