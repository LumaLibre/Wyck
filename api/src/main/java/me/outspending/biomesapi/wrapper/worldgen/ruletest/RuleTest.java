package me.outspending.biomesapi.wrapper.worldgen.ruletest;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.serialization.Codecs;
import me.outspending.biomesapi.serialization.StringRepresentable;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Map;

/**
 * Wraps Minecraft's RuleTest family, the block-match predicates used by ore and
 * replace-block configurations.
 *
 * @since 2.3.0
 * @version 2.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public sealed interface RuleTest extends NmsHandle, StringRepresentable permits RuleTest.AlwaysTrue, RuleTest.BlockMatch, RuleTest.BlockStateMatch, RuleTest.TagMatch, RuleTest.RandomBlockMatch, RuleTest.RandomBlockStateMatch {

    Codec<RuleTest> CODEC = Codec.lazyInitialized(() -> {
        Map<String, MapCodec<? extends RuleTest>> byType = Map.of(
            "always_true", AlwaysTrue.MAP_CODEC,
            "block_match", BlockMatch.MAP_CODEC,
            "block_state_match", BlockStateMatch.MAP_CODEC,
            "tag_match", TagMatch.MAP_CODEC,
            "random_block_match", RandomBlockMatch.MAP_CODEC,
            "random_block_state_match", RandomBlockStateMatch.MAP_CODEC
        );
        return Codec.STRING.dispatch("type", RuleTest::type, byType::get);
    });

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.worldgen.ruletest.RuleTestFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        Object toNms(RuleTest test);
    }

    @AsOf("2.3.0")
    static RuleTest alwaysTrue() {
        return new AlwaysTrue();
    }

    @AsOf("2.3.0")
    static RuleTest blockMatch(Material block) {
        return new BlockMatch(block);
    }

    @AsOf("2.3.0")
    static RuleTest blockStateMatch(BlockData state) {
        return new BlockStateMatch(state);
    }

    @AsOf("2.3.0")
    static RuleTest tagMatch(Tag<Material> tag) {
        return new TagMatch(tag);
    }

    @AsOf("2.3.0")
    static RuleTest randomBlockMatch(Material block, float probability) {
        return new RandomBlockMatch(block, probability);
    }

    @AsOf("2.3.0")
    static RuleTest randomBlockStateMatch(BlockData state, float probability) {
        return new RandomBlockStateMatch(state, probability);
    }

    @Override
    @AsOf("2.3.0")
    default Object toMinecraft() {
        return WIRE.get().toNms(this);
    }

    @AsOf("2.3.0")
    record AlwaysTrue() implements RuleTest {
        public static final MapCodec<AlwaysTrue> MAP_CODEC = MapCodec.unit(AlwaysTrue::new);
    }

    @AsOf("2.3.0")
    record BlockMatch(Material block) implements RuleTest {
        public static final MapCodec<BlockMatch> MAP_CODEC = Codecs.MATERIAL_CODEC.fieldOf("block")
            .xmap(BlockMatch::new, BlockMatch::block);

        @AsOf("2.3.0")
        public BlockMatch {
            Preconditions.checkNotNull(block, "block");
        }
    }

    @AsOf("2.3.0")
    record BlockStateMatch(BlockData state) implements RuleTest {
        public static final MapCodec<BlockStateMatch> MAP_CODEC = Codecs.BLOCK_DATA_CODEC.fieldOf("state")
            .xmap(BlockStateMatch::new, BlockStateMatch::state);

        @AsOf("2.3.0")
        public BlockStateMatch {
            Preconditions.checkNotNull(state, "state");
        }
    }

    @AsOf("2.3.0")
    record TagMatch(Tag<Material> tag) implements RuleTest {
        public static final MapCodec<TagMatch> MAP_CODEC = Codecs.MATERIAL_TAG_CODEC.fieldOf("tag")
            .xmap(TagMatch::new, TagMatch::tag);

        @AsOf("2.3.0")
        public TagMatch {
            Preconditions.checkNotNull(tag, "tag");
        }
    }

    @AsOf("2.3.0")
    record RandomBlockMatch(Material block, float probability) implements RuleTest {
        public static final MapCodec<RandomBlockMatch> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codecs.MATERIAL_CODEC.fieldOf("block").forGetter(RandomBlockMatch::block),
            Codec.FLOAT.fieldOf("probability").forGetter(RandomBlockMatch::probability)
        ).apply(i, RandomBlockMatch::new));

        @AsOf("2.3.0")
        public RandomBlockMatch {
            Preconditions.checkNotNull(block, "block");
        }
    }

    @AsOf("2.3.0")
    record RandomBlockStateMatch(BlockData state, float probability) implements RuleTest {
        public static final MapCodec<RandomBlockStateMatch> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codecs.BLOCK_DATA_CODEC.fieldOf("state").forGetter(RandomBlockStateMatch::state),
            Codec.FLOAT.fieldOf("probability").forGetter(RandomBlockStateMatch::probability)
        ).apply(i, RandomBlockStateMatch::new));

        @AsOf("2.3.0")
        public RandomBlockStateMatch {
            Preconditions.checkNotNull(state, "state");
        }
    }
}