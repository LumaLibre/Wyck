package me.outspending.biomesapi.wrapper.internal;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.serialization.ConstantRepresentable;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface TranslatableRegistryConstant<W extends TranslatableRegistryConstant<W>> extends ConstantRepresentable {

    @AsOf("2.4.0")
    RegisteredConstantTranslator<W> translator();

    @AsOf("2.4.0")
    @SuppressWarnings("unchecked")
    default <N> N toNms() {
        return this.translator().toNms((W) this);
    }

    @AsOf("2.4.0")
    default <N> W fromNms(N nmsConstant) {
        return this.translator().fromNms(nmsConstant);
    }
}
