package me.dickmeister.viaversion.platform;

import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.viaversion.IOPipelineName;
import us.myles.ViaVersion.api.platform.ViaInjector;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.gson.JsonObject;

import java.lang.reflect.Method;
import java.util.Arrays;

public class VRInjector implements ViaInjector {

    @Override
    public void inject() {
        // *looks at Mixins*
    }

    @Override
    public void uninject() {
        // not possible *plays sad violin*
    }

    @Override
    public int getServerProtocolVersion() {
        return getClientProtocol();
    }

    private int getClientProtocol() {
        return MCProtocol.BASE_PROTOCOL;
    }

    @Override
    public String getEncoderName() {
        return IOPipelineName.VIA_HANDLER_ENCODER_NAME;
    }

    @Override
    public String getDecoderName() {
        return IOPipelineName.VIA_HANDLER_DECODER_NAME;
    }

    @Override
    public JsonObject getDump() {
        JsonObject obj = new JsonObject();
        try {
            obj.add("serverNetworkIOChInit", GsonUtil.getGson().toJsonTree(
                    Arrays.stream(Class.forName("net.minecraft.class_3242$1").getDeclaredMethods())
                            .map(Method::toString)
                            .toArray(String[]::new)));
        } catch (ClassNotFoundException ignored) {
        }
        try {
            obj.add("clientConnectionChInit", GsonUtil.getGson().toJsonTree(
                    Arrays.stream(Class.forName("net.minecraft.class_2535$1").getDeclaredMethods())
                            .map(Method::toString)
                            .toArray(String[]::new)));
        } catch (ClassNotFoundException ignored) {
        }
        return obj;
    }
}
