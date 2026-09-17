package dev.wyck.worldgen.structure.templatesystem.rule;

import dev.wyck.keys.ResourceKey;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AppendLootModifierImpl(@Override ResourceKey lootTable) implements AppendLootModifier {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendLoot(
            net.minecraft.resources.ResourceKey.create(Registries.LOOT_TABLE, lootTable.identifier())
        );
    }
}
