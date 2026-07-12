package dev.wyck.wrapper.worldgen.material;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record FluidStateImpl(@Override FluidType fluid) implements FluidState {
    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.material.Fluid resolved = fluid.toNms();
        Preconditions.checkNotNull(resolved, "Fluid " + fluid.resourceKey() + " does not resolve in the fluid registry");
        return resolved.defaultFluidState();
    }
}