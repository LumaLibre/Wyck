package dev.wyck.test.bootstrap;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.densityfunction.DensityFunction;
import net.minecraft.world.level.levelgen.densityfunction.SamplerContext;
import net.minecraft.world.level.levelgen.synth.Noise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

final class DensityTestSupport {

    private static final DensityFunction.CompileContext COMPILE_CONTEXT = new DensityFunction.CompileContext() {
        @Override
        public Noise createNoiseSampler(Holder<NormalNoise> noise) {
            return noise.value().create(RandomSource.create(0L));
        }

        @Override
        public RandomSource createRandom(Identifier identifier) {
            return RandomSource.create(identifier.hashCode());
        }

        @Override
        public RandomSource createEndIslandRandom() {
            return RandomSource.create(0L);
        }
    };

    private DensityTestSupport() {
    }

    static float sample(DensityFunction function) {
        return function.compileSampler(COMPILE_CONTEXT)
            .sampleValue(SamplerContext.EMPTY_UNCACHED, 0, 0, 0);
    }
}
