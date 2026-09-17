package dev.wyck.worldgen.structure.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.worldgen.structure.pools.DimensionPadding;
import dev.wyck.worldgen.structure.pools.TemplatePool;
import dev.wyck.worldgen.structure.pools.alias.PoolAliasBinding;
import dev.wyck.worldgen.structure.templatesystem.LiquidSettings;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record JigsawStructureImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override StructureSettings settings,
    @Override TemplatePool startPool,
    @Override Optional<ResourceKey> startJigsawName,
    @Override int size,
    @Override HeightProvider startHeight,
    @Override boolean useExpansionHack,
    @Override Optional<HeightmapType> projectStartToHeightmap,
    @Override MaxDistance maxDistanceFromCenter,
    @Override List<PoolAliasBinding> poolAliases,
    @Override DimensionPadding dimensionPadding,
    @Override LiquidSettings liquidSettings
) implements JigsawStructure {

    @Override
    public Object toMinecraft() {
        return Holder.direct(new net.minecraft.world.level.levelgen.structure.structures.JigsawStructure(
            this.settings.asHandle(),
            this.startPool.<Holder<StructureTemplatePool>>asHandle(),
            this.startJigsawName.map(ResourceKey::identifier),
            this.size,
            this.startHeight.asHandle(),
            this.useExpansionHack,
            this.projectStartToHeightmap.map(it -> it.toNms(Heightmap.Types.class)),
            new net.minecraft.world.level.levelgen.structure.structures.JigsawStructure.MaxDistance(
                this.maxDistanceFromCenter.horizontal(), this.maxDistanceFromCenter.vertical()
            ),
            this.poolAliases.stream().map(JigsawStructureImpl::alias).toList(),
            new net.minecraft.world.level.levelgen.structure.pools.DimensionPadding(this.dimensionPadding.bottom(), this.dimensionPadding.top()),
            this.liquidSettings.toNms(net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings.class)
        ));
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public JigsawStructure register() {
        StructureRegistration.register(this.resourceKey, this);
        return this;
    }

    private static net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding alias(PoolAliasBinding binding) {
        return switch (binding) {
            case PoolAliasBinding.Direct(TemplatePool alias, TemplatePool target) ->
                new net.minecraft.world.level.levelgen.structure.pools.alias.DirectPoolAlias(poolKey(alias), poolKey(target));
            case PoolAliasBinding.Random(TemplatePool alias, WeightedList<TemplatePool> targets) ->
                new net.minecraft.world.level.levelgen.structure.pools.alias.RandomPoolAlias(poolKey(alias), weighted(targets, JigsawStructureImpl::poolKey));
            case PoolAliasBinding.RandomGroup(WeightedList<List<PoolAliasBinding>> groups) ->
                new net.minecraft.world.level.levelgen.structure.pools.alias.RandomGroupPoolAlias(
                    weighted(groups, group -> group.stream().map(JigsawStructureImpl::alias).toList())
                );
        };
    }

    private static net.minecraft.resources.ResourceKey<StructureTemplatePool> poolKey(TemplatePool pool) {
        Key key = pool.key();
        return net.minecraft.resources.ResourceKey.create(
            Registries.TEMPLATE_POOL, net.minecraft.resources.Identifier.fromNamespaceAndPath(key.namespace(), key.value())
        );
    }

    private static <W, N> net.minecraft.util.random.WeightedList<N> weighted(WeightedList<W> list, java.util.function.Function<W, N> convert) {
        net.minecraft.util.random.WeightedList.Builder<N> builder = net.minecraft.util.random.WeightedList.builder();
        for (WeightedList.Weighted<W> entry : list.unwrap()) {
            builder.add(convert.apply(entry.value()), entry.weight());
        }
        return builder.build();
    }
}
