package me.dickmeister.viaversion;

import io.netty.channel.EventLoop;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import me.dickmeister.viaversion.addons.ViaBackwardsImplementation;
import me.dickmeister.viaversion.addons.ViaRewindImplementation;
import me.dickmeister.viaversion.platform.VRInjector;
import me.dickmeister.viaversion.platform.VRLoader;
import me.dickmeister.viaversion.platform.VRPlatform;
import us.myles.ViaVersion.ViaManager;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.MappingDataLoader;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViaFabric {

    public static File directory;

    public static CompletableFuture<Void> INIT_FUTURE = new CompletableFuture<>();
    public static ExecutorService ASYNC_EXECUTOR;
    public static EventLoop EVENT_LOOP;

    public void onInitialize(final String cfgPath) {
        directory = new File(cfgPath);
        ASYNC_EXECUTOR = Executors.newFixedThreadPool(8);
        EVENT_LOOP = (Epoll.isAvailable() ? new EpollEventLoopGroup(1).next() : new NioEventLoopGroup(1).next());
        EVENT_LOOP.submit(INIT_FUTURE::join);
        if (!directory.exists())
            directory.mkdir();
        Via.init(ViaManager.builder()
                .injector(new VRInjector())
                .loader(new VRLoader())
                .platform(new VRPlatform()).build());
        MappingDataLoader.enableMappingsCache();
        new ViaBackwardsImplementation();
        new ViaRewindImplementation();
        Via.getManager().init();
        INIT_FUTURE.complete(null);
    }
}
