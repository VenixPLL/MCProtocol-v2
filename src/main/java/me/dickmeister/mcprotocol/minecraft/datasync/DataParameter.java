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

public class DataParameter<T>
{
    private final int id;
    private final DataSerializer<T> serializer;

    public DataParameter(int idIn, DataSerializer<T> serializerIn) {
        this.id = idIn;
        this.serializer = serializerIn;
    }

    public int getId() {
        return this.id;
    }

    public DataSerializer<T> getSerializer() {
        return this.serializer;
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
            DataParameter<?> dataparameter = (DataParameter) p_equals_1_;
            return this.id == dataparameter.id;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.id;
    }
}