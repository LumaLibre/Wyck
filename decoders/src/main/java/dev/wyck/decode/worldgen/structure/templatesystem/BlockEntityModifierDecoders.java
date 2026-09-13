package dev.wyck.decode.worldgen.structure.templatesystem;

import dev.wyck.decode.Decoders;
import dev.wyck.decode.FastReflection;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.templatesystem.rule.BlockEntityModifier;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.RuleBlockEntityModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class BlockEntityModifierDecoders extends DecoderRegistry<BlockEntityModifier, RuleBlockEntityModifier> {

    public BlockEntityModifierDecoders() {
        register("clear", _ -> BlockEntityModifier.clear());
        register("passthrough", _ -> BlockEntityModifier.passthrough());
        // CompoundTag#toString is SNBT, which is what the Wyck side carries
        register("append_static", modifier -> BlockEntityModifier.appendStatic(FastReflection.<CompoundTag>read(modifier, "tag").toString()));
        register("append_loot", modifier -> BlockEntityModifier.appendLoot(Decoders.key(
            FastReflection.<net.minecraft.resources.ResourceKey<?>>read(modifier, "lootTable")
        )));
    }

    @Override
    protected ResourceKey discriminate(RuleBlockEntityModifier minecraftObject) {
        return Decoders.registryKey(BuiltInRegistries.RULE_BLOCK_ENTITY_MODIFIER, minecraftObject.getType());
    }
}
