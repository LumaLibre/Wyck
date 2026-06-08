package me.outspending.biomesapi.wrapper.worldgen.ruletest;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.wrapper.NmsHandle;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

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
public sealed interface RuleTest extends NmsHandle permits RuleTest.AlwaysTrue, RuleTest.BlockMatch, RuleTest.BlockStateMatch, RuleTest.TagMatch, RuleTest.RandomBlockMatch, RuleTest.RandomBlockStateMatch {

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
    record AlwaysTrue() implements RuleTest {}

    @AsOf("2.3.0")
    record BlockMatch(Material block) implements RuleTest {

        @AsOf("2.3.0")
        public BlockMatch {
            Preconditions.checkNotNull(block, "block");
        }
    }

    @AsOf("2.3.0")
    record BlockStateMatch(BlockData state) implements RuleTest {

        @AsOf("2.3.0")
        public BlockStateMatch {
            Preconditions.checkNotNull(state, "state");
        }
    }

    @AsOf("2.3.0")
    record TagMatch(Tag<Material> tag) implements RuleTest {

        @AsOf("2.3.0")
        public TagMatch {
            Preconditions.checkNotNull(tag, "tag");
        }
    }

    @AsOf("2.3.0")
    record RandomBlockMatch(Material block, float probability) implements RuleTest {

        @AsOf("2.3.0")
        public RandomBlockMatch {
            Preconditions.checkNotNull(block, "block");
        }
    }

    @AsOf("2.3.0")
    record RandomBlockStateMatch(BlockData state, float probability) implements RuleTest {

        @AsOf("2.3.0")
        public RandomBlockStateMatch {
            Preconditions.checkNotNull(state, "state");
        }
    }
}