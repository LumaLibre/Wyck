package dev.wyck.worldgen.noise;

import dev.wyck.worldgen.function.DensityFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Constructor;

@NullMarked
@ApiStatus.Internal
public record AquiferSettingsImpl(
    @Override DensityFunction barrier,
    @Override DensityFunction fluidLevelFloodedness,
    @Override DensityFunction fluidLevelSpread,
    @Override DensityFunction lava,
    @Override DensityFunction exclusion,
    @Override DensityFunction surfaceLevel
) implements AquiferSettings {
    @Override
    public Object toMinecraft() {
        try {
            Class<?> type = Class.forName("net.minecraft.world.level.levelgen.Aquifer$Config");
            Constructor<?> constructor = type.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return constructor.newInstance(
                barrier.asHandle(), fluidLevelFloodedness.asHandle(), fluidLevelSpread.asHandle(),
                lava.asHandle(), exclusion.asHandle(), surfaceLevel.asHandle()
            );
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to construct the Minecraft aquifer configuration", exception);
        }
    }
}
