package dev.wyck.wrapper.internal;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Arrays;

@NullMarked
@AsOf("2.0.0")
@ApiStatus.Internal
public interface WrappedEnumerator<W extends WrappedEnumerator<W>> {

    @AsOf("2.0.0")
    KeyedEnumTranslator<W> translator();

    @AsOf("2.0.0")
    @SuppressWarnings("unchecked")
    default <N extends Enum<N>> N toNms(Class<N> nmsEnumClass) {
        return translator().toNms((W) this, nmsEnumClass);
    }

    @AsOf("2.5.0")
    @SuppressWarnings("unchecked")
    default <N extends Enum<N>> N toNms(String... nmsClassNames) {
        for (String nmsClassName : nmsClassNames) {
            try {
                Class<?> nmsClass = Class.forName(nmsClassName);
                Preconditions.checkArgument(nmsClass.isEnum(), "not an enum: '%s'", nmsClassName);
                return this.toNms((Class<N>) nmsClass);
            } catch (ClassNotFoundException _) {}
        }
        throw new IllegalStateException("Minecraft enum class not found: " + Arrays.toString(nmsClassNames));
    }

    @AsOf("2.1.0")
    default <N extends Enum<N>> W fromNms(N nmsEnum) {
        return translator().fromNms(nmsEnum);
    }
}
