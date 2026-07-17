package dev.wyck.test.live;

import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Assumptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

@NullMarked
public final class PaperServer {

    private static final int STARTUP_TIMEOUT_SECONDS = 120;
    private static final int WORK_TIMEOUT_SECONDS = 120;

    private static @Nullable Path universe;
    private static boolean booted;

    private PaperServer() {
    }

    public static void requireEula() {
        Assumptions.assumeTrue(Boolean.getBoolean("com.mojang.eula.agree"),
                "real-server tests need Mojang's EULA accepted; re-run with -PacceptEula");
    }

    public static synchronized MinecraftServer start() {
        requireEula();
        if (booted) {
            return MinecraftServer.getServer();
        }

        try {
            universe = Files.createTempDirectory(Path.of("").toAbsolutePath(), "universe-");
        } catch (IOException e) {
            throw new IllegalStateException("could not create a scratch universe directory", e);
        }

        Thread boot = new Thread(() -> org.bukkit.craftbukkit.Main.main(new String[]{
                "--nogui",
                "--universe", universe.toString(),
                "--world", "test_world",
                "--port", "0", // let the OS pick
        }), "wyck-test-server");
        boot.setDaemon(true);
        boot.start();

        MinecraftServer server = awaitReady();
        booted = true;
        return server;
    }

    private static MinecraftServer awaitReady() {
        for (int i = 0; i < STARTUP_TIMEOUT_SECONDS; i++) {
            MinecraftServer server = MinecraftServer.getServer();
            //noinspection ConstantConditions <- nullable
            if (server != null && server.isReady()) {
                return server;
            }
            sleep();
        }
        throw new IllegalStateException("server never became ready within " + STARTUP_TIMEOUT_SECONDS + "s");
    }

    public static <T> T onServerThread(Supplier<T> work) {
        MinecraftServer server = MinecraftServer.getServer();
        //noinspection ConstantConditions <- again, nullable
        if (server == null) {
            throw new IllegalStateException("no server started");
        }
        try {
            return server.submit(work).get(WORK_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException runtime) {
                throw runtime;
            }
            if (cause instanceof Error error) {
                throw error;
            }
            throw new IllegalStateException("work on the server thread failed", cause);
        } catch (TimeoutException e) {
            throw new IllegalStateException("work on the server thread did not finish within " + WORK_TIMEOUT_SECONDS + "s", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("interrupted waiting on the server thread", e);
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("interrupted while waiting for the server", e);
        }
    }
}
