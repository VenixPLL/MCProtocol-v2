package me.dickmeister.mcprotocol.client.impl;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import me.dickmeister.mcprotocol.client.ClientBase;

import java.net.Proxy;

public class MinecraftClient extends ClientBase {

    /**
     * Connect implementation without loopGroup.
     * Creates new eventLoop every time executed.
     *
     * @param host  Target server hostname
     * @param port  Target server port
     * @param proxy Proxy to connect through
     */
    public void connect(String host, int port, Proxy proxy) {
        super.connect(Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup(), host, port, proxy);
    }

    @Override
    public void connect(EventLoopGroup loopGroup, String host, int port, Proxy proxy) {
        super.connect(loopGroup, host, port, proxy);
    }

    @Override
    public void close() {
        super.close();
    }
}
