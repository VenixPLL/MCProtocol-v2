package me.dickmeister.mcprotocol.network.packet.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

/**
 * Class designed to replace not implemented Packets.
 */
@AllArgsConstructor
@NoArgsConstructor
public class CustomPacket extends Packet {

    @Setter
    @Getter
    private byte[] data;

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        final int size = buffer.readableBytes();
        this.data = new byte[size];

        buffer.readBytes(this.data);
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeBytes(this.data);
    }
}