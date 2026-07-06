package dev.wyck.wrapper.worldgen.feature.trunkplacers;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.internal.Wrapper;
import org.jspecify.annotations.NullMarked;

@NullMarked
@AsOf("3.0.0")
public interface TrunkPlacer extends Wrapper {

    int MAX_BASE_HEIGHT = 32;
    int MAX_RAND = 24;
    int MAX_HEIGHT = 80;

    @AsOf("3.0.0")
    int baseHeight();

    @AsOf("3.0.0")
    int heightRandA();

    @AsOf("3.0.0")
    int heightRandB();

    @AsOf("3.0.0")
    @SuppressWarnings("unchecked")
    abstract class TrunkPlacerBuilder<T, P> {
        protected int baseHeight;
        protected int heightRandA;
        protected int heightRandB;

        public TrunkPlacerBuilder() {}

        public TrunkPlacerBuilder(TrunkPlacer trunkPlacer) {
            this.baseHeight = trunkPlacer.baseHeight();
            this.heightRandA = trunkPlacer.heightRandA();
            this.heightRandB = trunkPlacer.heightRandB();
        }

        @AsOf("3.0.0")
        public T baseHeight(int baseHeight) {
            this.baseHeight = baseHeight;
            return (T) this;
        }

        @AsOf("3.0.0")
        public T heightRandA(int heightRandA) {
            this.heightRandA = heightRandA;
            return (T) this;
        }

        @AsOf("3.0.0")
        public T heightRandB(int heightRandB) {
            this.heightRandB = heightRandB;
            return (T) this;
        }

        public final P build() {
            Preconditions.checkArgument(this.baseHeight >= 0 && this.baseHeight <= MAX_BASE_HEIGHT, "baseHeight must be between 0 and " + MAX_BASE_HEIGHT);
            Preconditions.checkArgument(this.heightRandA >= 0 && this.heightRandA <= MAX_RAND, "heightRandA must be between 0 and " + MAX_RAND);
            Preconditions.checkArgument(this.heightRandB >= 0 && this.heightRandB <= MAX_RAND, "heightRandB must be between 0 and " + MAX_RAND);
            return create();
        }

        protected abstract P create();
    }
}
