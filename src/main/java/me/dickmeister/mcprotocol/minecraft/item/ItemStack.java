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