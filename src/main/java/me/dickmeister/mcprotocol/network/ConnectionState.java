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

package me.dickmeister.mcprotocol.network;

import java.util.Arrays;

/**
 * Minecraft protocol Connection States
 */
public enum ConnectionState {

    HANDSHAKE(0), STATUS(1), LOGIN(2), PLAY(3);

    private final int connectionState;

    ConnectionState(final int id) {
        this.connectionState = id;
    }

    public static ConnectionState valueOf(final int id) {
        return Arrays.stream(ConnectionState.values())
                .filter(val -> val.connectionState == id)
                .findAny().orElse(ConnectionState.HANDSHAKE);
    }

}
