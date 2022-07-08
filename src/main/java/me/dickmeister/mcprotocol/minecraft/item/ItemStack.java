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

package me.dickmeister.mcprotocol.minecraft.item;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

@Getter
@Setter
public class ItemStack {
    private int id;
    private int amount;
    private int data;
    private NBTTagCompound nbt;

    public ItemStack(int id) {
        this.id = id;
        this.amount = 1;
    }

    public ItemStack(int id, int amount) {
        this.id = id;
        this.amount = amount;
        this.data = 0;
    }

    public ItemStack(int id, int amount, int data) {
        this.id = id;
        this.amount = amount;
        this.data = data;
    }

    public ItemStack(int id, int amount, int data, NBTTagCompound nbt) {
        this.id = id;
        this.amount = amount;
        this.data = data;
        this.nbt = nbt;
    }

    public ItemStack copy() {
        var itemStack = new ItemStack(id, amount, data);
        if (Objects.nonNull(nbt)) {
            itemStack.setNbt((NBTTagCompound) nbt.copy());
        }

        return itemStack;
    }
}