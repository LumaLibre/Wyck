package me.outspending.biomesapi.wrapper.worldgen;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * Wraps the BlockPredicate family, the positional block tests used by
 * placement modifiers.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface BlockPredicate extends NmsHandle permits BlockPredicate.AllOf, BlockPredicate.AnyOf, BlockPredicate.HasSturdyFace, BlockPredicate.InsideWorldBounds, BlockPredicate.MatchingBiomes, BlockPredicate.MatchingBlockTag, BlockPredicate.MatchingBlocks, BlockPredicate.MatchingFluids, BlockPredicate.Not, BlockPredicate.Replaceable, BlockPredicate.True, BlockPredicate.Unobstructed, BlockPredicate.WouldSurvive {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.*?.wrapper.worldgen.BlockPredicateFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(BlockPredicate predicate);
    }

    private static BlockVector zero() {
        return new BlockVector(0, 0, 0);
    }

    @AsOf("2.3.0")
    static BlockPredicate matchesBlocks(BlockVector offset, List<Material> blocks) {
        return new MatchingBlocks(offset, blocks);
    }

    @AsOf("2.3.0")
    static BlockPredicate matchesBlocks(List<Material> blocks) {
        return new MatchingBlocks(zero(), blocks);
    }

    @AsOf("2.3.0")
    static BlockPredicate matchesBlocks(Material... blocks) {
        return new MatchingBlocks(zero(), List.of(blocks));
    }

    @AsOf("2.3.0")
    static BlockPredicate matchesTag(BlockVector offset, Tag<Material> tag) {
        return new MatchingBlockTag(offset, tag);
    }

    @AsOf("2.3.0")
    static BlockPredicate matchesTag(Tag<Material> tag) {
        return new MatchingBlockTag(zero(), tag);
    }

    @AsOf("2.3.0")
    static BlockPredicate matchesFluids(BlockVector offset, List<FluidType> fluids) {
        return new MatchingFluids(offset, fluids);
    }

    @AsOf("2.3.0")
    static BlockPredicate matchesFluids(FluidType... fluids) {
        return new MatchingFluids(zero(), List.of(fluids));
    }

    @AsOf("2.3.0")
    static BlockPredicate noFluid() {
        return new MatchingFluids(zero(), List.of(FluidType.EMPTY));
    }

    @AsOf("2.3.0")
    static BlockPredicate noFluid(BlockVector offset) {
        return new MatchingFluids(offset, List.of(FluidType.EMPTY));
    }

    @AsOf("2.3.0")
    static BlockPredicate hasSturdyFace(BlockVector offset, BlockFace direction) {
        return new HasSturdyFace(offset, direction);
    }

    @AsOf("2.3.0")
    static BlockPredicate hasSturdyFace(BlockFace direction) {
        return new HasSturdyFace(zero(), direction);
    }

    @AsOf("2.3.0")
    static BlockPredicate replaceable() {
        return new Replaceable(zero());
    }

    @AsOf("2.3.0")
    static BlockPredicate replaceable(BlockVector offset) {
        return new Replaceable(offset);
    }

    @AsOf("2.3.0")
    static BlockPredicate wouldSurvive(BlockData state) {
        return new WouldSurvive(zero(), state);
    }

    @AsOf("2.3.0")
    static BlockPredicate wouldSurvive(BlockVector offset, BlockData state) {
        return new WouldSurvive(offset, state);
    }

    @AsOf("2.3.0")
    static BlockPredicate insideWorld(BlockVector offset) {
        return new InsideWorldBounds(offset);
    }

    @AsOf("2.3.0")
    static BlockPredicate unobstructed() {
        return new Unobstructed(zero());
    }

    @AsOf("2.3.0")
    static BlockPredicate unobstructed(BlockVector offset) {
        return new Unobstructed(offset);
    }

    @AsOf("2.3.0")
    static BlockPredicate anyOf(List<BlockPredicate> predicates) {
        return new AnyOf(predicates);
    }

    @AsOf("2.3.0")
    static BlockPredicate anyOf(BlockPredicate... predicates) {
        return new AnyOf(List.of(predicates));
    }

    @AsOf("2.3.0")
    static BlockPredicate allOf(List<BlockPredicate> predicates) {
        return new AllOf(predicates);
    }

    @AsOf("2.3.0")
    static BlockPredicate allOf(BlockPredicate... predicates) {
        return new AllOf(List.of(predicates));
    }

    @AsOf("2.3.0")
    static BlockPredicate not(BlockPredicate predicate) {
        return new Not(predicate);
    }

    @AsOf("2.3.0")
    static BlockPredicate alwaysTrue() {
        return new True();
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record MatchingBlocks(BlockVector offset, List<Material> blocks) implements BlockPredicate {

        @AsOf("2.3.0")
        public MatchingBlocks {
            Preconditions.checkNotNull(offset, "offset");
            blocks = List.copyOf(blocks);
        }
    }

    @AsOf("2.3.0")
    record MatchingBlockTag(BlockVector offset, Tag<Material> tag) implements BlockPredicate {

        @AsOf("2.3.0")
        public MatchingBlockTag {
            Preconditions.checkNotNull(offset, "offset");
            Preconditions.checkNotNull(tag, "tag");
        }
    }

    @AsOf("2.3.0")
    record MatchingFluids(BlockVector offset, List<FluidType> fluids) implements BlockPredicate {

        @AsOf("2.3.0")
        public MatchingFluids {
            Preconditions.checkNotNull(offset, "offset");
            fluids = List.copyOf(fluids);
        }
    }

    @AsOf("2.3.0")
    record HasSturdyFace(BlockVector offset, BlockFace direction) implements BlockPredicate {

        @AsOf("2.3.0")
        public HasSturdyFace {
            Preconditions.checkNotNull(offset, "offset");
            Preconditions.checkNotNull(direction, "direction");
        }
    }

    @AsOf("2.3.0")
    record Replaceable(BlockVector offset) implements BlockPredicate {

        @AsOf("2.3.0")
        public Replaceable {
            Preconditions.checkNotNull(offset, "offset");
        }
    }

    @AsOf("2.3.0")
    record WouldSurvive(BlockVector offset, BlockData state) implements BlockPredicate {

        @AsOf("2.3.0")
        public WouldSurvive {
            Preconditions.checkNotNull(offset, "offset");
            Preconditions.checkNotNull(state, "state");
        }
    }

    @AsOf("2.3.0")
    record InsideWorldBounds(BlockVector offset) implements BlockPredicate {

        @AsOf("2.3.0")
        public InsideWorldBounds {
            Preconditions.checkNotNull(offset, "offset");
        }
    }

    @AsOf("2.3.0")
    record Unobstructed(BlockVector offset) implements BlockPredicate {

        @AsOf("2.3.0")
        public Unobstructed {
            Preconditions.checkNotNull(offset, "offset");
        }
    }

    // recursive
    @AsOf("2.3.0")
    record AnyOf(List<BlockPredicate> predicates) implements BlockPredicate {

        @AsOf("2.3.0")
        public AnyOf {
            predicates = List.copyOf(predicates);
        }
    }

    // recursive
    @AsOf("2.3.0")
    record AllOf(List<BlockPredicate> predicates) implements BlockPredicate {

        @AsOf("2.3.0")
        public AllOf {
            predicates = List.copyOf(predicates);
        }
    }

    // recursive
    @AsOf("2.3.0")
    record Not(BlockPredicate predicate) implements BlockPredicate {

        @AsOf("2.3.0")
        public Not {
            Preconditions.checkNotNull(predicate, "predicate");
        }
    }

    @AsOf("2.3.0")
    record True() implements BlockPredicate {}

    @AsOf("2.3.0")
    record MatchingBiomes(List<Biome> biomes) implements BlockPredicate {

        @AsOf("2.3.0")
        public MatchingBiomes {
            biomes = List.copyOf(biomes);
        }
    }
}