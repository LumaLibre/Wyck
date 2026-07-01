package me.outspending.biomesapi.wrapper.worldgen.ruletest;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import me.outspending.biomesapi.util.internal.InternalReflectUtil;
import me.outspending.biomesapi.wrapper.worldgen.WorldgenConversions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.block.data.CraftBlockData;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class RuleTestFactoryImpl implements RuleTest.Factory {

    @Override
    public Object toNms(RuleTest test) {
        return switch (test) {
            case RuleTest.AlwaysTrue _ -> net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest.INSTANCE;
            case RuleTest.BlockMatch blockMatch -> new net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest(block(blockMatch.block()));
            case RuleTest.BlockStateMatch blockStateMatch -> new net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest(blockState(blockStateMatch.state()));
            case RuleTest.TagMatch tagMatch -> new net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest(tagKey(tagMatch.tag()));
            case RuleTest.RandomBlockMatch randomBlockMatch -> new net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest(block(randomBlockMatch.block()), randomBlockMatch.probability());
            case RuleTest.RandomBlockStateMatch randomBlockStateMatch -> new net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest(blockState(randomBlockStateMatch.state()), randomBlockStateMatch.probability());
        };
    }

    @Override
    public RuleTest fromMinecraft(Object nms) {
        net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest ruleTest = (net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest) nms;
        return switch (ruleTest) {
            case AlwaysTrueTest _ -> RuleTest.alwaysTrue();
            case BlockMatchTest bm -> {
                Block block = InternalReflectUtil.getFieldValue(bm, "block");
                yield RuleTest.blockMatch(CraftMagicNumbers.getMaterial(block));
            }
            case BlockStateMatchTest bsm -> {
                BlockState blockState = InternalReflectUtil.getFieldValue(bsm, "blockState");
                yield RuleTest.blockStateMatch(CraftBlockData.createData(blockState));
            }
            case TagMatchTest tagTest -> {
                TagKey<Block> key = InternalReflectUtil.getFieldValue(tagTest, "tag");
                yield RuleTest.tagMatch(WorldgenConversions.toBukkitMaterialTag(key));
            }
            case RandomBlockMatchTest random -> {
                Block block = InternalReflectUtil.getFieldValue(random, "block");
                float probability = InternalReflectUtil.getFieldValue(random, "probability");
                yield RuleTest.randomBlockMatch(CraftMagicNumbers.getMaterial(block), probability);
            }
            case RandomBlockStateMatchTest randomState -> {
                BlockState blockState = InternalReflectUtil.getFieldValue(randomState, "blockState");
                float probability = InternalReflectUtil.getFieldValue(randomState, "probability");
                yield RuleTest.randomBlockStateMatch(CraftBlockData.createData(blockState), probability);
            }
            default -> throw new IllegalArgumentException("Unknown rule test: " + ruleTest.getClass().getSimpleName());
        };
    }

    private static BlockState blockState(BlockData data) {
        return ((CraftBlockData) data).getState();
    }

    private static Block block(Material material) {
        Block block = CraftMagicNumbers.getBlock(material);
        Preconditions.checkArgument(block != null, "material '%s' does not map to a block", material);
        return block;
    }

    private static TagKey<Block> tagKey(org.bukkit.Tag<Material> tag) {
        NamespacedKey key = tag.getKey();
        return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey()));
    }
}