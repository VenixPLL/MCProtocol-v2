package me.dickmeister.viaversion.addons;

import de.gerrygames.viarewind.api.ViaRewindConfig;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import us.myles.ViaVersion.api.Via;

import java.util.logging.Logger;

public class ViaRewindImplementation implements ViaRewindPlatform {

    public ViaRewindImplementation() {
        init(new ViaRewindConfig() {
            @Override
            public CooldownIndicator getCooldownIndicator() {
                return CooldownIndicator.TITLE;
            }

            @Override
            public boolean isReplaceAdventureMode() {
                return true;
            }

            @Override
            public boolean isReplaceParticles() {
                return true;
            }
        });
    }

    @Override
    public Logger getLogger() {
        return Via.getPlatform().getLogger();
    }
}
