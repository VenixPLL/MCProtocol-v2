package me.dickmeister.mcprotocol.network.security;

import io.netty.channel.Channel;
import me.dickmeister.mcprotocol.logging.SimpleErrorReporting;

public final class CodecSecurity {

    /**
     * Closing connection and printing error details.
     */
    public final void throwConnection(final Channel channel, final ErrorType errorType) {
        channel.close();
        new SimpleErrorReporting().header("Failed to read packet", "Reason: " + errorType.name()).print();
    }

    /**
     * Types of Attacks
     */
    public enum ErrorType {
        EMPTY_BUF, BAD_PACKET_ID, PACKET_READ_FAIL, PACKET_TOO_BIG, UNKNOWN_ERROR
    }
}
