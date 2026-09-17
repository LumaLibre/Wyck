package dev.wyck.worldgen.feature.configurations;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.stateproviders.BlockStateProvider;
import dev.wyck.wrapper.decode.Decoder;
import dev.wyck.wrapper.Wrapper;
import org.jspecify.annotations.NullMarked;

/**
 * Common contract for Minecraft 26.3's direct feature implementations.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.3.0")
public interface FeatureConfiguration extends Wrapper {

    @AsOf("3.0.0")
    NoOpFeature NONE = NoOpFeature.INSTANCE; // vanilla

    /**
     * Gets the {@link NoOpFeature} instance.
     * @return the none feature configuration
     * @since 3.1.0
     */
    @AsOf("3.1.0")
    static NoOpFeature noOp() {
        return NONE;
    }

    /**
     * Creates a builder for a {@link BlockBlobFeature}.
     * @return a new block blob configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockBlobFeature.Builder blockBlob() {
        return BlockBlobFeature.builder();
    }

    /**
     * Creates a builder for a {@link BlockColumnFeature}.
     * @return a new block column configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockColumnFeature.Builder blockColumn() {
        return BlockColumnFeature.builder();
    }

    /**
     * Creates a {@link BlockPileFeature}.
     * @param stateProvider the block state provider used for the blocks in the pile
     * @return a new block pile configuration
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BlockPileFeature blockPile(BlockStateProvider stateProvider) {
        return BlockPileFeature.of(stateProvider);
    }

    /**
     * Creates a builder for a {@link IcebergFeature}.
     * @return a new block state configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static IcebergFeature.Builder iceberg() {
        return IcebergFeature.builder();
    }

    /**
     * Creates a builder for a {@link DeltaFeature}.
     * @return a new delta feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DeltaFeature.Builder delta() {
        return DeltaFeature.builder();
    }

    /**
     * Creates a builder for a {@link DiskFeature}.
     * @return a new disk configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static DiskFeature.Builder disk() {
        return DiskFeature.builder();
    }

    /**
     * Creates a builder for a {@link SpeleothemFeature}.
     * @return a new pointed dripstone configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpeleothemFeature.Builder speleothem() {
        return SpeleothemFeature.builder();
    }

    /**
     * Creates a builder for a {@link SpeleothemClusterFeature}.
     * @return a new speleothem cluster configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpeleothemClusterFeature.Builder speleothemCluster() {
        return SpeleothemClusterFeature.builder();
    }

    /**
     * Creates a builder for an {@link EndGatewayFeature}.
     * @return a new end gateway configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EndGatewayFeature.Builder endGateway() {
        return EndGatewayFeature.builder();
    }

    /**
     * Creates a builder for an {@link EndSpikeFeature}.
     * @return a new end spike configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static EndSpikeFeature.Builder endSpike() {
        return EndSpikeFeature.builder();
    }

    /**
     * Creates a builder for a {@link FallenTreeFeature}.
     * @return a new fallen tree configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FallenTreeFeature.Builder fallenTree() {
        return FallenTreeFeature.builder();
    }

    /**
     * Creates a builder for a {@link GeodeFeature}.
     * @return a new geode configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static GeodeFeature.Builder geode() {
        return GeodeFeature.builder();
    }

    /**
     * Creates a builder for a {@link HugeRedMushroomFeature}.
     * @return a new huge mushroom feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static HugeRedMushroomFeature.Builder hugeRedMushroom() {
        return HugeRedMushroomFeature.builder();
    }

    /**
     * Creates a builder for a {@link LargeDripstoneFeature}.
     * @return a new large dripstone configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LargeDripstoneFeature.Builder largeDripstone() {
        return LargeDripstoneFeature.builder();
    }

    /**
     * Creates a builder for a {@link FillLayerFeature}.
     * @return a new layer configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static FillLayerFeature.Builder fillLayer() {
        return FillLayerFeature.builder();
    }

    /**
     * Creates a builder for a {@link MultifaceGrowthFeature}.
     * @return a new multiface growth configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static MultifaceGrowthFeature.Builder multifaceGrowth() {
        return MultifaceGrowthFeature.builder();
    }

    /**
     * Creates a builder for an {@link OreFeature}.
     * @return a new ore configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static OreFeature.Builder ore() {
        return OreFeature.builder();
    }

    /**
     * Creates a builder for a {@link BambooFeature}.
     * @return a new probability feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static BambooFeature bamboo(float probability) {
        return BambooFeature.of(probability);
    }

    /**
     * Creates a builder for a {@link RandomBooleanSelectorFeature}.
     * @return a new random boolean feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomBooleanSelectorFeature.Builder randomBooleanSelector() {
        return RandomBooleanSelectorFeature.builder();
    }

    /**
     * Creates a builder for a {@link RandomSelectorFeature}.
     * @return a new random feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RandomSelectorFeature.Builder randomSelector() {
        return RandomSelectorFeature.builder();
    }

    /**
     * Creates a builder for a {@link ReplaceBlockFeature}.
     * @return a new replace block configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReplaceBlockFeature.Builder replaceBlock() {
        return ReplaceBlockFeature.builder();
    }

    /**
     * Creates a builder for a {@link ReplaceBlobsFeature}.
     * @return a new replace sphere configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ReplaceBlobsFeature.Builder replaceBlobs() {
        return ReplaceBlobsFeature.builder();
    }

    /**
     * Creates a builder for a {@link RootSystemFeature}.
     * @return a new root system configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static RootSystemFeature.Builder rootSystem() {
        return RootSystemFeature.builder();
    }

    /**
     * Creates a builder for a {@link SculkPatchFeature}.
     * @return a new sculk patch configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SculkPatchFeature.Builder sculkPatch() {
        return SculkPatchFeature.builder();
    }

    /**
     * Creates a builder for a {@link SimpleBlockFeature}.
     * @return a new simple block configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SimpleBlockFeature.Builder simpleBlock() {
        return SimpleBlockFeature.builder();
    }

    /**
     * Creates a builder for a {@link SequenceFeature}.
     * @return a new simple random feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SequenceFeature.Builder sequence() {
        return SequenceFeature.builder();
    }

    /**
     * Creates a builder for a {@link SpikeFeature}.
     * @return a new spike configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpikeFeature.Builder spike() {
        return SpikeFeature.builder();
    }

    /**
     * Creates a builder for a {@link SpringFeature}.
     * @return a new spring configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static SpringFeature.Builder spring() {
        return SpringFeature.builder();
    }

    /**
     * Creates a builder for a {@link TreeFeature}.
     * @return a new tree configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static TreeFeature.Builder tree() {
        return TreeFeature.builder();
    }

    /**
     * Creates a builder for a {@link LakeFeature}.
     * @return a new lake feature configuration builder
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static LakeFeature.Builder lake() {
        return LakeFeature.builder();
    }

    /**
     * Creates a builder for a {@link TemplateFeature}.
     * @return a new template feature configuration builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static TemplateFeature.Builder template() {
        return TemplateFeature.builder();
    }

    /**
     * Creates a builder for an {@link UnderwaterMagmaFeature}.
     * @return a new underwater magma configuration builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static UnderwaterMagmaFeature.Builder underwaterMagma() {
        return UnderwaterMagmaFeature.builder();
    }

    /**
     * Creates a builder for a {@link VegetationPatchFeature}.
     * @return a new vegetation patch configuration builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static VegetationPatchFeature.Builder vegetationPatch() {
        return VegetationPatchFeature.builder();
    }

    /**
     * Creates a builder for a {@link WeightedRandomSelectorFeature}.
     * @return a new weighted random feature configuration builder
     * @since 3.0.1
     */
    @AsOf("3.0.1")
    static WeightedRandomSelectorFeature.Builder weightedRandomSelector() {
        return WeightedRandomSelectorFeature.builder();
    }

    /**
     * Creates a builder for a {@link HugeFungusFeature}.
     * @return a new huge fungus configuration builder
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static HugeFungusFeature.Builder hugeFungus() {
        return HugeFungusFeature.builder();
    }

    /**
     * Creates a builder for a {@link FossilFeature}.
     * @return a new fossil feature configuration builder
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static FossilFeature.Builder fossil() {
        return FossilFeature.builder();
    }

    /**
     * Reads the configuration owned by a Minecraft configured feature.
     * @param minecraftConfiguredFeature the Minecraft configured feature carrying the configuration
     * @return the wrapper for the configuration
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static FeatureConfiguration decode(Object minecraftConfiguredFeature) {
        record Holder() {
            static final Decoder<FeatureConfiguration> DECODER = Decoder.create("dev.wyck.decode.worldgen.feature.FeatureConfigurationDecoders");
        }
        return Holder.DECODER.decode(minecraftConfiguredFeature);
    }
}
