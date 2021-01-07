package me.dickmeister.mcprotocol.server;

import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.listeners.SessionListener;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.codec.NettyPacketCodec;
import me.dickmeister.mcprotocol.network.netty.codec.NettyVarInt21FrameCodec;
import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;
import me.dickmeister.mcprotocol.util.LogUtil;
import me.dickmeister.viaversion.IOPipelineName;
import me.dickmeister.viaversion.netty.server.IOViaServerDecode;
import me.dickmeister.viaversion.netty.server.IOViaServerEncode;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public abstract class ServerBase implements IServer {

    @Getter
    private final PacketRegistry packetRegistry;

    /**
     * List of active connections represented as Session
     */
    @Getter
    private final List<Session> sessionList = Lists.newArrayList();
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * Netty loop group.
     */
    @Getter
    private EventLoopGroup eventExecutors;
    private ChannelFuture channelFuture;

    @Override
    public void bind(int port, final SessionListener sessionListener) {
        try {

            this.eventExecutors = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
            Executors.newSingleThreadExecutor().submit(() -> this.run(port, sessionListener));
            this.countDownLatch.await();


        } catch (final Throwable t) {
            LogUtil.err(this.getClass(), "Failed to start server during initialization!");
            if (MCProtocol.DEBUG) t.printStackTrace();
        }
    }

    @Override
    public void close(boolean fast) {
        if (!fast) {
            sessionList.forEach(session -> session.getChannel().close());
            return;
        }

        this.channelFuture.channel().close();

        if (Objects.isNull(this.eventExecutors)) return;
        this.eventExecutors.shutdownNow();
    }

    /**
     * Running the server in concurent thread.
     */
    public final void run(final int port, final SessionListener sessionListener) {
        Thread.currentThread().setName("Server Thread");

        this.channelFuture = new ServerBootstrap()
                .channel((this.eventExecutors instanceof EpollEventLoopGroup) ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .group(this.eventExecutors)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel socketChannel) {
                        final ChannelPipeline pipeline = socketChannel.pipeline();

                        pipeline.addLast("timer", new ReadTimeoutHandler(30));
                        pipeline.addLast(IOPipelineName.FRAME_CODEC, new NettyVarInt21FrameCodec());
                        pipeline.addLast(IOPipelineName.PACKET_CODEC, new NettyPacketCodec(PacketDirection.SERVERBOUND, packetRegistry, ConnectionState.HANDSHAKE));

                        pipeline.addLast("handler", new SimpleChannelInboundHandler<Packet>() {

                            private Session session;

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                session = new Session(ctx.channel());
                                if (Objects.nonNull(sessionListener)) sessionListener.connected(session);
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                if (Objects.nonNull(sessionListener)) sessionListener.disconnected(session);
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
                                if (Objects.nonNull(sessionListener)) sessionListener.onPacketReceived(session, packet);
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                if (Objects.nonNull(sessionListener)) sessionListener.exceptionCaught(session, cause);
                            }
                        });
                    }
                }).bind(port).addListener((ChannelFutureListener) channelFuture -> {
                    if (channelFuture.isSuccess()) {
                        LogUtil.log(this.getClass(), "Server is now running on %s", channelFuture.channel().localAddress());
                    } else {
                        LogUtil.err(this.getClass(), "Server failed to start!");
                    }

                    this.countDownLatch.countDown();
                });
    }
}
