package dev.wyck.wrapper.worldgen.stateproviders;

import dev.wyck.annotations.WireFactory;
import dev.wyck.util.WeightedList;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RotatedBlockProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@WireFactory
@ApiStatus.Internal
public final class BlockStateProviderFactoryImpl implements BlockStateProvider.Factory {

    @Override
    public Object toNms(BlockStateProvider provider) {
        return switch (provider) {
            case BlockStateProvider.Simple simple -> simple(simple);
            case BlockStateProvider.Weighted weighted -> weighted(weighted);
            case BlockStateProvider.Rotated rotated -> rotated(rotated);
            case BlockStateProvider.RandomizedInt randomizedInt -> randomizedInt(randomizedInt);
            case BlockStateProvider.RuleBased ruleBased -> ruleBased(ruleBased);
            case BlockStateProvider.Noise noise -> noise(noise);
            case BlockStateProvider.DualNoise dualNoise -> dualNoise(dualNoise);
            case BlockStateProvider.NoiseThreshold noiseThreshold -> noiseThreshold(noiseThreshold);
        };
    }

    private static BlockState blockState(BlockData data) {
        return ((CraftBlockData) data).getState();
    }

    private static net.minecraft.util.random.WeightedList<net.minecraft.world.level.block.state.BlockState> weightedStates(WeightedList<BlockData> states) {
        net.minecraft.util.random.WeightedList.Builder<net.minecraft.world.level.block.state.BlockState> builder = net.minecraft.util.random.WeightedList.builder();

        for (WeightedList.Weighted<BlockData> entry : states.unwrap()) {
            builder.add(blockState(entry.value()), entry.weight());
        }
        return builder.build();
    }

    private static List<BlockState> nmsStates(List<BlockData> states) {
        List<BlockState> result = new ArrayList<>(states.size());
        for (BlockData data : states) {
            result.add(blockState(data));
        }
        return result;
    }

    private net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider simple(BlockStateProvider.Simple simple) {
        return net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider.simple(blockState(simple.state()));
    }

    private net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider weighted(BlockStateProvider.Weighted weighted) {
        return new net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider(weightedStates(weighted.states()));
    }

    private net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider rotated(BlockStateProvider.Rotated rotated) {
        Block block = CraftMagicNumbers.getBlock(rotated.block());
        if (block == null) {
            throw new IllegalArgumentException("Material " + rotated.block() + " does not map to a block");
        }
        return new RotatedBlockProvider(block);
    }

    private net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider randomizedInt(BlockStateProvider.RandomizedInt randomizedInt) {
        net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider source = (net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider) randomizedInt.source().toMinecraft();
        IntProvider values = (IntProvider) randomizedInt.values().toMinecraft();
        return new RandomizedIntStateProvider(source, randomizedInt.property(), values);
    }

    private net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider ruleBased(BlockStateProvider.RuleBased ruleBased) {
        net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider fallback =
            ruleBased.fallback() == null ? null : (net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider) ruleBased.fallback().toMinecraft();

        List<net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider.Rule> rules = new ArrayList<>();
        for (BlockStateProvider.RuleBased.Rule rule : ruleBased.rules()) {
            BlockPredicate ifTrue = (BlockPredicate) rule.ifTrue().toMinecraft();
            net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider then = (net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider) rule.then().toMinecraft();

            rules.add(new RuleBasedStateProvider.Rule(ifTrue, then));
        }
        return new RuleBasedStateProvider(fallback, rules);
    }

    private net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider noise(BlockStateProvider.Noise noise) {
        net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters parameters =
            (net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters) noise.parameters().toMinecraft();
        return new net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider(noise.seed(), parameters, noise.scale(), nmsStates(noise.states()));
    }

    private net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider dualNoise(BlockStateProvider.DualNoise dualNoise) {
        InclusiveRange<Integer> variety = new InclusiveRange<>(dualNoise.varietyMin(), dualNoise.varietyMax());
        NormalNoise.NoiseParameters slowNoise = (NormalNoise.NoiseParameters) dualNoise.slowNoise().toMinecraft();
        net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters parameters = (net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters) dualNoise.parameters().toMinecraft();
        return new DualNoiseProvider(
            variety,
            slowNoise,
            dualNoise.slowScale(),
            dualNoise.seed(),
            parameters,
            dualNoise.scale(),
            nmsStates(dualNoise.states())
        );
    }

    private net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider noiseThreshold(BlockStateProvider.NoiseThreshold noiseThreshold) {
        NormalNoise.NoiseParameters parameters = (NormalNoise.NoiseParameters) noiseThreshold.parameters().toMinecraft();
        return new NoiseThresholdProvider(
            noiseThreshold.seed(),
            parameters,
            noiseThreshold.scale(),
            noiseThreshold.threshold(),
            noiseThreshold.highChance(),
            blockState(noiseThreshold.defaultState()),
            nmsStates(noiseThreshold.lowStates()),
            nmsStates(noiseThreshold.highStates())
        );
    }
}