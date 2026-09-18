package dev.wyck.worldgen.carver;

import dev.wyck.annotations.AsOf;
import dev.wyck.annotations.Generated;
import dev.wyck.keys.ResourceKey;
import java.lang.String;
import java.lang.UnsupportedOperationException;
import org.jspecify.annotations.NullMarked;

/**
 * Auto-generated. Do not modify!
 * Run ./gradlew generateSources to regenerate.
 * <p>
 * Typed references to the built-in configured carvers registered by vanilla.
 * </p>
 *
 *
 * @since 2.3.0
 * @version 4.0.0
 * @author Wyck codegen
 */
@NullMarked
@AsOf("2.3.0")
@Generated("2026-09-18T20:37:08.744598Z")
public final class Carvers {
    // From: Carvers
    @AsOf("2.3.0")
    public static final WorldCarver CAVE = reference("cave");
    @AsOf("2.3.0")
    public static final WorldCarver CAVE_EXTRA_UNDERGROUND = reference("cave_extra_underground");
    @AsOf("2.3.0")
    public static final WorldCarver CANYON = reference("canyon");
    @AsOf("2.3.0")
    public static final WorldCarver NETHER_CAVE = reference("nether_cave");

    Carvers() {
        throw new UnsupportedOperationException("Not intended for instantiation");
    }

    private static WorldCarver reference(String path) {
        return WorldCarver.reference(ResourceKey.minecraft(path));
    }
}
