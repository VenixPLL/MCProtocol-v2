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

package me.dickmeister.mcprotocol.minecraft.datasync;

import me.dickmeister.mcprotocol.network.netty.PacketBuffer;

import java.io.IOException;

public interface DataSerializer<T>
{
    void write(PacketBuffer buf, T value);

    T read(PacketBuffer buf) throws IOException;

    DataParameter<T> createKey(int id);

    T func_192717_a(T p_192717_1_);
}