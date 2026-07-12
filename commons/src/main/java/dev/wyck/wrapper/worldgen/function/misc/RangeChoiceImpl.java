package dev.wyck.wrapper.worldgen.function.misc;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.wrapper.worldgen.function.DensityFunction;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record RangeChoiceImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override DensityFunction input,
    @Override double minInclusive,
    @Override double maxExclusive,
    @Override DensityFunction whenInRange,
    @Override DensityFunction whenOutOfRange
) implements RangeChoice {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.DENSITY_FUNCTION);

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.levelgen.DensityFunction unwrappedInput = this.input.asHandle();
        net.minecraft.world.level.levelgen.DensityFunction unwrappedInRange = this.whenInRange.asHandle();
        net.minecraft.world.level.levelgen.DensityFunction unwrappedOutOfRange = this.whenOutOfRange.asHandle();
        return net.minecraft.world.level.levelgen.DensityFunctions.rangeChoice(
            unwrappedInput,
            this.minInclusive,
            this.maxExclusive,
            unwrappedInRange,
            unwrappedOutOfRange
        );
    }

    @Override
    public RangeChoice register() {
        ResourceKey key = this.resourceKey.orElseThrow();
        REGISTRY.get().register(key, this);
        return this;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }
}
