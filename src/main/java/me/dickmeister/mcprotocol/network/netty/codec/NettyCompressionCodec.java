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

package me.dickmeister.mcprotocol.network.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.DecoderException;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;

import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class NettyCompressionCodec extends ByteToMessageCodec<ByteBuf> {
    private final byte[] buffer = new byte[8192];
    private final Deflater deflater;
    private final Inflater inflater;
    private int threshold;

    public NettyCompressionCodec(int thresholdIn) {
        threshold = thresholdIn;
        deflater = new Deflater();
        inflater = new Inflater();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf in, ByteBuf out) throws Exception {
        final int readable = in.readableBytes();
        final PacketBuffer output = new PacketBuffer(out);

        if (readable < threshold) {
            output.writeVarIntToBuffer(0);
            out.writeBytes(in);
            return;
        }

        final byte[] bytes = new byte[readable];
        in.readBytes(bytes);
        output.writeVarIntToBuffer(bytes.length);

        deflater.setInput(bytes, 0, readable);
        deflater.finish();
        while (!deflater.finished()) {
            final int length = deflater.deflate(buffer);
            output.writeBytes(buffer, length);
        }
        deflater.reset();
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> out) throws Exception {
        if (buf.readableBytes() != 0) {
            final PacketBuffer in = new PacketBuffer(buf);
            final int size = in.readVarIntFromBuffer();
            if (size == 0) {
                out.add(buf.readBytes(buf.readableBytes()));
                return;
            }

            if (size < threshold)
                throw new DecoderException("Badly compressed packet: size of " + size + " is below threshold of " + threshold + ".");

            if (size > 2097152)
                throw new DecoderException("Badly compressed packet: size of " + size + " is larger than protocol maximum of " + 2097152 + ".");

            final byte[] bytes = new byte[buf.readableBytes()];
            in.readBytes(bytes);
            inflater.setInput(bytes);
            final byte[] inflated = new byte[size];
            inflater.inflate(inflated);
            out.add(Unpooled.wrappedBuffer(inflated));
            inflater.reset();
        }
    }

    public void setCompressionThreshold(int thresholdIn) {
        threshold = thresholdIn;
    }
}
