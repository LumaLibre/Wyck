package me.outspending.biomesapi.api.registry;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.api.annotations.AsOf;
import me.outspending.biomesapi.api.factory.WireProvider;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

/**
 * This interface represents a key for a biome resource in the game.
 * It uses the @AsOf annotation to indicate the version since the interface or its methods have been present or modified.
 *
 * @version 0.0.15
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("0.0.15")
public interface BiomeResourceKey extends Keyed {

    String BIOMESAPI_NAMESPACE = "biomesapi";
    char NAMESPACE_SEPARATOR = ':';

    WireProvider<Factory> FACTORY = WireProvider.create("BiomeResourceKey");

    interface Factory {
        BiomeResourceKey create(String namespace, String path);
    }

    /**
     * The namespace portion of this key (e.g. "minecraft", "biomesapi").
     */
    @AsOf("0.0.15")
    @NotNull String namespace();

    /**
     * The path portion of this key.
     */
    @AsOf("0.0.15")
    @NotNull String path();

    /**
     * Creates a new BiomeResourceKey from the given namespace and path.
     *
     * @param namespace the namespace for the resource location
     * @param path the path for the resource location
     * @return a new BiomeResourceKey with the given namespace and path
     */
    @AsOf("0.0.1")
    static @NotNull BiomeResourceKey of(@NotNull String namespace, @NotNull String path) {
        Preconditions.checkArgument(!namespace.isEmpty(), "namespace cannot be empty");
        Preconditions.checkArgument(!path.isEmpty(), "path cannot be empty");
        return FACTORY.get().create(namespace.toLowerCase(), path.toLowerCase());
    }

    /**
     * Creates a new BiomeResourceKey in the "biomesapi" namespace with the given path.
     * @param path the path for the resource location
     * @return a new BiomeResourceKey with the "biomesapi" namespace and the given path
     */
    @AsOf("0.0.8")
    static @NotNull BiomeResourceKey biomesapi(@NotNull String path) {
        return of(BIOMESAPI_NAMESPACE, path);
    }

    /**
     * Creates a new BiomeResourceKey from the given string representation.
     * The string should be in the format "namespace:path".
     *
     * @param keyString the string representation of the BiomeResourceKey
     * @return a new BiomeResourceKey with the given string representation
     * @version 0.0.15
     */
    @AsOf("0.0.15")
    static @NotNull BiomeResourceKey fromString(@NotNull String keyString) {
        Preconditions.checkArgument(!keyString.isEmpty(), "keyString cannot be empty");
        String[] parts = keyString.split(String.valueOf(NAMESPACE_SEPARATOR), 2);
        Preconditions.checkArgument(parts.length == 2, "keyString must be in the format 'namespace:path'");
        return of(parts[0], parts[1]);
    }

    /**
     * @return the Key representation of this BiomeResourceKey
     */
    @AsOf("0.0.6")
    @Override
    default @NotNull Key key() {
        return Key.key(namespace(), path());
    }
}