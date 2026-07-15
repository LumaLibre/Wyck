package dev.wyck.worldgen.carver.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.DatapackPromotion;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.carver.CarverConfiguration;
import dev.wyck.worldgen.carver.WorldCarverType;
import dev.wyck.worldgen.carver.types.ComposedCarver;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.NoSuchElementException;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ComposedCarverImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override WorldCarverType type,
    @Override CarverConfiguration config
) implements ComposedCarver {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.CONFIGURED_CARVER);

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Object toMinecraft() {
        if (DatapackPromotion.isReferenceMode()) {
            return DatapackPromotion.current().reference(this, net.minecraft.core.registries.Registries.CONFIGURED_CARVER);
        }

        net.minecraft.world.level.levelgen.carver.WorldCarver worldCarver = this.type.toNms();
        net.minecraft.world.level.levelgen.carver.CarverConfiguration nmsConfig = this.config.asHandle();

        net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?> configured =
            new net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<>(worldCarver, nmsConfig);


        if (DatapackPromotion.isCollectMode()) {
            DatapackPromotion.current().collectCarver(this, configured);
        }
        return net.minecraft.core.Holder.direct(configured);
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public ComposedCarver register() {
        ResourceKey key = this.resourceKey.orElseThrow(() -> new NoSuchElementException("Cannot register a composed carver without a resource key."));
        REGISTRY.get().register(key, this);
        return this;
    }
}
