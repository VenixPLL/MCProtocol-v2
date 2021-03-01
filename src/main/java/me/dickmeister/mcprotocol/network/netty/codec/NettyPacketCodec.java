package me.dickmeister.mcprotocol.network.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.EncoderException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.network.packet.impl.CustomPacket;
import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;
import me.dickmeister.mcprotocol.network.security.CodecSecurity;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class NettyPacketCodec extends ByteToMessageCodec<Packet> {

    private final PacketDirection packetDirection;
    private final PacketRegistry packetRegistry;
    private ConnectionState connectionState;

    /**
     * Encoding minecraft Packets
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) {
        final PacketBuffer buffer = new PacketBuffer(byteBuf);

        try {
            buffer.writeVarIntToBuffer(packet.getId());
            packet.write(buffer);
        } catch (final Throwable t) {
            throw new EncoderException("Failed to encode packet - " + packet.getClass().getSimpleName() + " ID: " + packet.getId());
        }
    }

    /**
     * Decoding minecraft Packets
     * Basic security layer.
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List out) {
        if (byteBuf.isReadable()) {
            if (!ctx.channel().isActive()) {
                return;
            }

            final PacketBuffer buffer = new PacketBuffer(byteBuf);
            try {
                final int id = buffer.readVarIntFromBuffer();
                if (id < 0 || id > 255) {
                    this.throwConnection(ctx, CodecSecurity.ErrorType.BAD_PACKET_ID);
                    return;
                }

                Packet packet;
                if (Objects.isNull(packet = packetRegistry.getPacket(id, connectionState, packetDirection))) {
                    packet = new CustomPacket();
                    packet.setId(id);
                }

                try {
                    packet.read(buffer);
                } catch (final Throwable t) {
                    this.throwConnection(ctx, CodecSecurity.ErrorType.PACKET_READ_FAIL);
                    packet = null;
                    return;
                }

                if (buffer.isReadable()) {
                    System.out.println("ID: " + id + " recognized as " + packet.getClass().getSimpleName());
                    this.throwConnection(ctx, CodecSecurity.ErrorType.PACKET_TOO_BIG);
                    packet = null;
                    return;
                }

                out.add(packet);
            } catch (final Throwable t) {
                this.throwConnection(ctx, CodecSecurity.ErrorType.UNKNOWN_ERROR);
            }
        }
    }

    /**
     * Security against NullPing and Handshake Spam Attacks.
     */
    private void throwConnection(final ChannelHandlerContext ctx, final CodecSecurity.ErrorType type) {
        MCProtocol.codecSecurity.throwConnection(ctx.channel(), type);
    }
}