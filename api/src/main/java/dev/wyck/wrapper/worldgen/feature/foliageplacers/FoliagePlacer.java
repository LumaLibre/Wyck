package dev.wyck.wrapper.worldgen.feature.foliageplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface FoliagePlacer extends Wrapper {

    @AsOf("3.0.0")
    IntProvider radius();

    @AsOf("3.0.0")
    IntProvider offset();

    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class FoliagePlacerBuilder<T, P> {
        protected IntProvider radius = IntProvider.constant(0);
        protected IntProvider offset = IntProvider.constant(0);

        public FoliagePlacerBuilder() {}

        public FoliagePlacerBuilder(FoliagePlacer foliagePlacer) {
            this.radius = foliagePlacer.radius();
            this.offset = foliagePlacer.offset();
        }

        @AsOf("3.0.0")
        public T radius(IntProvider radius) {
            this.radius = radius;
            return (T) this;
        }

        @AsOf("3.0.0")
        public T offset(IntProvider offset) {
            this.offset = offset;
            return (T) this;
        }

        public final P build() {
            Preconditions.checkArgument(radius.minInclusive() >= 0 && radius.maxInclusive() <= 16, "radius must be between 0 and 16");
            Preconditions.checkArgument(offset.minInclusive() >= 0 && offset.maxInclusive() <= 16, "offset must be between 0 and 16");
            return create();
        }

        protected abstract P create();
    }
}
