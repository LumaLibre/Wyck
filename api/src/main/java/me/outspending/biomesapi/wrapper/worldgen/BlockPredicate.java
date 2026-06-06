package me.outspending.biomesapi.wrapper.worldgen;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * Wraps the BlockPredicate family, the positional block tests used by
 * placement modifiers.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@AsOf("2.3.0")
public sealed interface BlockPredicate extends NmsHandle permits BlockPredicate.MatchingBlocks, BlockPredicate.MatchingBlockTag, BlockPredicate.MatchingFluids, BlockPredicate.HasSturdyFace, BlockPredicate.Replaceable, BlockPredicate.WouldSurvive, BlockPredicate.InsideWorldBounds, BlockPredicate.Unobstructed, BlockPredicate.AnyOf, BlockPredicate.AllOf, BlockPredicate.Not, BlockPredicate.True {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.BlockPredicateFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        @NotNull Object toNms(@NotNull BlockPredicate predicate);
    }

    private static @NotNull BlockVector zero() {
        return new BlockVector(0, 0, 0);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate matchesBlocks(@NotNull BlockVector offset, @NotNull List<Material> blocks) {
        return new MatchingBlocks(offset, blocks);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate matchesBlocks(@NotNull List<Material> blocks) {
        return new MatchingBlocks(zero(), blocks);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate matchesBlocks(@NotNull Material... blocks) {
        return new MatchingBlocks(zero(), List.of(blocks));
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate matchesTag(@NotNull BlockVector offset, @NotNull Tag<Material> tag) {
        return new MatchingBlockTag(offset, tag);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate matchesTag(@NotNull Tag<Material> tag) {
        return new MatchingBlockTag(zero(), tag);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate matchesFluids(@NotNull BlockVector offset, @NotNull List<FluidType> fluids) {
        return new MatchingFluids(offset, fluids);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate matchesFluids(@NotNull FluidType... fluids) {
        return new MatchingFluids(zero(), List.of(fluids));
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate noFluid() {
        return new MatchingFluids(zero(), List.of(FluidType.EMPTY));
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate noFluid(@NotNull BlockVector offset) {
        return new MatchingFluids(offset, List.of(FluidType.EMPTY));
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate hasSturdyFace(@NotNull BlockVector offset, @NotNull BlockFace direction) {
        return new HasSturdyFace(offset, direction);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate hasSturdyFace(@NotNull BlockFace direction) {
        return new HasSturdyFace(zero(), direction);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate replaceable() {
        return new Replaceable(zero());
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate replaceable(@NotNull BlockVector offset) {
        return new Replaceable(offset);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate wouldSurvive(@NotNull BlockData state) {
        return new WouldSurvive(zero(), state);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate wouldSurvive(@NotNull BlockVector offset, @NotNull BlockData state) {
        return new WouldSurvive(offset, state);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate insideWorld(@NotNull BlockVector offset) {
        return new InsideWorldBounds(offset);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate unobstructed() {
        return new Unobstructed(zero());
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate unobstructed(@NotNull BlockVector offset) {
        return new Unobstructed(offset);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate anyOf(@NotNull List<BlockPredicate> predicates) {
        return new AnyOf(predicates);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate anyOf(@NotNull BlockPredicate... predicates) {
        return new AnyOf(List.of(predicates));
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate allOf(@NotNull List<BlockPredicate> predicates) {
        return new AllOf(predicates);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate allOf(@NotNull BlockPredicate... predicates) {
        return new AllOf(List.of(predicates));
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate not(@NotNull BlockPredicate predicate) {
        return new Not(predicate);
    }

    @AsOf("2.3.0")
    static @NotNull BlockPredicate alwaysTrue() {
        return new True();
    }

    @Override
    @AsOf("2.3.0")
    default @NotNull Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record MatchingBlocks(@NotNull BlockVector offset, @NotNull List<Material> blocks) implements BlockPredicate {

        @AsOf("2.3.0")
        public MatchingBlocks {
            Objects.requireNonNull(offset, "offset");
            blocks = List.copyOf(blocks);
        }
    }

    @AsOf("2.3.0")
    record MatchingBlockTag(@NotNull BlockVector offset, @NotNull Tag<Material> tag) implements BlockPredicate {

        @AsOf("2.3.0")
        public MatchingBlockTag {
            Objects.requireNonNull(offset, "offset");
            Objects.requireNonNull(tag, "tag");
        }
    }

    @AsOf("2.3.0")
    record MatchingFluids(@NotNull BlockVector offset, @NotNull List<FluidType> fluids) implements BlockPredicate {

        @AsOf("2.3.0")
        public MatchingFluids {
            Objects.requireNonNull(offset, "offset");
            fluids = List.copyOf(fluids);
        }
    }

    @AsOf("2.3.0")
    record HasSturdyFace(@NotNull BlockVector offset, @NotNull BlockFace direction) implements BlockPredicate {

        @AsOf("2.3.0")
        public HasSturdyFace {
            Objects.requireNonNull(offset, "offset");
            Objects.requireNonNull(direction, "direction");
        }
    }

    @AsOf("2.3.0")
    record Replaceable(@NotNull BlockVector offset) implements BlockPredicate {

        @AsOf("2.3.0")
        public Replaceable {
            Objects.requireNonNull(offset, "offset");
        }
    }

    @AsOf("2.3.0")
    record WouldSurvive(@NotNull BlockVector offset, @NotNull BlockData state) implements BlockPredicate {

        @AsOf("2.3.0")
        public WouldSurvive {
            Objects.requireNonNull(offset, "offset");
            Objects.requireNonNull(state, "state");
        }
    }

    @AsOf("2.3.0")
    record InsideWorldBounds(@NotNull BlockVector offset) implements BlockPredicate {

        @AsOf("2.3.0")
        public InsideWorldBounds {
            Objects.requireNonNull(offset, "offset");
        }
    }

    @AsOf("2.3.0")
    record Unobstructed(@NotNull BlockVector offset) implements BlockPredicate {

        @AsOf("2.3.0")
        public Unobstructed {
            Objects.requireNonNull(offset, "offset");
        }
    }

    // recursive
    @AsOf("2.3.0")
    record AnyOf(@NotNull List<BlockPredicate> predicates) implements BlockPredicate {

        @AsOf("2.3.0")
        public AnyOf {
            predicates = List.copyOf(predicates);
        }
    }

    // recursive
    @AsOf("2.3.0")
    record AllOf(@NotNull List<BlockPredicate> predicates) implements BlockPredicate {

        @AsOf("2.3.0")
        public AllOf {
            predicates = List.copyOf(predicates);
        }
    }

    // recursive
    @AsOf("2.3.0")
    record Not(@NotNull BlockPredicate predicate) implements BlockPredicate {

        @AsOf("2.3.0")
        public Not {
            Objects.requireNonNull(predicate, "predicate");
        }
    }

    @AsOf("2.3.0")
    record True() implements BlockPredicate {}
}