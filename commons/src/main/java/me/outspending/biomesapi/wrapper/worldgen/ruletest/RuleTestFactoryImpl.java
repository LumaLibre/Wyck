package me.outspending.biomesapi.wrapper.worldgen.ruletest;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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
            case RuleTest.AlwaysTrue ignored -> net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest.INSTANCE;
            case RuleTest.BlockMatch blockMatch -> new net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest(block(blockMatch.block()));
            case RuleTest.BlockStateMatch blockStateMatch -> new net.minecraft.world.level.levelgen.structure.templatesystem.BlockStateMatchTest(blockState(blockStateMatch.state()));
            case RuleTest.TagMatch tagMatch -> new net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest(tagKey(tagMatch.tag()));
            case RuleTest.RandomBlockMatch randomBlockMatch -> new net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest(block(randomBlockMatch.block()), randomBlockMatch.probability());
            case RuleTest.RandomBlockStateMatch randomBlockStateMatch -> new net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockStateMatchTest(blockState(randomBlockStateMatch.state()), randomBlockStateMatch.probability());
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