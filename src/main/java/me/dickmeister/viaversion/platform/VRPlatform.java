package me.dickmeister.viaversion.platform;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.dickmeister.viaversion.ViaFabric;
import me.dickmeister.viaversion.config.VRViaConfig;
import me.dickmeister.viaversion.managers.VRConnectionManager;
import me.dickmeister.viaversion.managers.ViaFutureTaskId;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.ViaVersionConfig;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.configuration.ConfigurationProvider;
import us.myles.ViaVersion.api.platform.TaskId;
import us.myles.ViaVersion.api.platform.ViaConnectionManager;
import us.myles.ViaVersion.api.platform.ViaPlatform;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TextComponent;
import us.myles.viaversion.libs.bungeecordchat.chat.ComponentSerializer;
import us.myles.viaversion.libs.gson.JsonObject;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VRPlatform implements ViaPlatform<UUID> {

    private final Logger logger = Logger.getLogger("ViaFabric");
    private final VRViaConfig config;
    private final File dataFolder;
    private final ViaConnectionManager connectionManager;
    private final ViaAPI<UUID> api;

    public VRPlatform() {
        logger.setLevel(Level.OFF);
        Path configDir = ViaFabric.directory.toPath();
        config = new VRViaConfig(configDir.resolve("viaversion.yml").toFile());
        dataFolder = configDir.toFile();
        connectionManager = new VRConnectionManager();
        api = new VRViaAPI();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String getPlatformName() {
        return "ViaFabric";
    }

    @Override
    public String getPlatformVersion() {
        return "1.0";
    }

    @Override
    public String getPluginVersion() {
        return "3.3.0";
    }

    @Override
    public TaskId runAsync(Runnable runnable) {
        return new ViaFutureTaskId(CompletableFuture
                .runAsync(runnable, ViaFabric.ASYNC_EXECUTOR)
                .exceptionally(throwable -> {
                    if (!(throwable instanceof CancellationException)) {
                        throwable.printStackTrace();
                    }
                    return null;
                })
        );
    }

    @Override
    public TaskId runSync(Runnable runnable) {
        return runEventLoop(runnable);
    }

    private TaskId runEventLoop(Runnable runnable) {
        return new ViaFutureTaskId(
                ViaFabric.EVENT_LOOP
                        .submit(runnable)
                        .addListener(errorLogger())
        );
    }

    @Override
    public TaskId runSync(Runnable runnable, Long ticks) {
        return new ViaFutureTaskId(
                ViaFabric.EVENT_LOOP
                        .schedule(() -> runSync(runnable), ticks * 50, TimeUnit.MILLISECONDS)
                        .addListener(errorLogger())
        );
    }

    @Override
    public TaskId runRepeatingSync(Runnable runnable, Long ticks) {
        // ViaVersion seems to not need to run repeating tasks on main thread
        return new ViaFutureTaskId(
                ViaFabric.EVENT_LOOP
                        .scheduleAtFixedRate(() -> runSync(runnable), 0, ticks * 50, TimeUnit.MILLISECONDS)
                        .addListener(errorLogger())
        );
    }

    private <T extends Future<?>> GenericFutureListener<T> errorLogger() {
        return future -> {
            if (!future.isCancelled() && future.cause() != null) {
                future.cause().printStackTrace();
            }
        };
    }

    @Override
    public void cancelTask(TaskId taskId) {
        if (taskId instanceof ViaFutureTaskId) {
            ((ViaFutureTaskId) taskId).getObject().cancel(false);
        }
    }

    @Override
    public ViaCommandSender[] getOnlinePlayers() {
        return new ViaCommandSender[0];
    }

    @Override
    public void sendMessage(UUID uuid, String s) {
    }

    @Override
    public boolean kickPlayer(UUID uuid, String s) {
        return kickServer(uuid, s);
    }

    private boolean kickServer(UUID uuid, String s) {
        return false;  // Can't know if it worked
    }

    @Override
    public boolean isPluginEnabled() {
        return true;
    }

    @Override
    public ViaAPI<UUID> getApi() {
        return api;
    }

    @Override
    public ViaVersionConfig getConf() {
        return config;
    }

    @Override
    public ConfigurationProvider getConfigurationProvider() {
        return config;
    }

    @Override
    public File getDataFolder() {
        return dataFolder;
    }

    @Override
    public void onReload() {
        // Nothing to do
    }

    @Override
    public JsonObject getDump() {
        return new JsonObject();
    }

    @Override
    public boolean isOldClientsAllowed() {
        return true;
    }

    @Override
    public ViaConnectionManager getConnectionManager() {
        return connectionManager;
    }

    private String legacyToJson(String legacy) {
        return ComponentSerializer.toString(TextComponent.fromLegacyText(legacy));
    }
}
