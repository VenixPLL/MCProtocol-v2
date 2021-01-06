package me.dickmeister.mcprotocol.server;

import me.dickmeister.mcprotocol.listeners.SessionListener;

public interface IServer {

    /**
     * Binding the server to specified port.
     *
     * @param port            Port on which the server should be bind.
     * @param sessionListener listen to actions performed by server.
     */
    void bind(final int port, final SessionListener sessionListener);

    /**
     * Closing the server
     *
     * @param fast Fast close option so the Server does not close connections gracefully.
     */
    void close(final boolean fast);
}
