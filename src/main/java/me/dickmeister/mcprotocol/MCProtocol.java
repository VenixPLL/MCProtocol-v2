package me.dickmeister.mcprotocol;

import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;
import me.dickmeister.mcprotocol.network.security.CodecSecurity;
import me.dickmeister.viaversion.ViaFabric;

public class MCProtocol {

    public static boolean DEBUG = false;
    public static CodecSecurity codecSecurity = new CodecSecurity();
    public static String VIA_CFG_PATH = "viaversion/cfg";

    /**
     * Initializing McProtocol classes
     */
    public final PacketRegistry initialize() {

        final PacketRegistry packetRegistry;

        try {
            packetRegistry = new PacketRegistry();

            /* ViaVersion */
            try {
                new ViaFabric().onInitialize(VIA_CFG_PATH);
            } catch (Exception e) {
                e.printStackTrace();
            }
            /* ---------- */;


        } finally {
            System.gc();
        }

        return packetRegistry;
    }

}
