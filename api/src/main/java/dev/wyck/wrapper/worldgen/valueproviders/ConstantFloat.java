package dev.wyck.wrapper.worldgen.valueproviders;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * A constant float provider which always returns the same value.
 *
 * @see <a href="https://minecraft.wiki/w/Configured_feature/float_provider">float provider</a>
 * @since 3.0.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.0.0")
public interface ConstantFloat extends FloatProvider {

    @ApiStatus.Internal
    ConstructWireProvider<ConstantFloat> WIRE = ConstructWireProvider.create("dev.wyck.wrapper.worldgen.valueproviders.ConstantFloatImpl");

    /**
     * A constant float provider.
     * @param value the value to return
     * @return the constant float provider
     * @since 3.0.0
     */
    @AsOf("3.0.0")
    static ConstantFloat of(float value) {
        return WIRE.construct(value);
    }

}
