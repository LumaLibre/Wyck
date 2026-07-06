package dev.wyck.wrapper.worldgen.feature.featuresize;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.Wrapper;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.OptionalInt;

@NullMarked
@AsOf("3.0.0")
public interface FeatureSize extends Wrapper {

    @AsOf("3.0.0")
    OptionalInt minClippedHeight();


    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class FeatureSizeBuilder<T, P> {
        protected @Nullable Integer minClippedHeight;

        public FeatureSizeBuilder() {}

        public FeatureSizeBuilder(final @Nullable Integer minClippedHeight) {
            this.minClippedHeight = minClippedHeight;
        }

        public T minClippedHeight(final int minClippedHeight) {
            this.minClippedHeight = minClippedHeight;
            return (T) this;
        }

        public final P build() {
            if (minClippedHeight != null) {
                Preconditions.checkArgument(minClippedHeight >= 0 && minClippedHeight <= 80, "if set, minClippedHeight must be between 0 and 80");
            }
            return create();
        }

        protected abstract P create();
    }
}
