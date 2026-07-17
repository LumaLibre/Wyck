package dev.wyck.test;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class MinecraftBootstrap implements BeforeAllCallback {

    private static boolean booted;

    @Override
    public void beforeAll(ExtensionContext context) {
        boot();
    }

    public static synchronized void boot() {
        if (booted) {
            return;
        }
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        Bootstrap.validate();
        booted = true;
    }
}
