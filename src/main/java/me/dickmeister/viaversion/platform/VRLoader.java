package me.dickmeister.viaversion.platform;

import me.dickmeister.viaversion.managers.VRVersionProvider;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.platform.ViaPlatformLoader;
import us.myles.ViaVersion.bungee.providers.BungeeMovementTransmitter;
import us.myles.ViaVersion.protocols.base.VersionProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;

public class VRLoader implements ViaPlatformLoader {

    @Override
    public void load() {
        Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
        Via.getManager().getProviders().use(VersionProvider.class, new VRVersionProvider());
    }

    @Override
    public void unload() {
        // Nothing to do
    }
}
