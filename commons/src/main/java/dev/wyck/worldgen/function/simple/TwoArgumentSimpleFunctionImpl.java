package dev.wyck.worldgen.function.simple;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.function.DensityFunction;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record TwoArgumentSimpleFunctionImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override Operation operation,
    @Override DensityFunction first,
    @Override DensityFunction second
) implements TwoArgumentSimpleFunction {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.DENSITY_FUNCTION);

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.DensityFunction f1 = this.first.asHandle();
        net.minecraft.world.level.levelgen.DensityFunction f2 = this.second.asHandle();

        return switch (operation) {
            case ADD -> net.minecraft.world.level.levelgen.DensityFunctions.add(f1, f2);
            case MUL -> net.minecraft.world.level.levelgen.DensityFunctions.mul(f1, f2);
            case MIN -> net.minecraft.world.level.levelgen.DensityFunctions.min(f1, f2);
            case MAX -> net.minecraft.world.level.levelgen.DensityFunctions.max(f1, f2);
        };
    }

    @Override
    public TwoArgumentSimpleFunction register() {
        ResourceKey key = this.resourceKey.orElseThrow();
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }
}
