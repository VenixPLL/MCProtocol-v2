package me.dickmeister.mcprotocol.network.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import me.dickmeister.mcprotocol.network.netty.encryption.NettyEncryptionTranslator;

import javax.crypto.Cipher;
import java.util.List;

public class NettyEncryptionCodec extends ByteToMessageCodec<ByteBuf> {

    private final NettyEncryptionTranslator cipher;
    private final NettyEncryptionTranslator decipher;

    public NettyEncryptionCodec(final Cipher encode, final Cipher decode) {
        this.cipher = new NettyEncryptionTranslator(encode);
        this.decipher = new NettyEncryptionTranslator(decode);
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        this.cipher.cipher(byteBuf, byteBuf2);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(this.decipher.decipher(channelHandlerContext, byteBuf));
    }
}
