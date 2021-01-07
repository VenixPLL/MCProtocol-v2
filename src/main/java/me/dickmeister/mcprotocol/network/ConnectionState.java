package me.dickmeister.mcprotocol.network;

import java.util.Arrays;

/**
 * Minecraft protocol Connection States
 */
public enum ConnectionState {

    HANDSHAKE(0), STATUS(1), LOGIN(2), PLAY(3);

    private final int connectionState;

    ConnectionState(final int id) {
        this.connectionState = id;
    }

    public static ConnectionState valueOf(final int id) {
        return Arrays.stream(ConnectionState.values())
                .filter(val -> val.connectionState == id)
                .findAny().orElse(ConnectionState.HANDSHAKE);
    }

}
