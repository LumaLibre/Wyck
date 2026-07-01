package me.outspending.biomesapi.wrapper.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.material.Fluid;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public class BlockPredicateFactoryImpl implements BlockPredicate.Factory {

    private static final Logger LOGGER = Logger.getLogger(BlockPredicateFactoryImpl.class.getName());

    @Override
    public Object toNms(BlockPredicate predicate) {
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
                net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.not((net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate) not.predicate().toMinecraft());
            case BlockPredicate.True _ ->
                net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.alwaysTrue();
            case BlockPredicate.MatchingBiomes matchingBiomes -> matchingBiomes(matchingBiomes);
        };
    }

    @Override
    public BlockPredicate fromMinecraft(Object nms) {
        net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate predicate =
            (net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate) nms;
        BlockPredicateType<?> type = predicate.type();

        if (type == BlockPredicateType.MATCHING_BLOCKS) {
            return new BlockPredicate.MatchingBlocks(offsetVector(predicate), WorldgenConversions.fromBlockHolderSet(InternalReflectUtil.getFieldValue(predicate, "blocks")));
        }
        if (type == BlockPredicateType.MATCHING_BLOCK_TAG) {
            TagKey<Block> tag = InternalReflectUtil.getFieldValue(predicate, "tag");
            Tag<Material> bukkitTag = WorldgenConversions.toBukkitMaterialTag(tag);
            return new BlockPredicate.MatchingBlockTag(offsetVector(predicate), bukkitTag);
        }
        if (type == BlockPredicateType.MATCHING_FLUIDS) {
            HolderSet<Fluid> fluids = InternalReflectUtil.getFieldValue(predicate, "fluids");
            List<FluidType> wrapped = new ArrayList<>(fluids.size());
            for (Holder<Fluid> holder : fluids) {
                wrapped.add(FluidType.TRANSLATOR.fromNms(holder.value()));
            }

            return new BlockPredicate.MatchingFluids(offsetVector(predicate), wrapped);
        }
        if (type == BlockPredicateType.HAS_STURDY_FACE) {
            return new BlockPredicate.HasSturdyFace(offsetVector(predicate), CraftBlock.notchToBlockFace(InternalReflectUtil.getFieldValue(predicate, "direction")));
        }
        if (type == BlockPredicateType.REPLACEABLE) {
            return new BlockPredicate.Replaceable(offsetVector(predicate));
        }
        if (type == BlockPredicateType.WOULD_SURVIVE) {
            BlockState state = InternalReflectUtil.getFieldValue(predicate, "state");
            return new BlockPredicate.WouldSurvive(offsetVector(predicate), CraftBlockData.createData(state));
        }
        if (type == BlockPredicateType.INSIDE_WORLD_BOUNDS) {
            return new BlockPredicate.InsideWorldBounds(offsetVector(predicate));
        }
        if (type == BlockPredicateType.UNOBSTRUCTED) {
            return new BlockPredicate.Unobstructed(offsetVector(predicate));
        }
        if (type == BlockPredicateType.ANY_OF) {
            return new BlockPredicate.AnyOf(fromNmsList(InternalReflectUtil.getFieldValue(predicate, "predicates")));
        }
        if (type == BlockPredicateType.ALL_OF) {
            return new BlockPredicate.AllOf(fromNmsList(InternalReflectUtil.getFieldValue(predicate, "predicates")));
        }
        if (type == BlockPredicateType.NOT) {
            return new BlockPredicate.Not(fromMinecraft(InternalReflectUtil.getFieldValue(predicate, "predicate")));
        }
        if (type == BlockPredicateType.TRUE) {
            return new BlockPredicate.True();
        }

        return fromUnsupported(predicate);
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate buildMatchingBlocks(BlockPredicate.MatchingBlocks matching) {
        net.minecraft.core.Vec3i offset = WorldgenConversions.toVec3i(matching.offset());
        List<net.minecraft.world.level.block.Block> blocks = WorldgenConversions.toBlockList(matching.blocks());
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesBlocks(offset, blocks);
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate buildMatchingTag(BlockPredicate.MatchingBlockTag matching) {
        net.minecraft.core.Vec3i offset = WorldgenConversions.toVec3i(matching.offset());
        NamespacedKey key = matching.tag().getKey();

        net.minecraft.resources.Identifier location =
            net.minecraft.resources.Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey());
        net.minecraft.tags.TagKey<net.minecraft.world.level.block.Block> tagKey =
            net.minecraft.tags.TagKey.create(net.minecraft.core.registries.Registries.BLOCK, location);

        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesTag(offset, tagKey);
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate buildMatchingFluids(BlockPredicate.MatchingFluids matching) {
        net.minecraft.core.Vec3i offset = WorldgenConversions.toVec3i(matching.offset());
        List<net.minecraft.world.level.material.Fluid> fluids = new ArrayList<>(matching.fluids().size());
        for (FluidType fluid : matching.fluids()) {
            fluids.add(fluid.toNms());
        }

        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesFluids(offset, fluids);
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate buildSturdyFace(BlockPredicate.HasSturdyFace sturdy) {
        net.minecraft.core.Vec3i offset = WorldgenConversions.toVec3i(sturdy.offset());
        net.minecraft.core.Direction direction = WorldgenConversions.toNmsDirection(sturdy.direction());
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.hasSturdyFace(offset, direction);
    }

    private net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate buildWouldSurvive(BlockPredicate.WouldSurvive survive) {
        net.minecraft.core.Vec3i offset = WorldgenConversions.toVec3i(survive.offset());
        net.minecraft.world.level.block.state.BlockState state = ((CraftBlockData) survive.state()).getState();
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.wouldSurvive(state, offset);
    }

    private List<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate> toNmsList(List<BlockPredicate> predicates) {
        List<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate> converted = new ArrayList<>(predicates.size());
        for (BlockPredicate predicate : predicates) {
            converted.add((net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate) predicate.toMinecraft());
        }
        return converted;
    }

    private List<BlockPredicate> fromNmsList(List<net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate> nms) {
        List<BlockPredicate> converted = new ArrayList<>(nms.size());
        for (net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate predicate : nms) {
            converted.add(fromMinecraft(predicate));
        }
        return converted;
    }

    private static BlockVector offsetVector(Object predicate) {
        return WorldgenConversions.toBlockVector(InternalReflectUtil.getFieldValue(predicate, "offset"));
    }

    protected Object matchingBiomes(BlockPredicate.MatchingBiomes matching) {
        LOGGER.warning("BlockPredicate.MatchingBiomes is not supported in this version, defaulting to alwaysTrue()");
        return net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.alwaysTrue();
    }

    protected BlockPredicate fromUnsupported(net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate predicate) {
        throw new IllegalArgumentException("Unsupported block predicate '" + predicate.getClass().getName() + "'");
    }
}