package me.dickmeister.viaversion.managers;

import us.myles.ViaVersion.api.platform.TaskId;

import java.util.concurrent.Future;

public class ViaFutureTaskId implements TaskId {

    private final Future<?> object;

    public ViaFutureTaskId(Future<?> object) {
        this.object = object;
    }

    @Override
    public Future<?> getObject() {
        return object;
    }
}
