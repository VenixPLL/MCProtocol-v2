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

package me.dickmeister.mcprotocol.server.impl;

import lombok.Getter;
import lombok.Setter;
import me.dickmeister.mcprotocol.listeners.SessionListener;
import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;
import me.dickmeister.mcprotocol.server.ServerBase;

/**
 * Empty implementation, idk how to do it
 */
public class MinecraftServer extends ServerBase {

    @Setter
    @Getter
    private SessionListener sessionListener;

    public MinecraftServer(PacketRegistry packetRegistry) {
        super(packetRegistry);
    }

    @Override
    public void bind(int port, final SessionListener listener) {
        this.sessionListener = listener;
        super.bind(port, sessionListener);

    }

    @Override
    public void close(boolean fast) {
        super.close(fast);
    }
}