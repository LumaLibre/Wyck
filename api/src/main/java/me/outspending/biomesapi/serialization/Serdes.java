package me.outspending.biomesapi.serialization;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;

/**
 * Denotes that a class or interface has a codec.
 *
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@AsOf("2.4.0")
public interface Serdes<T> {

    /**
     * The codec for this class.
     * @return the codec.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Codec<? extends T> codec();
}
