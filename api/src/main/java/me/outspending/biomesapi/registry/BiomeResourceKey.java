package me.outspending.biomesapi.registry;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.NmsHandle;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * This interface represents a key for a biome resource in the game.
 * It uses the @AsOf annotation to indicate the version since the interface or its methods have been present or modified.
 *
 * @version 2.3.0
 * @since 0.0.1
 * @author Outspending
 */
@AsOf("2.3.0")
@SuppressWarnings("PatternValidation")
public interface BiomeResourceKey extends Key, Keyed, NmsHandle {

    String MINECRAFT_NAMESPACE = "minecraft";
    String BIOMESAPI_NAMESPACE = "biomesapi";
    char NAMESPACE_SEPARATOR = ':';

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.registry.BiomeResourceKeyFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        BiomeResourceKey create(String namespace, String path);
    }

    /**
     * The namespace portion of this key (e.g. "minecraft", "biomesapi").
     */
    @AsOf("0.0.15")
    @KeyPattern.Namespace
    @NotNull String namespace();

    /**
     * The path portion of this key.
     */
    @AsOf("0.0.15")
    @NotNull String path();

    @Override
    @AsOf("2.2.1")
    @KeyPattern.Value
    default @NotNull String value() {
        return path();
    }

    /**
     * Underlying Minecraft Identifier.
     * @return the underlying Minecraft Identifier
     * @since 2.0.0
     */
    @AsOf("2.0.0")
    @NotNull Object resourceLocation();

    /**
     * Creates a new BiomeResourceKey from the given namespace and path.
     *
     * @param namespace the namespace for the resource location
     * @param path the path for the resource location
     * @return a new BiomeResourceKey with the given namespace and path
     * @since 0.0.1
     */
    @AsOf("0.0.1")
    static @NotNull BiomeResourceKey of(@NotNull String namespace, @NotNull String path) {
        Preconditions.checkArgument(!namespace.isEmpty(), "namespace cannot be empty");
        Preconditions.checkArgument(!path.isEmpty(), "path cannot be empty");
        return WIRE.get().create(namespace.toLowerCase(), path.toLowerCase());
    }

    /**
     * Creates a new BiomeResourceKey from the given string.
     * @param string the string to create the BiomeResourceKey from
     * @return a new BiomeResourceKey with the given string
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull BiomeResourceKey of(@NotNull String string) {
        return fromString(string);
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
     * Creates a new BiomeResourceKey in the "minecraft" namespace with the given path.
     * @param path the path for the resource location
     * @return a new BiomeResourceKey with the "minecraft" namespace and the given path
     * @since 2.3.0
     */
    @AsOf("2.3.0")
    static @NotNull BiomeResourceKey minecraft(@NotNull String path) {
        return of(MINECRAFT_NAMESPACE, path);
    }

    /**
     * Creates a new BiomeResourceKey from the given string representation.
     * The string should be in the format "namespace:path".
     *
     * @param keyString the string representation of the BiomeResourceKey
     * @return a new BiomeResourceKey with the given string representation
     * @since 0.0.15
     */
    @AsOf("0.0.15")
    static @NotNull BiomeResourceKey fromString(@NotNull String keyString) {
        Preconditions.checkArgument(!keyString.isEmpty(), "keyString cannot be empty");
        String[] parts = keyString.split(String.valueOf(NAMESPACE_SEPARATOR), 2);
        Preconditions.checkArgument(parts.length == 2, "keyString must be in the format 'namespace:path'");
        return of(parts[0], parts[1]);
    }

    @AsOf("2.2.1")
    @Override
    default @NotNull String asString() {
        return namespace() + NAMESPACE_SEPARATOR + path();
    }

    @AsOf("0.0.6")
    @Override
    default @NotNull Key key() {
        return Key.key(namespace(), path());
    }

    @Override
    @AsOf("2.3.0")
    default @NotNull Object toMinecraft() {
        return resourceLocation();
    }
}