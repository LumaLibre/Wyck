package dev.wyck.test.bootstrap;

import dev.wyck.worldgen.function.simple.ConstantSimpleFunction;
import dev.wyck.worldgen.noise.NoiseRouter;
import net.minecraft.world.level.levelgen.DensityFunction.SinglePointContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MinecraftBootstrap.class)
class NoiseRouterTest {

    private static final SinglePointContext ORIGIN = new SinglePointContext(0, 0, 0);
    private static final double EXACT = 0.0;

    private static net.minecraft.world.level.levelgen.NoiseRouter routerWithDistinctSlots() {
        return NoiseRouter.builder()
                .barrier(ConstantSimpleFunction.of(1.0))
                .fluidLevelFloodedness(ConstantSimpleFunction.of(2.0))
                .fluidLevelSpread(ConstantSimpleFunction.of(3.0))
                .lava(ConstantSimpleFunction.of(4.0))
                .temperature(ConstantSimpleFunction.of(5.0))
                .vegetation(ConstantSimpleFunction.of(6.0))
                .continents(ConstantSimpleFunction.of(7.0))
                .erosion(ConstantSimpleFunction.of(8.0))
                .depth(ConstantSimpleFunction.of(9.0))
                .ridges(ConstantSimpleFunction.of(10.0))
                .preliminarySurfaceLevel(ConstantSimpleFunction.of(11.0))
                .finalDensity(ConstantSimpleFunction.of(12.0))
                .veinToggle(ConstantSimpleFunction.of(13.0))
                .veinRidged(ConstantSimpleFunction.of(14.0))
                .veinGap(ConstantSimpleFunction.of(15.0))
                .build()
                .asHandle();
    }

    private static void assertSlot(double expected, net.minecraft.world.level.levelgen.DensityFunction slot, String name) {
        assertEquals(expected, slot.compute(ORIGIN), EXACT,
                () -> "router slot '" + name + "' carries the function meant for slot #" + (int) slot.compute(ORIGIN));
    }

    @Test
    void everyBuilderSlotLandsInItsOwnSlotOnTheRouter() {
        net.minecraft.world.level.levelgen.NoiseRouter nms = routerWithDistinctSlots();

        assertSlot(1.0, nms.barrierNoise(), "barrier");
        assertSlot(2.0, nms.fluidLevelFloodednessNoise(), "fluidLevelFloodedness");
        assertSlot(3.0, nms.fluidLevelSpreadNoise(), "fluidLevelSpread");
        assertSlot(4.0, nms.lavaNoise(), "lava");
        assertSlot(5.0, nms.temperature(), "temperature");
        assertSlot(6.0, nms.vegetation(), "vegetation");
        assertSlot(7.0, nms.continents(), "continents");
        assertSlot(8.0, nms.erosion(), "erosion");
        assertSlot(9.0, nms.depth(), "depth");
        assertSlot(10.0, nms.ridges(), "ridges");
        assertSlot(11.0, nms.preliminarySurfaceLevel(), "preliminarySurfaceLevel");
        assertSlot(12.0, nms.finalDensity(), "finalDensity");
        assertSlot(13.0, nms.veinToggle(), "veinToggle");
        assertSlot(14.0, nms.veinRidged(), "veinRidged");
        assertSlot(15.0, nms.veinGap(), "veinGap");
    }

    @Test
    void thePositionalFactoryAgreesWithTheBuilder() {
        net.minecraft.world.level.levelgen.NoiseRouter positional = NoiseRouter.of(
                ConstantSimpleFunction.of(1.0),
                ConstantSimpleFunction.of(2.0),
                ConstantSimpleFunction.of(3.0),
                ConstantSimpleFunction.of(4.0),
                ConstantSimpleFunction.of(5.0),
                ConstantSimpleFunction.of(6.0),
                ConstantSimpleFunction.of(7.0),
                ConstantSimpleFunction.of(8.0),
                ConstantSimpleFunction.of(9.0),
                ConstantSimpleFunction.of(10.0),
                ConstantSimpleFunction.of(11.0),
                ConstantSimpleFunction.of(12.0),
                ConstantSimpleFunction.of(13.0),
                ConstantSimpleFunction.of(14.0),
                ConstantSimpleFunction.of(15.0)
        ).asHandle();
        net.minecraft.world.level.levelgen.NoiseRouter built = routerWithDistinctSlots();

        assertSlot(positional.barrierNoise().compute(ORIGIN), built.barrierNoise(), "barrier");
        assertSlot(positional.temperature().compute(ORIGIN), built.temperature(), "temperature");
        assertSlot(positional.depth().compute(ORIGIN), built.depth(), "depth");
        assertSlot(positional.finalDensity().compute(ORIGIN), built.finalDensity(), "finalDensity");
        assertSlot(positional.veinGap().compute(ORIGIN), built.veinGap(), "veinGap");
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
