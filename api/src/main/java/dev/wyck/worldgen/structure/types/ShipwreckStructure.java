package dev.wyck.worldgen.structure.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A shipwreck.
 *
 * @see <a href="https://minecraft.wiki/w/Shipwreck">Shipwreck</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface ShipwreckStructure extends DefinedStructure, Registerable<ShipwreckStructure> {

    /**
     * Whether the wreck is placed on land, choosing from the beached ship templates, rather than
     * on the ocean floor.
     * @return whether the shipwreck is beached
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    boolean beached();

    @Override
    @AsOf("3.4.0")
    default ShipwreckStructure withSettings(StructureSettings settings) {
        return of(resourceKey().orElse(null), settings, beached());
    }

    @Override
    @AsOf("3.4.0")
    default ShipwreckStructure withResourceKey(ResourceKey resourceKey) {
        return of(resourceKey, settings(), beached());
    }

    @Override
    @AsOf("3.4.0")
    ShipwreckStructure register();

    /**
     * Converts this object back to a builder.
     * @return a builder with the same values as this object
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new shipwreck.
     * @param resourceKey the resource key of the structure, or null if not present
     * @param settings the settings every structure carries
     * @param beached whether the wreck is placed on land
     * @return a new shipwreck
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ShipwreckStructure of(@Nullable ResourceKey resourceKey, StructureSettings settings, boolean beached) {
        record Holder() {
            static final ConstructWireProvider<ShipwreckStructure> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.types.ShipwreckStructureImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), settings, beached);
    }

    /**
     * Creates a new builder for a shipwreck on the ocean floor, vanilla's default.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder(StructureSettings settings) {
        return new Builder(settings);
    }

    /**
     * Builder for {@link ShipwreckStructure}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private StructureSettings settings;
        private boolean beached = false;

        public Builder(StructureSettings settings) {
            this.settings = settings;
        }

        public Builder(ShipwreckStructure structure) {
            this.resourceKey = structure.resourceKey().orElse(null);
            this.settings = structure.settings();
            this.beached = structure.beached();
        }

        /**
         * Sets the resource key of the structure.
         * @param resourceKey the resource key of the structure
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the settings every structure carries.
         * @param settings the settings of the structure
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder settings(StructureSettings settings) {
            this.settings = settings;
            return this;
        }

        /**
         * Sets whether the wreck is placed on land rather than on the ocean floor.
         * @param beached whether the shipwreck is beached
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder beached(boolean beached) {
            this.beached = beached;
            return this;
        }

        /**
         * Builds the structure.
         * @return the structure
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public ShipwreckStructure build() {
            return of(resourceKey, settings, beached);
        }
    }
}
