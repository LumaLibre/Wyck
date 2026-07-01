package me.outspending.biomesapi.wrapper.worldgen.stateproviders;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.serialization.Codecs;
import me.outspending.biomesapi.serialization.StringRepresentable;
import me.outspending.biomesapi.util.WeightedList;
import me.outspending.biomesapi.wrapper.internal.NmsDecoder;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import me.outspending.biomesapi.wrapper.worldgen.BlockPredicate;
import me.outspending.biomesapi.wrapper.worldgen.valueproviders.IntProvider;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Wraps the BlockStateProvider family, the per-position block state suppliers
 * used by feature configurations. All variants are authored; to climatePoint at a
 * registered provider type by key, see BlockStateProviderTypeReference.
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface BlockStateProvider extends NmsHandle, StringRepresentable permits BlockStateProvider.Simple, BlockStateProvider.Weighted, BlockStateProvider.Rotated, BlockStateProvider.RandomizedInt, BlockStateProvider.RuleBased, BlockStateProvider.Noise, BlockStateProvider.DualNoise, BlockStateProvider.NoiseThreshold {

    Codec<BlockStateProvider> CODEC = Codec.recursive("BlockStateProvider", self -> {
        Map<String, MapCodec<? extends BlockStateProvider>> byType = Map.of(
            "simple", Simple.MAP_CODEC,
            "weighted", Weighted.MAP_CODEC,
            "rotated", Rotated.MAP_CODEC,
            "randomized_int", RandomizedInt.mapCodec(self),
            "rule_based", RuleBased.mapCodec(self),
            "noise", Noise.MAP_CODEC,
            "dual_noise", DualNoise.MAP_CODEC,
            "noise_threshold", NoiseThreshold.MAP_CODEC
        );
        return Codec.STRING.dispatch("type", BlockStateProvider::type, byType::get);
    });

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.stateproviders.BlockStateProviderFactoryImpl");

    @ApiStatus.Internal
    interface Factory extends NmsDecoder<BlockStateProvider> {
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

    @AsOf("2.4.0")
    static BlockStateProvider fromMinecraft(Object nms) {
        return WIRE.get().fromMinecraft(nms);
    }

    @AsOf("2.3.0")
    record Simple(BlockData state) implements BlockStateProvider {
        public static final MapCodec<Simple> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codecs.BLOCK_DATA_CODEC.fieldOf("state").forGetter(Simple::state)
        ).apply(i, Simple::new));

        @AsOf("2.3.0")
        public Simple {
            Preconditions.checkNotNull(state, "state");
        }
    }

    @AsOf("2.3.0")
    record Weighted(WeightedList<BlockData> states) implements BlockStateProvider {
        public static final MapCodec<Weighted> MAP_CODEC = WeightedList.codec(Codecs.BLOCK_DATA_CODEC)
            .fieldOf("states").xmap(Weighted::new, Weighted::states);

        @AsOf("2.3.0")
        public Weighted {
            Preconditions.checkNotNull(states, "states");
        }
    }

    @AsOf("2.3.0")
    record Rotated(Material block) implements BlockStateProvider {
        public static final MapCodec<Rotated> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codecs.MATERIAL_CODEC.fieldOf("block").forGetter(Rotated::block)
        ).apply(i, Rotated::new));

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

        public static MapCodec<RandomizedInt> mapCodec(Codec<BlockStateProvider> self) {
            return RecordCodecBuilder.mapCodec(i -> i.group(
                self.fieldOf("source").forGetter(RandomizedInt::source),
                Codec.STRING.fieldOf("property").forGetter(RandomizedInt::property),
                IntProvider.CODEC.fieldOf("values").forGetter(RandomizedInt::values)
            ).apply(i, RandomizedInt::new));
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

        public static MapCodec<RuleBased> mapCodec(Codec<BlockStateProvider> self) {
            Codec<Rule> ruleCodec = RecordCodecBuilder.create(i -> i.group(
                BlockPredicate.CODEC.fieldOf("if_true").forGetter(Rule::ifTrue),
                self.fieldOf("then").forGetter(Rule::then)
            ).apply(i, Rule::new));
            return RecordCodecBuilder.mapCodec(i -> i.group(
                self.optionalFieldOf("fallback").forGetter(p -> Optional.ofNullable(p.fallback())),
                Codec.list(ruleCodec).fieldOf("rules").forGetter(RuleBased::rules)
            ).apply(i, (fallback, rules) -> new RuleBased(fallback.orElse(null), rules)));
        }
    }

    @AsOf("2.3.0")
    record Noise(long seed, NoiseParameters parameters, float scale, List<BlockData> states) implements BlockStateProvider {
        public static final MapCodec<Noise> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.LONG.fieldOf("seed").forGetter(Noise::seed),
            NoiseParameters.CODEC.fieldOf("parameters").forGetter(Noise::parameters),
            Codec.FLOAT.fieldOf("scale").forGetter(Noise::scale),
            Codec.list(Codecs.BLOCK_DATA_CODEC).fieldOf("states").forGetter(Noise::states)
        ).apply(i, Noise::new));

        @AsOf("2.3.0")
        public Noise {
            Preconditions.checkNotNull(parameters, "parameters");
            states = List.copyOf(states);
        }
    }

    @AsOf("2.3.0")
    record DualNoise(int varietyMin, int varietyMax, NoiseParameters slowNoise, float slowScale, long seed, NoiseParameters parameters, float scale, List<BlockData> states) implements BlockStateProvider {
        public static final MapCodec<DualNoise> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.INT.fieldOf("variety_min").forGetter(DualNoise::varietyMin),
            Codec.INT.fieldOf("variety_max").forGetter(DualNoise::varietyMax),
            NoiseParameters.CODEC.fieldOf("slow_noise").forGetter(DualNoise::slowNoise),
            Codec.FLOAT.fieldOf("slow_scale").forGetter(DualNoise::slowScale),
            Codec.LONG.fieldOf("seed").forGetter(DualNoise::seed),
            NoiseParameters.CODEC.fieldOf("parameters").forGetter(DualNoise::parameters),
            Codec.FLOAT.fieldOf("scale").forGetter(DualNoise::scale),
            Codec.list(Codecs.BLOCK_DATA_CODEC).fieldOf("states").forGetter(DualNoise::states)
        ).apply(i, DualNoise::new));

        @AsOf("2.3.0")
        public DualNoise {
            Preconditions.checkNotNull(slowNoise, "slowNoise");
            Preconditions.checkNotNull(parameters, "parameters");
            states = List.copyOf(states);
        }
    }

    @AsOf("2.3.0")
    record NoiseThreshold(long seed, NoiseParameters parameters, float scale, float threshold, float highChance, BlockData defaultState, List<BlockData> lowStates, List<BlockData> highStates) implements BlockStateProvider {
        public static final MapCodec<NoiseThreshold> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.LONG.fieldOf("seed").forGetter(NoiseThreshold::seed),
            NoiseParameters.CODEC.fieldOf("parameters").forGetter(NoiseThreshold::parameters),
            Codec.FLOAT.fieldOf("scale").forGetter(NoiseThreshold::scale),
            Codec.FLOAT.fieldOf("threshold").forGetter(NoiseThreshold::threshold),
            Codec.FLOAT.fieldOf("high_chance").forGetter(NoiseThreshold::highChance),
            Codecs.BLOCK_DATA_CODEC.fieldOf("default_state").forGetter(NoiseThreshold::defaultState),
            Codec.list(Codecs.BLOCK_DATA_CODEC).fieldOf("low_states").forGetter(NoiseThreshold::lowStates),
            Codec.list(Codecs.BLOCK_DATA_CODEC).fieldOf("high_states").forGetter(NoiseThreshold::highStates)
        ).apply(i, NoiseThreshold::new));

        @AsOf("2.3.0")
        public NoiseThreshold {
            Preconditions.checkNotNull(parameters, "parameters");
            Preconditions.checkNotNull(defaultState, "defaultState");
            lowStates = List.copyOf(lowStates);
            highStates = List.copyOf(highStates);
        }
    }
}