package me.outspending.biomesapi.serialization;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;

public interface ConstantRepresentable {

    @AsOf("2.4.0")
    String getKey();

    @AsOf("2.4.0")
    static <E extends ConstantRepresentable> Codec<E> codec(E[] values) {
        return Codec.stringResolver(ConstantRepresentable::getKey, key -> {
            for (E value : values) {
                if (value.getKey().equalsIgnoreCase(key)) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Unknown value: " + key);
        });
    }
}
