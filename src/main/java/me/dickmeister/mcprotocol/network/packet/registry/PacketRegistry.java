package me.dickmeister.mcprotocol.network.packet.registry;

import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.network.packet.impl.handshake.HandshakePacket;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class PacketRegistry {

    private final Map<ConnectionState, ProtocolStateEntry> protocolEntries = new ConcurrentHashMap<>();
    /**
     * Override for that one packet that is in Handshake State;
     * There is no sense to make a full ProtocolStateEntry for one Packet
     */
    private HandshakePacket handshakePacket;

    /**
     *  Registering default Packets.
     *  States that does not change between versions are HANDSHAKE,STATUS,LOGIN
     */ {
        this.reflectionsRegister("me.dickmeister.mcprotocol.network.packet.impl"
                , ConnectionState.HANDSHAKE, ConnectionState.STATUS, ConnectionState.LOGIN, ConnectionState.PLAY);

        if (MCProtocol.DEBUG) System.out.println("Registered " + protocolEntries.size() + " Protocols");

    }

    /**
     * Getting and creating a new instance of a Packet.
     *
     * @param id              ID of a Packet you want to get
     * @param connectionState State of which the packet belongs to.
     * @param direction       Direction of the packet SERVERBOUND or CLIENTBOUND
     * @return Returns a new Packet instance
     */
    public final Packet getPacket(final int id, final ConnectionState connectionState, final PacketDirection direction) {
        if (connectionState == ConnectionState.HANDSHAKE && id == 0x00) {
            try {
                return newInstance(this.handshakePacket);
            } catch (final Throwable throwable) {
                if (MCProtocol.DEBUG) throwable.printStackTrace();
                return null;
            }
        }

        final ProtocolStateEntry protocolStateEntry = this.protocolEntries.get(connectionState);

        if (Objects.nonNull(protocolStateEntry)) {

            Packet packet = null;

            try {
                packet = protocolStateEntry.getPacket(id, direction);
            } catch (final Throwable t) {
                if (MCProtocol.DEBUG) t.printStackTrace();
            }

            return packet;
        }

        return null;
    }

    /**
     * Find all packets of specified ConnectionState and register them.
     *
     * @param lookUpPath Path to lookUp for classes
     * @param states     ConnectionState's of packets you want to register.
     */
    public final void reflectionsRegister(final String lookUpPath, final ConnectionState... states) {
        final Set<Class<?>> classes = getPacketClasses(lookUpPath);

        classes.forEach(c -> {
            try {

                final Packet packet = (Packet) c.newInstance();
                final Packet.PacketInfo info = c.getAnnotation(Packet.PacketInfo.class);
                final ConnectionState connectionState = info.connectionState();

                final Stream<ConnectionState> stream = Arrays.stream(states);
                if (stream.anyMatch(connectionState::equals)) {
                    if (connectionState == ConnectionState.HANDSHAKE) {

                        if (Objects.isNull(this.handshakePacket)) {
                            this.handshakePacket = (HandshakePacket) packet;
                            return;
                        }

                        throw new IllegalStateException("Handshake packet is already defined!");
                    }

                    this.defineProtocolEntry(connectionState).registerPacket(packet, info.packetDirection());
                }

            } catch (final Exception exc) {
                if (MCProtocol.DEBUG) {
                    System.err.println("Failed to load packet - " + c.getSimpleName());
                    exc.printStackTrace();
                }
            }
        });

        classes.clear();
    }

    /**
     * @param connectionState Connection state on which protocol will be defined
     * @return returns a new instance of ProtocolStateEntry
     */
    public final ProtocolStateEntry defineProtocolEntry(final ConnectionState connectionState) {
        if (this.protocolEntries.containsKey(connectionState)) return this.protocolEntries.get(connectionState);

        final ProtocolStateEntry protocolStateEntry = new ProtocolStateEntry(this);
        this.protocolEntries.put(connectionState, protocolStateEntry);

        return protocolStateEntry;
    }

    /**
     * @param lookUpPath Path to lookUp for classes
     * @return Returns a Set of Packet classes annotated with PacketInfo
     */
    private Set<Class<?>> getPacketClasses(final String lookUpPath) {
        return new Reflections(lookUpPath)
                .getTypesAnnotatedWith(Packet.PacketInfo.class);
    }

    /**
     * @param packetIn Packet you want to instantiate
     * @return Returns a instantiated packet.
     * @throws InstantiationException throws when a Packet object is missing @NoArgsConstructor
     */
    public final Packet newInstance(final Packet packetIn) throws InstantiationException {
        if (packetIn == null) return null;
        Class<? extends Packet> packet = packetIn.getClass();
        try {
            Constructor<? extends Packet> constructor = packet.getDeclaredConstructor();
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }

            return constructor.newInstance();
        } catch (Exception e) {
            if (MCProtocol.DEBUG) e.printStackTrace();
            throw new InstantiationException("Failed to instantiate packet \"" + packetIn.getId() + ", " + packet.getName() + "\".");
        }
    }

}
