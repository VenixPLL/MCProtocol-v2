package me.dickmeister.viaversion.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.RequiredArgsConstructor;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.exception.CancelCodecException;
import us.myles.ViaVersion.exception.CancelDecoderException;

import java.util.List;

@ChannelHandler.Sharable
@RequiredArgsConstructor
public class IOViaServerDecode extends MessageToMessageDecoder<ByteBuf> {

    private final UserConnection info;

    @Override
    protected void decode(final ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (!info.checkIncomingPacket()) throw CancelDecoderException.generate(null);
        if (!info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }

        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            info.transformIncoming(transformedBuf, CancelDecoderException::generate);
            out.add(transformedBuf.retain());
        } finally {
            transformedBuf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof CancelCodecException) return;
        super.exceptionCaught(ctx, cause);
    }
}
