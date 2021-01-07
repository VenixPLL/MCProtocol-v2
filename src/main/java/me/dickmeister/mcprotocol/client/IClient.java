package me.dickmeister.mcprotocol.client;

import io.netty.channel.EventLoopGroup;

import java.net.Proxy;

public interface IClient {

    /**
     * Requesting a connection to target server.
     *
     * @param host      Hostname of target server.
     * @param port      Port of target server.
     * @param loopGroup Netty LoopGroup used by the Client.
     * @param proxy     Proxy used for the Client to connect through
     */
    void connect(final EventLoopGroup loopGroup, final String host, final int port, final Proxy proxy);

    /**
     * Closing a connection if it is already open.
     */
    void close();

}
