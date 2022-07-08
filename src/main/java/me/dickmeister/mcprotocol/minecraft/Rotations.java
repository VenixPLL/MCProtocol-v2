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

package me.dickmeister.mcprotocol.minecraft;

import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

/**
 * @author Unix
 * @since 10.01.2021
 */

public class Rotations {
    /**
     * Rotation on the X axis
     */
    protected final float x;

    /**
     * Rotation on the Y axis
     */
    protected final float y;

    /**
     * Rotation on the Z axis
     */
    protected final float z;

    public Rotations(float x, float y, float z) {
        this.x = !Float.isInfinite(x) && !Float.isNaN(x) ? x % 360.0F : 0.0F;
        this.y = !Float.isInfinite(y) && !Float.isNaN(y) ? y % 360.0F : 0.0F;
        this.z = !Float.isInfinite(z) && !Float.isNaN(z) ? z % 360.0F : 0.0F;
    }

    public Rotations(NBTTagList nbt) {
        this(nbt.getFloat(0), nbt.getFloat(1), nbt.getFloat(2));
    }

    public NBTTagList writeToNBT() {
        NBTTagList nbttaglist = new NBTTagList();
        nbttaglist.appendTag(new NBTTagFloat(this.x));
        nbttaglist.appendTag(new NBTTagFloat(this.y));
        nbttaglist.appendTag(new NBTTagFloat(this.z));
        return nbttaglist;
    }

    public boolean equals(Object p_equals_1_) {
        if (!(p_equals_1_ instanceof Rotations)) {
            return false;
        } else {
            Rotations rotations = (Rotations) p_equals_1_;
            return this.x == rotations.x && this.y == rotations.y && this.z == rotations.z;
        }
    }

    /**
     * Gets the X axis rotation
     */
    public float getX() {
        return this.x;
    }

    /**
     * Gets the Y axis rotation
     */
    public float getY() {
        return this.y;
    }

    /**
     * Gets the Z axis rotation
     */
    public float getZ() {
        return this.z;
    }
}