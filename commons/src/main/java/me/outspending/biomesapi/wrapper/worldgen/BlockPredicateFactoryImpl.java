package me.outspending.biomesapi.wrapper.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class BlockPredicateFactoryImpl implements BlockPredicate.Factory {

    @Override
    public @NotNull Object toNms(@NotNull BlockPredicate predicate) {
        return switch (predicate) {
            case BlockPredicate.MatchingBlocks matching -> buildMatchingBlocks(matching);
            case BlockPredicate.MatchingBlockTag matching -> buildMatchingTag(matching);
            case BlockPredicate.MatchingFluids matching -> buildMatchingFluids(matching);
            case BlockPredicate.HasSturdyFace sturdy -> buildSturdyFace(sturdy);
            case BlockPredicate.Replaceable replaceable ->
                    net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.replaceable(WorldgenConversions.toVec3i(replaceable.offset()));
            case BlockPredicate.WouldSurvive survive -> buildWouldSurvive(survive);
            case BlockPredicate.InsideWorldBounds bounds ->
                    net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.insideWorld(WorldgenConversions.toVec3i(bounds.offset()));
            case BlockPredicate.Unobstructed unobstructed ->
                    net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.unobstructed(WorldgenConversions.toVec3i(unobstructed.offset()));
            case BlockPredicate.AnyOf anyOf ->
                    net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.anyOf(toNmsList(anyOf.predicates()));
            case BlockPredicate.AllOf allOf ->
                    net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.allOf(toNmsList(allOf.predicates()));
            case BlockPredicate.Not not ->
                    net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.not(toNmsPredicate(not.predicate()));
            case BlockPredicate.True ignored ->
                    net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.alwaysTrue();
        };
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate buildMatchingBlocks(@NotNull BlockPredicate.MatchingBlocks matching) {
        net.minecraft.core.Vec3i offset = WorldgenConversions.toVec3i(matching.offset());
        List<net.minecraft.world.level.block.Block> blocks = WorldgenConversions.toBlockList(matching.blocks());
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesBlocks(offset, blocks);
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate buildMatchingTag(@NotNull BlockPredicate.MatchingBlockTag matching) {
        net.minecraft.core.Vec3i offset = WorldgenConversions.toVec3i(matching.offset());
        NamespacedKey key = matching.tag().getKey();

        // mapping-sensitive: ResourceLocation.fromNamespaceAndPath / TagKey.create
        net.minecraft.resources.Identifier location =
                net.minecraft.resources.Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey());
        net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> tagKey =
                net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, location);

        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesTag(offset, tagKey);
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate buildMatchingFluids(@NotNull BlockPredicate.MatchingFluids matching) {
        net.minecraft.core.Vec3i offset = WorldgenConversions.toVec3i(matching.offset());
        List<net.minecraft.world.level.material.Fluid> fluids = new ArrayList<>(matching.fluids().size());
        for (FluidType fluid : matching.fluids()) {
            fluids.add(toNmsFluid(fluid));
        }

        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesFluids(offset, fluids);
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate buildSturdyFace(@NotNull BlockPredicate.HasSturdyFace sturdy) {
        net.minecraft.core.Vec3i offset = WorldgenConversions.toVec3i(sturdy.offset());
        net.minecraft.core.Direction direction = WorldgenConversions.toNmsDirection(sturdy.direction());
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.hasSturdyFace(offset, direction);
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate buildWouldSurvive(@NotNull BlockPredicate.WouldSurvive survive) {
        net.minecraft.core.Vec3i offset = WorldgenConversions.toVec3i(survive.offset());
        net.minecraft.world.level.block.state.BlockState state = ((CraftBlockData) survive.state()).getState();
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.wouldSurvive(state, offset);
    }

    private net.minecraft.world.level.material.Fluid toNmsFluid(@NotNull FluidType fluid) {
        // mapping-sensitive: registry value lookup
        net.minecraft.resources.Identifier location =
                net.minecraft.resources.Identifier.withDefaultNamespace(fluid.key());
        net.minecraft.world.level.material.Fluid resolved =
                net.minecraft.core.registries.BuiltInRegistries.FLUID.getValue(location);
        if (resolved == null) {
            throw new IllegalArgumentException("Fluid " + fluid.key() + " does not resolve in the fluid registry");
        }
        return resolved;
    }

    private List<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate> toNmsList(@NotNull List<BlockPredicate> predicates) {
        List<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate> converted = new ArrayList<>(predicates.size());
        for (BlockPredicate predicate : predicates) {
            converted.add(toNmsPredicate(predicate));
        }
        return converted;
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate toNmsPredicate(@NotNull BlockPredicate predicate) {
        return (net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate) predicate.toMinecraft();
    }
}