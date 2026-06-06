package me.outspending.biomesapi.factory;

import io.papermc.paper.ServerBuildInfo;
import me.outspending.biomesapi.BiomesAPI;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.exceptions.UnknownNMSVersionException;
import org.bukkit.Bukkit;
import org.jspecify.annotations.NullMarked;

/**
 * An enum representing supported Minecraft versions for the BiomesAPI.
 * Each enum value represents a specific Minecraft version and includes a Version object that contains information about the version.
 * @version 2.0.0
 * @since 2.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.0.0")
public enum Versions {
    V1_21_11(Version.of(Version.Type.SUPPORTED, "v1_21_11", "1.21.10", "1.21.11")),
    V26_1(Version.of(Version.Type.SUPPORTED, "v26_1", "26.1", "26.1.1", "26.1.2"));

    public static final Version ACTIVE = getActive();

    private final Version version;

    Versions(Version version) {
        this.version = version;
    }

    public Version getVersion() {
        return version;
    }

    @AsOf("2.0.0")
    public static Version getActive() {
        // This is here to prevent lazy loading of the BiomesAPI interface
        BiomesAPI.biomesapi();

        String mc;
        try {
            mc = ServerBuildInfo.buildInfo().minecraftVersionId();
        } catch (Exception _) { // Listening for ClassNotFoundException; Should never happen
            mc = Bukkit.getMinecraftVersion();
        }

        for (Versions versions : values()) {
            if (versions.getVersion().isApplicable(mc)) {
                Version del = versions.getVersion();
                if (del.type() == Version.Type.UNSUPPORTED) {
                    throw new UnknownNMSVersionException(del.getMessage());
                }
                return del;
            }
        }

        throw new UnknownNMSVersionException(Version.of(Version.Type.UNKNOWN, "").getMessage());
    }
}
