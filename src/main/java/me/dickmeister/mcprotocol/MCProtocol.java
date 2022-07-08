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

package me.dickmeister.mcprotocol;

import me.dickmeister.mcprotocol.network.packet.registry.PacketRegistry;
import me.dickmeister.mcprotocol.network.security.CodecSecurity;
import me.dickmeister.viaversion.ViaFabric;

public class MCProtocol {

    /**
     * Base protocol defined by Packets in PLAY ConnectionState.
     * To Fully change BASE_PROTOCOL You have to rewrite all PLAY Packets to protocol you want.
     */
    public static final int BASE_PROTOCOL = 340;

    /**
     * Debug option to help find errors and problems.
     */
    public static boolean DEBUG = false;
    public static CodecSecurity codecSecurity = new CodecSecurity();

    /**
     * Configuration path for ViaVersion
     */
    public static String VIA_CFG_PATH = "viaversion/cfg";
    public static boolean VIA_ENABLED = false;

    /**
     * PacketRegistry object. idk how to do it better.
     */
    public static PacketRegistry packetRegistry;

    /**
     * Initializing McProtocol classes
     */
    public final PacketRegistry initialize(boolean enableVia) {
        VIA_ENABLED = enableVia;

        try {
            packetRegistry = new PacketRegistry();
            if (enableVia) new ViaFabric().onInitialize(VIA_CFG_PATH);
        } finally {
            System.gc();
        }

        return packetRegistry;
    }

}
