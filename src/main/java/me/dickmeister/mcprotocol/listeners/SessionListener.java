package me.dickmeister.mcprotocol.listeners;

import me.dickmeister.mcprotocol.network.objects.Session;
import me.dickmeister.mcprotocol.network.packet.Packet;

public abstract class SessionListener {

    /**
     * Connected event fired on channel connection
     *
     * @param session Session on which the event was fired.
     */
    public void connected(final Session session) {
    }

    /**
     * Disconnected event fired on channel disconnection
     *
     * @param session Session on which the event was fired.
     */
    public void disconnected(final Session session) {
    }

    /**
     * Packet event fired on packet receive (Dependent on PacketDirection)
     *
     * @param session Session on which the event was fired.
     * @param packet  Packet received
     */
    public void onPacketReceived(final Session session, final Packet packet) {
    }

    /**
     * Exception event fired on any exception within Netty itself, on force close, timeout etc.
     *
     * @param session Session on which the event was fired
     * @param throwable       Throwable thrown
     */
    public void exceptionCaught(final Session session, final Throwable throwable) {
    }

}
