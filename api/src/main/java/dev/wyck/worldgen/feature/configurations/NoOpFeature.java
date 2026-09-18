package dev.wyck.worldgen.feature.configurations;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import org.jspecify.annotations.NullMarked;

/**
 * A feature configuration that does nothing.
 *
 * @since 2.3.0
 * @version 3.0.0
 * @author Jsinco
 */
@NullMarked
@AsOf("4.0.0")
public interface NoOpFeature extends FeatureConfiguration {

    /** The singleton instance of {@link NoOpFeature}. */
    @AsOf("4.0.0")
    NoOpFeature INSTANCE = of();

    private static NoOpFeature of() {
        record Holder() {
            static final ConstructWireProvider<NoOpFeature> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.NoOpFeatureImpl");
        }
        return Holder.WIRE.construct();
    }
}