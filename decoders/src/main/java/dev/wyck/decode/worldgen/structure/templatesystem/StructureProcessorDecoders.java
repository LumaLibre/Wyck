package dev.wyck.decode.worldgen.structure.templatesystem;

import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.keys.ResourceKey;
import dev.wyck.tags.TagSet;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.structure.templatesystem.processor.BlackstoneReplaceProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.BlockAgeProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.BlockIgnoreProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.BlockRotProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.CappedProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.GravityProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.JigsawReplacementProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.LavaSubmergedBlockProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.NopProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.ProcessorRule;
import dev.wyck.worldgen.structure.templatesystem.processor.ProtectedBlocksProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.RuleProcessor;
import dev.wyck.worldgen.structure.templatesystem.processor.StructureProcessor;
import dev.wyck.worldgen.valueproviders.IntProvider;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;
import org.bukkit.craftbukkit.block.CraftBlockType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public final class StructureProcessorDecoders extends DecoderRegistry<StructureProcessor, net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor> {

    public StructureProcessorDecoders() {
        register("blackstone_replace", _ -> BlackstoneReplaceProcessor.INSTANCE);
        register("jigsaw_replacement", _ -> JigsawReplacementProcessor.INSTANCE);
        register("lava_submerged_block", _ -> LavaSubmergedBlockProcessor.INSTANCE);
        register("nop", _ -> NopProcessor.INSTANCE);
        register("block_age", processor -> BlockAgeProcessor.of(FastReflection.<Float>read(processor, "mossiness")));
        register("block_ignore", processor -> {
            List<Block> blocks = FastReflection.read(processor, "toIgnore");
            return BlockIgnoreProcessor.of(blocks.stream().map(CraftBlockType::minecraftToBukkit).toList());
        });
        register("block_rot", processor -> {
            Optional<HolderSet<Block>> rottable = FastReflection.read(processor, "rottableBlocks");
            return BlockRotProcessor.of(rottable.map(TagSet::decodeBlocks).orElse(null), FastReflection.<Float>read(processor, "integrity"));
        });
        register("capped", processor -> CappedProcessor.of(
            StructureProcessor.decode(FastReflection.read(processor, "delegate")),
            IntProvider.decode(FastReflection.read(processor, "limit"))
        ));
        register("gravity", processor -> GravityProcessor.of(
            HeightmapType.TRANSLATOR.fromNms(FastReflection.<Heightmap.Types>read(processor, "heightmap")),
            FastReflection.<Integer>read(processor, "offset")
        ));
        register("protected_blocks", processor -> ProtectedBlocksProcessor.of(TagSet.decodeBlocks(
            ((net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor) processor).cannotReplace()
        )));
        register("rule", processor -> {
            List<net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule> rules = FastReflection.read(processor, "rules");
            return RuleProcessor.of(rules.stream().map(ProcessorRule::decode).toList());
        });
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor minecraftObject) {
        return Decoders.registryKey(BuiltInRegistries.STRUCTURE_PROCESSOR, minecraftObject.codec());
    }
}
