package dev.wyck.decode.worldgen.structure.pools;

import com.mojang.datafixers.util.Either;
import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.structure.pools.PoolElement;
import dev.wyck.worldgen.structure.pools.Projection;
import dev.wyck.worldgen.structure.pools.elements.EmptyPoolElement;
import dev.wyck.worldgen.structure.pools.elements.FeaturePoolElement;
import dev.wyck.worldgen.structure.pools.elements.LegacySinglePoolElement;
import dev.wyck.worldgen.structure.pools.elements.ListPoolElement;
import dev.wyck.worldgen.structure.pools.elements.SinglePoolElement;
import dev.wyck.worldgen.structure.templatesystem.LiquidSettings;
import dev.wyck.worldgen.structure.templatesystem.ProcessorList;
import dev.wyck.worldgen.structure.templatesystem.StructureTemplate;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class PoolElementDecoders extends DecoderRegistry<PoolElement, StructurePoolElement> {

    public PoolElementDecoders() {
        register("single_pool_element", element -> SinglePoolElement.of(
            template(element), processors(element), projection(element), liquidSettings(element)
        ));
        register("legacy_single_pool_element", element -> LegacySinglePoolElement.of(
            template(element), processors(element), projection(element), liquidSettings(element)
        ));
        register("list_pool_element", element -> ListPoolElement.of(
            projection(element),
            ((net.minecraft.world.level.levelgen.structure.pools.ListPoolElement) element).getElements().stream()
                .map(PoolElement::decode)
                .toList()
        ));
        register("feature_pool_element", element -> FeaturePoolElement.of(
            projection(element),
            PlacedFeature.decode(FastReflection.read(element, "feature"))
        ));
        register("empty_pool_element", _ -> EmptyPoolElement.INSTANCE);
    }

    @Override
    protected ResourceKey discriminate(StructurePoolElement minecraftObject) {
        return Decoders.registryKey(BuiltInRegistries.STRUCTURE_POOL_ELEMENT, minecraftObject.getType());
    }

    private static Projection projection(StructurePoolElement element) {
        return Projection.TRANSLATOR.fromNms(element.getProjection());
    }

    private static StructureTemplate template(StructurePoolElement element) {
        Either<Identifier, net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate> template =
            FastReflection.read(net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement.class, element, "template");
        Identifier location = template.left().orElseThrow(() -> new IllegalArgumentException(
            "pool element holds a template built at runtime rather than one named by identifier, which cannot be read"
        ));
        return StructureTemplate.of(Decoders.key(location));
    }

    private static ProcessorList processors(StructurePoolElement element) {
        return ProcessorList.decode(FastReflection.read(net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement.class, element, "processors"));
    }

    private static @Nullable LiquidSettings liquidSettings(StructurePoolElement element) {
        Optional<net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings> settings =
            FastReflection.read(net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement.class, element, "overrideLiquidSettings");
        return settings.map(LiquidSettings.TRANSLATOR::fromNms).orElse(null);
    }
}
