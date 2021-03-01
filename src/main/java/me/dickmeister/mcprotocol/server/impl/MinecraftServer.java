package me.dickmeister.mcprotocol.server.impl;

import lombok.Getter;
import lombok.Setter;
import me.dickmeister.mcprotocol.listeners.SessionListener;
import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;
import me.dickmeister.mcprotocol.server.ServerBase;

/**
 * Empty implementation, idk how to do it
 */
public class MinecraftServer extends ServerBase {

    @Setter
    @Getter
    private SessionListener sessionListener;

    public MinecraftServer(PacketRegistry packetRegistry) {
        super(packetRegistry);
    }

    @Override
    public void bind(int port, final SessionListener listener) {
        this.sessionListener = listener;
        super.bind(port, sessionListener);

    }

    @Override
    public void close(boolean fast) {
        super.close(fast);
    }
}