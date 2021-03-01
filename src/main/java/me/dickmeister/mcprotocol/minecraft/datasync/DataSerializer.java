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