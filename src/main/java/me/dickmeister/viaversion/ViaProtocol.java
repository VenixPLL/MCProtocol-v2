package me.dickmeister.viaversion;

import lombok.Getter;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public final class ViaProtocol {

    @Getter private static final LinkedList<ProtocolVersion> protocolVersions = new LinkedList<>();

    private ViaProtocol() {
    }

    public static void load() {
        var count = 0;
        for (var field : ProtocolVersion.class.getDeclaredFields()) {
            if (!field.getType().equals(ProtocolVersion.class)) {
                continue;
            }

            count++;
            try {
                var protocolVersion = (ProtocolVersion) field.get(null);
                if (count < 8 || protocolVersion.getName().equals("UNKNOWN")) {
                    continue;
                }

                protocolVersions.add(protocolVersion);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        Collections.reverse(protocolVersions);
    }

    public static String getProtocol(int id) {
        return protocolVersions.stream()
                .filter(protocolVersion -> protocolVersion.getVersion() == id)
                .findFirst()
                .orElse(null)
                .getName();
    }
}