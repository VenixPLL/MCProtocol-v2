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
import lombok.Setter;
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
     * Amount of threads used by eventExecutors.
     */
    @Setter
    private int threads = 1;

    /**
     * Netty loop group.
     */
    @Getter
    private EventLoopGroup eventExecutors;
    private ChannelFuture channelFuture;

    @Override
    public void bind(int port, SessionListener sessionListener) {
        try {
            eventExecutors = Epoll.isAvailable() ? new EpollEventLoopGroup(threads) : new NioEventLoopGroup(threads);
            Executors.newSingleThreadExecutor().submit(() -> this.run(port, sessionListener));
            countDownLatch.await();
        } catch (Throwable throwable) {
            LogUtil.err(this.getClass(), "Failed to start server during initialization!");
            if (MCProtocol.DEBUG) {
                throwable.printStackTrace();
            }
        }
    }

    @Override
    public void close(boolean fast) {
        if (!fast) {
            sessionList.forEach(session -> session.getChannel().close());
            return;
        }

        channelFuture.channel().close();
        if (Objects.isNull(eventExecutors)) {
            return;
        }

        eventExecutors.shutdownNow();
    }

    /**
     * Running the server in concurent thread.
     */
    public final void run(final int port, final SessionListener sessionListener) {
        Thread.currentThread().setName("Server Thread");

        channelFuture = new ServerBootstrap()
                .channel((eventExecutors instanceof EpollEventLoopGroup) ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .group(eventExecutors)
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
                            public void channelActive(ChannelHandlerContext ctx) {
                                session = new Session(ctx.channel());
                                sessionList.add(session);
                                if (Objects.isNull(sessionListener)) {
                                    return;
                                }

                                sessionListener.connected(session);
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) {
                                if (Objects.nonNull(sessionListener)) {
                                    sessionListener.disconnected(session);
                                }

                                sessionList.remove(session);
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) {
                                if (Objects.isNull(sessionListener)) {
                                    return;
                                }

                                sessionListener.onPacketReceived(session, packet);
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                if (Objects.isNull(sessionListener)) {
                                    return;
                                }

                                sessionListener.exceptionCaught(session, cause);
                            }
                        });
                    }
                }).bind(port).addListener((ChannelFutureListener) channelFuture -> {
                    if (channelFuture.isSuccess()) {
                        LogUtil.log(this.getClass(), "Server is now running on " + channelFuture.channel().localAddress());
                    } else {
                        LogUtil.err(this.getClass(), "Server failed to start!");
                    }

                    countDownLatch.countDown();
                });
    }
}
