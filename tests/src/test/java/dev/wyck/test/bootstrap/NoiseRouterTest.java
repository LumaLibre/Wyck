package dev.wyck.test.bootstrap;

import dev.wyck.worldgen.function.simple.ConstantSimpleFunction;
import dev.wyck.worldgen.noise.NoiseRouter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MinecraftBootstrap.class)
class NoiseRouterTest {

    private static final double EXACT = 0.0;

    private static net.minecraft.world.level.levelgen.NoiseRouter routerWithDistinctSlots() {
        return NoiseRouter.builder()
            .temperature(ConstantSimpleFunction.of(1.0))
            .vegetation(ConstantSimpleFunction.of(2.0))
            .continents(ConstantSimpleFunction.of(3.0))
            .erosion(ConstantSimpleFunction.of(4.0))
            .depth(ConstantSimpleFunction.of(5.0))
            .ridges(ConstantSimpleFunction.of(6.0))
            .chunkSurfaceLevel(ConstantSimpleFunction.of(7.0))
            .finalDensity(ConstantSimpleFunction.of(8.0))
            .build()
            .asHandle();
    }

    private static void assertSlot(
        double expected,
        net.minecraft.world.level.levelgen.densityfunction.DensityFunction slot,
        String name
    ) {
        assertEquals(expected, DensityTestSupport.sample(slot), EXACT, "router slot '" + name + "'");
    }

    @Test
    void everyBuilderSlotLandsInItsOwnSlotOnTheRouter() {
        net.minecraft.world.level.levelgen.NoiseRouter router = routerWithDistinctSlots();
        assertSlot(1.0, router.temperature(), "temperature");
        assertSlot(2.0, router.vegetation(), "vegetation");
        assertSlot(3.0, router.continents(), "continents");
        assertSlot(4.0, router.erosion(), "erosion");
        assertSlot(5.0, router.depth(), "depth");
        assertSlot(6.0, router.ridges(), "ridges");
        assertSlot(7.0, router.chunkSurfaceLevel(), "chunkSurfaceLevel");
        assertSlot(8.0, router.finalDensity(), "finalDensity");
    }

    @Test
    void thePositionalFactoryAgreesWithTheBuilder() {
        net.minecraft.world.level.levelgen.NoiseRouter positional = NoiseRouter.of(
            ConstantSimpleFunction.of(1.0), ConstantSimpleFunction.of(2.0),
            ConstantSimpleFunction.of(3.0), ConstantSimpleFunction.of(4.0),
            ConstantSimpleFunction.of(5.0), ConstantSimpleFunction.of(6.0),
            ConstantSimpleFunction.of(7.0), ConstantSimpleFunction.of(8.0)
        ).asHandle();
        net.minecraft.world.level.levelgen.NoiseRouter built = routerWithDistinctSlots();
        assertSlot(DensityTestSupport.sample(positional.temperature()), built.temperature(), "temperature");
        assertSlot(DensityTestSupport.sample(positional.depth()), built.depth(), "depth");
        assertSlot(DensityTestSupport.sample(positional.chunkSurfaceLevel()), built.chunkSurfaceLevel(), "chunkSurfaceLevel");
        assertSlot(DensityTestSupport.sample(positional.finalDensity()), built.finalDensity(), "finalDensity");
    }

    @Test
    void unsetSlotsFallBackToZeroRatherThanNull() {
        net.minecraft.world.level.levelgen.NoiseRouter sparse = NoiseRouter.builder()
            .temperature(ConstantSimpleFunction.of(5.0))
            .build()
            .asHandle();
        assertSlot(5.0, sparse.temperature(), "temperature");
        assertSlot(0.0, sparse.continents(), "continents");
        assertSlot(0.0, sparse.finalDensity(), "finalDensity");
    }
}
