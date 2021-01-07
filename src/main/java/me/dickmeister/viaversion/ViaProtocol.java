package me.dickmeister.viaversion;

import us.myles.ViaVersion.api.protocol.ProtocolVersion;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;

public class ViaProtocol {

    private static final LinkedList<ProtocolVersion> protocolVersions = new LinkedList<>();

    public static void load() {
        int count = 0;
        for (Field f : ProtocolVersion.class.getDeclaredFields()) {
            if (f.getType().equals(ProtocolVersion.class)) {
                count++;
                try {
                    ProtocolVersion ver = (ProtocolVersion) f.get(null);
                    if (count >= 8 && !ver.getName().equals("UNKNOWN"))
                        getProtocolVersions().add(ver);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.reverse(getProtocolVersions());
    }

    public static LinkedList<ProtocolVersion> getProtocolVersions() {
        return protocolVersions;
    }

    public static String getProtocol(Integer id) {
        return protocolVersions.stream().filter(v -> v.getVersion() == id).findFirst().orElse(null).getName();
    }
}
