package me.outspending.biomesapi.level;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.serialization.ConstantRepresentable;
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents the persistence mode of a level stem.
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@AsOf("2.4.0")
@ApiStatus.Experimental
public enum StemPersistence implements ConstantRepresentable {
    /**
     * The level stem lives only for the session and is injected straight into the world's dimensions.
     */
    TRANSIENT("transient"),
    /**
     * The level stem is also registered into the level_stem registry, so other registry callers can see it.
     */
    PERSISTENT("persistent");

    public static final Codec<StemPersistence> CODEC = ConstantRepresentable.codec(StemPersistence.values());

    private final String key;

    StemPersistence(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
