package me.dickmeister.viaversion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@RequiredArgsConstructor
public class ViaClient {

    public static List<ViaClient> clients = new CopyOnWriteArrayList<>();

    private long id;
    private int protocolConnection = 340;

    public ViaClient(long id) {
        this.id = id;
    }

    public void initFabric() {
        clients.add(this);
    }

    public void updateID(long id) {
        this.id = id;
    }

    public static ViaClient getClient(long id) {
        return clients.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public void setProtocolVersion(int id) {
        this.protocolConnection = id;
    }

    public void remove() {
        clients.remove(this);
    }

}
