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
