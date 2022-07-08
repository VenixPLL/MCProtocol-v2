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

package me.dickmeister.mcprotocol.client;

import io.netty.channel.EventLoopGroup;

import java.net.Proxy;

public interface IClient {

    /**
     * Requesting a connection to target server.
     *
     * @param host      Hostname of target server.
     * @param port      Port of target server.
     * @param loopGroup Netty LoopGroup used by the Client.
     * @param proxy     Proxy used for the Client to connect through
     */
    void connect(final EventLoopGroup loopGroup, final String host, final int port, final Proxy proxy);

    /**
     * Closing a connection if it is already open.
     */
    void close();

}
