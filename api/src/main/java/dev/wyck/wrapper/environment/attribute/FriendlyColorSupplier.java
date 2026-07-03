package dev.wyck.wrapper.environment.attribute;

import dev.wyck.annotations.AsOf;
import org.jspecify.annotations.NullMarked;

/**
 * A supplier for int color values.
 * @version 2.1.0
 * @since 2.1.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.1.0")
public final class FriendlyColorSupplier extends EnvironmentAttributeSupplier<Integer> {
    @AsOf("2.1.0")
    public FriendlyColorSupplier(EnvironmentAttribute<Integer> environmentAttribute) {
        super(environmentAttribute);
    }
}