package me.dickmeister.mcprotocol.network.packet;

import lombok.Data;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Data
public abstract class Packet {

    /**
     * Packet unique ID.
     */
    private int id = -1;

    /**
     * Reading from packet.
     *
     * @param buffer PacketBuffer to read from
     * @throws Exception
     */
    public abstract void read(final PacketBuffer buffer) throws Exception;

    /**
     * Writing to packet.
     *
     * @param buffer PacketBuffer to write to
     * @throws Exception
     */
    public abstract void write(final PacketBuffer buffer) throws Exception;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface PacketInfo {
        ConnectionState connectionState();

        PacketDirection packetDirection();
    }
}
