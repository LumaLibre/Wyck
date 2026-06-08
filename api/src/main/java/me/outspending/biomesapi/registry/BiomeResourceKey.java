package me.outspending.biomesapi.registry;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * @deprecated Not all resource keys are inherently biomes anymore, use {@link ResourceKey} instead.
 */
@NullMarked
@AsOf("0.0.1")
@SuppressWarnings("DeprecatedIsStillUsed")
@Deprecated(forRemoval = true, since = "2.3.0")
@ApiStatus.ScheduledForRemoval(inVersion = "2.4.0")
public interface BiomeResourceKey extends ResourceKey {

    String MINECRAFT_NAMESPACE = "minecraft";
    String BIOMESAPI_NAMESPACE = "biomesapi";
    char NAMESPACE_SEPARATOR = ':';


    @AsOf("0.0.1")
    static BiomeResourceKey of(String namespace, String path) {
        return (BiomeResourceKey) ResourceKey.of(namespace, path);
    }

    @AsOf("2.3.0")
    static BiomeResourceKey of(String string) {
        return fromString(string);
    }

    @AsOf("0.0.8")
    static BiomeResourceKey biomesapi(String path) {
        return of(BIOMESAPI_NAMESPACE, path);
    }

    @AsOf("2.3.0")
    static BiomeResourceKey minecraft(String path) {
        return of(MINECRAFT_NAMESPACE, path);
    }

    @AsOf("0.0.15")
    static BiomeResourceKey fromString(String keyString) {
        return (BiomeResourceKey) ResourceKey.fromString(keyString);
    }
}
