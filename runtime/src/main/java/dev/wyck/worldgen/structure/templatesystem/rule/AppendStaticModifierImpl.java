package dev.wyck.worldgen.structure.templatesystem.rule;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.TagParser;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record AppendStaticModifierImpl(@Override String snbt) implements AppendStaticModifier {
    @Override
    public Object toMinecraft() {
        try {
            return new net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.AppendStatic(TagParser.parseCompoundFully(snbt));
        } catch (CommandSyntaxException e) {
            throw new IllegalArgumentException("not a valid SNBT compound: " + snbt, e);
        }
    }
}
