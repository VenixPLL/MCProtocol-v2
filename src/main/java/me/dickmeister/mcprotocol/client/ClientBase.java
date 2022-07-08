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

package me.dickmeister.mcprotocol.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.proxy.HttpProxyHandler;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.Getter;
import lombok.Setter;
import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.listeners.SessionListener;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.codec.NettyPacketCodec;
import me.dickmeister.mcprotocol.network.netty.codec.NettyVarInt21FrameCodec;
import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.viaversion.IOPipelineName;

import java.net.Proxy;
import java.util.Objects;

public abstract class ClientBase implements IClient {

    /**
     * Session defined by Client.
     */
    @Getter
    private Session session;

    /**
     * Listener for Session
     */
    @Setter
    private SessionListener sessionListener;

    /**
     * Opening connection to target server
     *
     * @param host      Hostname of target server.
     * @param port      Port of target server.
     * @param loopGroup Netty LoopGroup used by the Client.
     * @param proxy     Proxy used for the Client to connect through
     */
    @Override
    public void connect(final EventLoopGroup loopGroup, final String host, final int port, final Proxy proxy) {

        final Bootstrap bootstrap = new Bootstrap()
                .group(loopGroup)
                .channel((loopGroup instanceof EpollEventLoopGroup) ? EpollSocketChannel.class : NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        final ChannelPipeline pipeline = socketChannel.pipeline();
                        if (proxy != Proxy.NO_PROXY) {
                            switch(proxy.type()){
                                case HTTP:
                                    pipeline.addFirst("proxy", new HttpProxyHandler(proxy.address()));
                                    break;
                                case SOCKS:
                                    pipeline.addFirst("proxy", new Socks4ProxyHandler(proxy.address()));
                                    break;
                            }
                        }

                        session = new Session(socketChannel);
                        session.getChannel().config().setOption(ChannelOption.TCP_NODELAY, true);
                        session.getChannel().config().setOption(ChannelOption.IP_TOS, 0x18);

                        pipeline.addLast("timer", new ReadTimeoutHandler(30));
                        pipeline.addLast(IOPipelineName.FRAME_CODEC, new NettyVarInt21FrameCodec());
                        pipeline.addLast(IOPipelineName.PACKET_CODEC, new NettyPacketCodec(PacketDirection.CLIENTBOUND, MCProtocol.packetRegistry, ConnectionState.HANDSHAKE));
                        pipeline.addLast("handler", new SimpleChannelInboundHandler<Packet>() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                if (Objects.isNull(sessionListener)) return;
                                sessionListener.connected(session);
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
                                if (Objects.isNull(sessionListener)) return;
                                sessionListener.onPacketReceived(session, packet);
                            }

                            @Override
                            public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                if (Objects.isNull(sessionListener)) return;
                                sessionListener.disconnected(session);
                            }

                            @Override
                            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                if (Objects.isNull(sessionListener)) return;
                                sessionListener.exceptionCaught(session, cause);
                            }
                        });

                    }
                });

        bootstrap.connect(host, port).syncUninterruptibly();
    }

    /**
     * Closing already opened connection
     */
    @Override
    public void close() {
        if (Objects.isNull(this.session)) return;

        this.session.close();
    }
}
