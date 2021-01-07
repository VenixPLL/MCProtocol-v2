package me.dickmeister.viaversion.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import me.dickmeister.viaversion.IOPipelineName;
import us.myles.ViaVersion.api.data.UserConnection;

public class VRClientSideUserConnection extends UserConnection {

    public VRClientSideUserConnection(Channel socketChannel) {
        super(socketChannel);
    }

    @Override
    public void sendRawPacket(final ByteBuf packet, boolean currentThread) {
        Runnable act = () -> getChannel().pipeline().context(IOPipelineName.VIA_HANDLER_DECODER_NAME)
                .fireChannelRead(packet);
        if (currentThread) {
            act.run();
        } else {
            getChannel().eventLoop().execute(act);
        }
    }

    @Override
    public ChannelFuture sendRawPacketFuture(ByteBuf packet) {
        getChannel().pipeline().context(IOPipelineName.VIA_HANDLER_DECODER_NAME).fireChannelRead(packet);
        return getChannel().newSucceededFuture();
    }

    @Override
    public void sendRawPacketToServer(ByteBuf packet, boolean currentThread) {
        if (currentThread) {
            getChannel().pipeline().context(IOPipelineName.VIA_HANDLER_ENCODER_NAME).writeAndFlush(packet);
        } else {
            getChannel().eventLoop().submit(() -> {
                getChannel().pipeline().context(IOPipelineName.VIA_HANDLER_ENCODER_NAME).writeAndFlush(packet);
            });
        }
    }
}
