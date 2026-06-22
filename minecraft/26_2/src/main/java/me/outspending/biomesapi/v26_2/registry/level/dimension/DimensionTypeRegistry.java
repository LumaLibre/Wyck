package me.outspending.biomesapi.v26_2.registry.level.dimension;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.level.dimension.Dimension;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.registry.bootstrap.util.BootstrapSafeMinecraftRegistries;
import me.outspending.biomesapi.registry.internal.FrozenRegistry;
import me.outspending.biomesapi.registry.level.dimension.DimensionRegistry;
import me.outspending.biomesapi.util.Lazy;
import me.outspending.biomesapi.wrapper.entity.data.MonsterSettings;
import me.outspending.biomesapi.wrapper.environment.attribute.NmsEnvironmentAttributes;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import me.outspending.biomesapi.wrapper.level.dimension.InfiniburnImpl;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.clock.WorldClock;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.timeline.Timeline;
import org.jspecify.annotations.NonNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;

import java.lang.reflect.Method;
import java.util.Optional;


@NullMarked
@WireFactory
@AsOf("2.4.0")
@SuppressWarnings("unchecked")
public class DimensionTypeRegistry implements DimensionRegistry {

    private final Lazy<FrozenRegistry> dimensionTypeRegistry = FrozenRegistry.lazy("dimension_type");

    @Override
    @AsOf("2.4.0")
    public DimensionType buildDelegate(Dimension dimension) {
        Preconditions.checkNotNull(dimension, "dimension cannot be null");

        MonsterSettings ms = dimension.getMonsterSettings();
        DimensionType.MonsterSettings monsterSettings = new DimensionType.MonsterSettings(
            (IntProvider) ms.monsterSpawnLightTest().toMinecraft(),
            ms.monsterSpawnBlockLightLimit()
        );

        EnvironmentAttributeMap.Builder attributeBuilder = EnvironmentAttributeMap.builder();
        WrappedEnvironmentAttributeMap attributes = dimension.getAttributes();
        NmsEnvironmentAttributes.applyTo(attributeBuilder, attributes);

        InfiniburnImpl infiniburn = (InfiniburnImpl) dimension.getInfiniburn();

        return new DimensionType(
            dimension.hasFixedTime(),
            dimension.hasSkyLight(),
            dimension.hasCeiling(),
            dimension.hasEnderDragonFight(),
            dimension.getCoordinateScale(),
            dimension.getMinY(),
            dimension.getHeight(),
            dimension.getLogicalHeight(),
            infiniburn.asHolderSet(),
            dimension.getAmbientLight(),
            monsterSettings,
            dimension.getSkybox().toNms(net.minecraft.world.level.dimension.DimensionType.Skybox.class),
            dimension.getCardinalLightType().toNms(CardinalLighting.Type.class),
            attributeBuilder.build(),
            (HolderSet<@NonNull Timeline>) dimension.getTimelines().toMinecraft(),
            toNmsDefaultClock(dimension.getDefaultClock())
        );
    }

    @Override
    @AsOf("2.4.0")
    @SuppressWarnings("unchecked")
    public void register(Dimension dimension) {
        Preconditions.checkNotNull(dimension, "dimension cannot be null");

        Identifier location = (Identifier) dimension.getResourceKey().toMinecraft();
        net.minecraft.world.level.dimension.DimensionType built = buildDelegate(dimension);

        dimensionTypeRegistry.get().whileUnfrozen(() -> {
            Registry<net.minecraft.world.level.dimension.DimensionType> registry = (Registry<net.minecraft.world.level.dimension.DimensionType>) dimensionTypeRegistry.get().toMinecraft();
            if (!registry.containsKey(location)) {
                Registry.register(registry, location, built);
            }
        });
    }

    //@Override
    @AsOf("2.4.0")
    public void modify(Dimension dimension) {
        Preconditions.checkNotNull(dimension, "dimension cannot be null");
        ResourceKey key = dimension.getResourceKey();
        Registry<net.minecraft.world.level.dimension.DimensionType> registry = (Registry<net.minecraft.world.level.dimension.DimensionType>) dimensionTypeRegistry.get().toMinecraft();
        Preconditions.checkArgument(registry.containsKey((Identifier) key.resourceLocation()),
                "Dimension type %s is not registered", key);
        modify(key, dimension);
    }

    //@Override
    @AsOf("2.4.0")
    public void modify(ResourceKey key, Dimension newData) {
        Preconditions.checkNotNull(key, "key cannot be null");
        Preconditions.checkNotNull(newData, "newData cannot be null");

        throw new UnsupportedOperationException("Not yet implemented");
        //TODO
//        RegistryHandle handle = RegistryHandle.dimensionType();
//        Identifier location = (Identifier) key.toMinecraft();
//        DimensionType rebuilt = buildDelegate(newData);
//
//        handle.modify(() -> {
//            Registry<DimensionType> registry = (Registry<@NonNull DimensionType>) handle.toMinecraft();
//            Holder.Reference<DimensionType> reference = registry.get(location).orElseThrow(
//                    () -> new IllegalStateException("Dimension type " + key + " is not registered"));
//            // DimensionType is an immutable record, so unlike a Biome we cannot reflect into
//            // its fields in place, instead we rebind the holder to the freshly-built value.
//            rebindHolder(reference, rebuilt);
//        });
    }


    private static void rebindHolder(Holder.Reference<net.minecraft.world.level.dimension.DimensionType> reference, net.minecraft.world.level.dimension.DimensionType value) {
        try {
            Method bindValue = Holder.Reference.class.getDeclaredMethod("bindValue", Object.class);
            bindValue.setAccessible(true);
            bindValue.invoke(reference, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to rebind dimension type holder", e);
        }
    }

    // TODO: this sucks — needs to be moved to NMSHandle/wrapper
    private static Optional<Holder<WorldClock>> toNmsDefaultClock(@Nullable ResourceKey key) {
        if (key == null) {
            return Optional.empty();
        }
        Registry<WorldClock> registry = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.WORLD_CLOCK);
        Identifier id = (Identifier) key.resourceLocation();
        return Optional.of(registry.get(id).orElseThrow(() -> new IllegalArgumentException("Unknown world clock: " + id)));
    }
}