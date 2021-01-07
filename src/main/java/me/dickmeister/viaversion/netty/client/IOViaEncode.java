package me.dickmeister.viaversion.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.exception.CancelCodecException;
import us.myles.ViaVersion.exception.CancelEncoderException;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class IOViaEncode extends MessageToMessageEncoder<ByteBuf> {

    private final UserConnection info;

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (!info.checkIncomingPacket()) throw CancelEncoderException.generate(null);
        if (!info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }
        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            info.transformIncoming(transformedBuf, CancelEncoderException::generate);
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
