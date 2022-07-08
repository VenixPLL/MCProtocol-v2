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

package me.dickmeister.mcprotocol.network.netty.encryption;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.NonNull;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class NettyEncryptionTranslator {
    private final Cipher cipher;
    private byte[] decode = new byte[0];
    private byte[] encode = new byte[0];

    public NettyEncryptionTranslator(@NonNull final Cipher cipherIn) {
        this.cipher = cipherIn;
    }

    private byte[] read(final ByteBuf buffer) {
        final int i = buffer.readableBytes();
        if (decode.length < i)
            decode = new byte[i];

        buffer.readBytes(decode, 0, i);
        return decode;
    }

    public ByteBuf decipher(ChannelHandlerContext ctx, ByteBuf buffer) throws ShortBufferException {
        final int size = buffer.readableBytes();
        final byte[] bytes = this.read(buffer);
        final ByteBuf byteBuf = ctx.alloc().heapBuffer(cipher.getOutputSize(size));
        byteBuf.writerIndex(cipher.update(bytes, 0, size, byteBuf.array(), byteBuf.arrayOffset()));
        return byteBuf;
    }

    public void cipher(ByteBuf buf, ByteBuf buf2) throws ShortBufferException {
        final int size = buf.readableBytes();
        final byte[] bytes = this.read(buf);
        final int outputSize = cipher.getOutputSize(size);

        if (this.encode.length < outputSize)
            this.encode = new byte[outputSize];

        buf2.writeBytes(this.encode, 0, cipher.update(bytes, 0, size, this.encode));
    }
}
