package dev.wyck.wrapper.worldgen.stateproviders;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.WireProvider;
import dev.wyck.util.WeightedList;
import dev.wyck.wrapper.internal.Wrapper;
import dev.wyck.wrapper.worldgen.BlockPredicate;
import dev.wyck.wrapper.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Wraps the BlockStateProvider family, the per-position block state suppliers
 * used by feature configurations. All variants are authored; to point at a
 * registered provider type by key, see BlockStateProviderTypeReference.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface BlockStateProvider extends Wrapper permits BlockStateProvider.Simple, BlockStateProvider.Weighted, BlockStateProvider.Rotated, BlockStateProvider.RandomizedInt, BlockStateProvider.RuleBased, BlockStateProvider.Noise, BlockStateProvider.DualNoise, BlockStateProvider.NoiseThreshold {

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("dev.wyck.wrapper.worldgen.stateproviders.BlockStateProviderFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(BlockStateProvider provider);
    }

    @AsOf("2.3.0")
    static BlockStateProvider simple(BlockData state) {
        return new Simple(state);
    }

    @AsOf("2.3.0")
    static BlockStateProvider simple(Material block) {
        return new Simple(block.createBlockData());
    }

    @AsOf("2.3.0")
    static BlockStateProvider weighted(WeightedList<BlockData> states) {
        return new Weighted(states);
    }

    @AsOf("2.3.0")
    static BlockStateProvider rotated(Material block) {
        return new Rotated(block);
    }

    @AsOf("2.3.0")
    static BlockStateProvider randomizedInt(BlockStateProvider source, String property, IntProvider values) {
        return new RandomizedInt(source, property, values);
    }

    @AsOf("2.3.0")
    static BlockStateProvider ruleBased(BlockStateProvider fallback, List<RuleBased.Rule> rules) {
        return new RuleBased(fallback, rules);
    }

    @AsOf("2.3.0")
    static BlockStateProvider noise(long seed, NoiseParameters parameters, float scale, List<BlockData> states) {
        return new Noise(seed, parameters, scale, states);
    }

    @AsOf("2.3.0")
    static BlockStateProvider dualNoise(int varietyMin, int varietyMax, NoiseParameters slowNoise, float slowScale, long seed, NoiseParameters parameters, float scale, List<BlockData> states) {
        return new DualNoise(varietyMin, varietyMax, slowNoise, slowScale, seed, parameters, scale, states);
    }

    @AsOf("2.3.0")
    static BlockStateProvider noiseThreshold(long seed, NoiseParameters parameters, float scale, float threshold, float highChance, BlockData defaultState, List<BlockData> lowStates, List<BlockData> highStates) {
        return new NoiseThreshold(seed, parameters, scale, threshold, highChance, defaultState, lowStates, highStates);
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record Simple(BlockData state) implements BlockStateProvider {

        @AsOf("2.3.0")
        public Simple {
            Preconditions.checkNotNull(state, "state");
        }
    }

    @AsOf("2.3.0")
    record Weighted(WeightedList<BlockData> states) implements BlockStateProvider {

        @AsOf("2.3.0")
        public Weighted {
            Preconditions.checkNotNull(states, "states");
        }
    }

    @AsOf("2.3.0")
    record Rotated(Material block) implements BlockStateProvider {

        @AsOf("2.3.0")
        public Rotated {
            Preconditions.checkNotNull(block, "block");
        }
    }

    // recursive, composes a child provider
    @AsOf("2.3.0")
    record RandomizedInt(BlockStateProvider source, String property, IntProvider values) implements BlockStateProvider {

        @AsOf("2.3.0")
        public RandomizedInt {
            Preconditions.checkNotNull(source, "source");
            Preconditions.checkNotNull(property, "property");
            Preconditions.checkNotNull(values, "values");
        }
    }

    // recursive, composes child providers and block predicates
    @AsOf("2.3.0")
    record RuleBased(@Nullable BlockStateProvider fallback, List<Rule> rules) implements BlockStateProvider {

        @AsOf("2.3.0")
        public RuleBased {
            rules = List.copyOf(rules);
        }

        /**
         * A rule: when the predicate holds, the provider is used.
         * @since 2.3.0
         */
        @AsOf("2.3.0")
        public record Rule(BlockPredicate ifTrue, BlockStateProvider then) {

            @AsOf("2.3.0")
            public Rule {
                Preconditions.checkNotNull(ifTrue, "ifTrue");
                Preconditions.checkNotNull(then, "then");
            }
        }
    }

    @AsOf("2.3.0")
    record Noise(long seed, NoiseParameters parameters, float scale, List<BlockData> states) implements BlockStateProvider {

        @AsOf("2.3.0")
        public Noise {
            Preconditions.checkNotNull(parameters, "parameters");
            states = List.copyOf(states);
        }
    }

    @AsOf("2.3.0")
    record DualNoise(int varietyMin, int varietyMax, NoiseParameters slowNoise, float slowScale, long seed, NoiseParameters parameters, float scale, List<BlockData> states) implements BlockStateProvider {

        @AsOf("2.3.0")
        public DualNoise {
            Preconditions.checkNotNull(slowNoise, "slowNoise");
            Preconditions.checkNotNull(parameters, "parameters");
            states = List.copyOf(states);
        }
    }

    @AsOf("2.3.0")
    record NoiseThreshold(long seed, NoiseParameters parameters, float scale, float threshold, float highChance, BlockData defaultState, List<BlockData> lowStates, List<BlockData> highStates) implements BlockStateProvider {

        @AsOf("2.3.0")
        public NoiseThreshold {
            Preconditions.checkNotNull(parameters, "parameters");
            Preconditions.checkNotNull(defaultState, "defaultState");
            lowStates = List.copyOf(lowStates);
            highStates = List.copyOf(highStates);
        }
    }
}