package dev.wyck.worldgen.material;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record FluidStateImpl(
    @Override FluidType fluid,
    @Override int amount,
    @Override boolean isSource,
    @Override boolean falling
) implements FluidState {

    public FluidStateImpl {
        Preconditions.checkNotNull(fluid, "fluid");
        if (fluid == FluidType.EMPTY) {
            Preconditions.checkArgument(amount == 0, "empty fluid states must have an amount of 0, got %s", amount);
        } else if (isSource) {
            Preconditions.checkArgument(amount == 8, "source fluid states must have an amount of 8, got %s", amount);
        } else {
            Preconditions.checkArgument(amount >= 1 && amount <= 8, "amount must be within [1, 8], got %s", amount);
        }
    }

    @Override
    public Object toMinecraft() {
        net.minecraft.world.level.material.Fluid resolved = this.fluid.toNms();
        Preconditions.checkNotNull(resolved, "Fluid '" + this.fluid.resourceKey() + "' does not resolve in the fluid registry");

        if (resolved instanceof net.minecraft.world.level.material.FlowingFluid flowing) {
            return this.isSource
                ? flowing.getSource(this.falling)
                : flowing.getFlowing(this.amount, this.falling);
        }
        return resolved.defaultFluidState();
    }
}