package dev.wyck.worldgen.structure.templatesystem.rule;

import dev.wyck.util.internal.InternalReflectUtil;
import net.minecraft.world.level.levelgen.structure.templatesystem.rule.blockentity.Clear;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ClearModifierImpl() implements ClearModifier {

    // Clear keeps its singleton private
    private static final Clear CLEAR = InternalReflectUtil.get(InternalReflectUtil.field(Clear.class, "INSTANCE"), null);

    @Override
    public Object toMinecraft() {
        return CLEAR;
    }
}
