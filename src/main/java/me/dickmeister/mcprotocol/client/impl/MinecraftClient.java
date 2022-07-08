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

package me.dickmeister.mcprotocol.client.impl;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import me.dickmeister.mcprotocol.client.ClientBase;

import java.net.Proxy;

public class MinecraftClient extends ClientBase {

    /**
     * Connect implementation without loopGroup.
     * Creates new eventLoop every time executed.
     *
     * @param host  Target server hostname
     * @param port  Target server port
     * @param proxy Proxy to connect through
     */
    public void connect(String host, int port, Proxy proxy) {
        super.connect(Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup(), host, port, proxy);
    }

    @Override
    public void connect(EventLoopGroup loopGroup, String host, int port, Proxy proxy) {
        super.connect(loopGroup, host, port, proxy);
    }

    @Override
    public void close() {
        super.close();
    }
}
