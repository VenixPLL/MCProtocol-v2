package me.dickmeister.viaversion.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.RequiredArgsConstructor;
import me.dickmeister.viaversion.IOPipelineName;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.bungee.util.BungeePipelineUtil;
import us.myles.ViaVersion.exception.CancelCodecException;
import us.myles.ViaVersion.exception.CancelEncoderException;

import java.util.List;

@ChannelHandler.Sharable
@RequiredArgsConstructor
public class IOViaServerEncode extends MessageToMessageEncoder<ByteBuf> {

    private final UserConnection info;
    private boolean handledCompression;

    @Override
    protected void encode(final ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
        if (!info.checkOutgoingPacket()) throw CancelEncoderException.generate(null);
        if (!info.shouldTransformPacket()) {
            out.add(bytebuf.retain());
            return;
        }

        ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        try {
            boolean needsCompress = handleCompressionOrder(ctx, transformedBuf);
            info.transformOutgoing(transformedBuf, CancelEncoderException::generate);

            if (needsCompress) {
                recompress(ctx, transformedBuf);
            }

            out.add(transformedBuf.retain());
        } finally {
            transformedBuf.release();
        }
    }

    private boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) {
        boolean needsCompress = false;
        if (!handledCompression) {
            if (ctx.pipeline().names().indexOf(IOPipelineName.PACKET_COMPRESSION) > ctx.pipeline().names().indexOf(IOPipelineName.VIA_HANDLER_ENCODER_NAME)) {
                // Need to decompress this packet due to bad order
                ByteBuf decompressed = BungeePipelineUtil.decompress(ctx, buf);
                try {
                    buf.clear().writeBytes(decompressed);
                } finally {
                    decompressed.release();
                }
                ChannelHandler dec = ctx.pipeline().get(IOPipelineName.VIA_HANDLER_DECODER_NAME);
                ChannelHandler enc = ctx.pipeline().get(IOPipelineName.VIA_HANDLER_ENCODER_NAME);
                ctx.pipeline().remove(dec);
                ctx.pipeline().remove(enc);
                ctx.pipeline().addAfter(IOPipelineName.PACKET_COMPRESSION, IOPipelineName.VIA_HANDLER_DECODER_NAME, dec);
                ctx.pipeline().addAfter(IOPipelineName.PACKET_COMPRESSION, IOPipelineName.VIA_HANDLER_ENCODER_NAME, enc);
                needsCompress = true;
                handledCompression = true;
            }
        }
        return needsCompress;
    }

    private void recompress(ChannelHandlerContext ctx, ByteBuf buf) {
        ByteBuf compressed = BungeePipelineUtil.compress(ctx, buf);
        try {
            buf.clear().writeBytes(compressed);
        } finally {
            compressed.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof CancelCodecException) return;
        super.exceptionCaught(ctx, cause);
    }
}
