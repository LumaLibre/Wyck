package dev.wyck.worldgen;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.level.dimension.Dimension;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.bukkit.World;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

/**
 * Information about a world. Wyck's version of {@link org.bukkit.generator.WorldInfo}.
 *
 * @see dev.wyck.worldgen.feature.custom.PlacementContext
 * @param resourceKey the world's key
 * @param uuid the world's UUID
 * @param seed the world's seed
 * @param minHeight the world's minimum height
 * @param maxHeight the world's maximum height
 * @param dimension the world's dimension
 * @param bukkitName the world's name, obsolete
 * @param environment the world's environment
 * @author Thorinwasher
 * @since 3.4.0
 * @version 3.4.0
 */
@NullMarked
@AsOf("3.4.0")
public record WorldContext(
    ResourceKey resourceKey,
    UUID uuid,
    long seed,
    int minHeight,
    int maxHeight,
    Dimension dimension,
    String bukkitName,
    World.Environment environment
    // TODO: Vanilla BiomeSource
) implements Keyed {

    @Override
    public Key key() {
        return this.resourceKey;
    }

    public String name() {
        return this.bukkitName;
    }

    public UUID uid() {
        return this.uuid;
    }
}
