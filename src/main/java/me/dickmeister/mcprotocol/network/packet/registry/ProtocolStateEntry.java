package me.dickmeister.mcprotocol.network.packet.registry;

import lombok.RequiredArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.packet.Packet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ProtocolStateEntry {

    private final PacketRegistry registry;

    private final Map<Integer, Packet> CLIENT = Collections.synchronizedMap(new HashMap<>());
    private final Map<Integer, Packet> SERVER = Collections.synchronizedMap(new HashMap<>());

    /**
     * Getting a new packet instance
     *
     * @param id        ID of a Packet you want to get
     * @param direction Direction of the packet SERVERBOUND or CLIENTBOUND
     * @return Returns a new instance of a Packet.
     * @throws InstantiationException throws when a Packet object is missing @NoArgsConstructor
     */
    public final Packet getPacket(final int id, final PacketDirection direction) throws InstantiationException {

        Packet packet = null;

        switch (direction) {
            case CLIENTBOUND:
                packet = this.SERVER.get(id);
                break;
            case SERVERBOUND:
                packet = this.CLIENT.get(id);
                break;
        }

        return registry.newInstance(packet);
    }

    /**
     * Registering a packet to the Protocol
     *
     * @param packet    Packet to register
     * @param direction Direction of the packet SERVERBOUND or CLIENTBOUND
     */
    public final void registerPacket(final Packet packet, final PacketDirection direction) {
        final int id = packet.getId();
        switch (direction) {
            case SERVERBOUND:
                CLIENT.put(id, packet);
                break;
            case CLIENTBOUND:
                SERVER.put(id, packet);
                break;
        }
    }

}
