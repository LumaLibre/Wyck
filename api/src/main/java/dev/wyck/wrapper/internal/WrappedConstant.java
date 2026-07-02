package dev.wyck.wrapper.internal;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("2.5.0")
public interface WrappedConstant<W extends WrappedConstant<W>> {

    @AsOf("2.5.0")
    RegisteredConstantTranslator<W> translator();

    @AsOf("2.5.0")
    @SuppressWarnings("unchecked")
    default <N> N toNms() {
        return this.translator().toNms((W) this);
    }

    @AsOf("2.5.0")
    default <N> W fromNms(N nmsConstant) {
        return this.translator().fromNms(nmsConstant);
    }
}