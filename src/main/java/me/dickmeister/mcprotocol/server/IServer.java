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

package me.dickmeister.mcprotocol.server;

import me.dickmeister.mcprotocol.listeners.SessionListener;

public interface IServer {

    /**
     * Binding the server to specified port.
     *
     * @param port            Port on which the server should be bind.
     * @param sessionListener listen to actions performed by server.
     */
    void bind(final int port, final SessionListener sessionListener);

    /**
     * Closing the server
     *
     * @param fast Fast close option so the Server does not close connections gracefully.
     */
    void close(final boolean fast);
}
