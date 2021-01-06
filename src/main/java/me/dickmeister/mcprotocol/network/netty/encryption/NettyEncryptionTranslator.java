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
