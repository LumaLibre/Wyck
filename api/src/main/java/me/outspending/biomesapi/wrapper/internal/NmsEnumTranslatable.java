package me.outspending.biomesapi.wrapper.internal;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.serialization.ConstantRepresentable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.0.0")
@ApiStatus.Internal
public interface NmsEnumTranslatable<W extends NmsEnumTranslatable<W>> extends ConstantRepresentable {

    @AsOf("2.0.0")
    KeyedEnumTranslator<W> translator();

    @AsOf("2.0.0")
    @SuppressWarnings("unchecked")
    default <N extends Enum<N>> N toNms(Class<N> nmsEnumClass) {
        return translator().toNms((W) this, nmsEnumClass);
    }

    @AsOf("2.1.0")
    default <N extends Enum<N>> W fromNms(N nmsEnum) {
        return translator().fromNms(nmsEnum);
    }
}
