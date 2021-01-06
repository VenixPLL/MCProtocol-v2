package me.dickmeister.mcprotocol;

import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;
import me.dickmeister.mcprotocol.network.security.CodecSecurity;

public class MCProtocol {

    public static boolean DEBUG = false;
    public static CodecSecurity codecSecurity = new CodecSecurity();

    /**
     * Initializing McProtocol classes
     */
    public final PacketRegistry initialize() {

        final PacketRegistry packetRegistry;

        try {
            packetRegistry = new PacketRegistry();
        } finally {
            System.gc();
        }

        return packetRegistry;
    }

}
