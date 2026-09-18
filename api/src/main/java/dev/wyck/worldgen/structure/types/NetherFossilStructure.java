package dev.wyck.worldgen.structure.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * A nether fossil.
 *
 * @see <a href="https://minecraft.wiki/w/Nether_Fossil">Nether Fossil</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface NetherFossilStructure extends DefinedStructure, Registerable<NetherFossilStructure> {

    /**
     * The height the fossil starts searching downward from for solid ground.
     * @return the starting height
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    HeightProvider height();

    @Override
    @AsOf("3.4.0")
    default NetherFossilStructure withSettings(StructureSettings settings) {
        return of(resourceKey().orElse(null), settings, height());
    }

    @Override
    @AsOf("3.4.0")
    default NetherFossilStructure withResourceKey(ResourceKey resourceKey) {
        return of(resourceKey, settings(), height());
    }

    @Override
    @AsOf("3.4.0")
    NetherFossilStructure register();

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
     * Creates a new nether fossil.
     * @param resourceKey the resource key of the structure, or null if not present
     * @param settings the settings every structure carries
     * @param height the height the fossil starts searching downward from
     * @return a new nether fossil
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static NetherFossilStructure of(@Nullable ResourceKey resourceKey, StructureSettings settings, HeightProvider height) {
        record Holder() {
            static final ConstructWireProvider<NetherFossilStructure> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.types.NetherFossilStructureImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), settings, height);
    }

    /**
     * Creates a new builder with vanilla's height: anywhere from y=32 up to two blocks below the
     * dimension's ceiling.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder(StructureSettings settings) {
        return new Builder(settings);
    }

    /**
     * Builder for {@link NetherFossilStructure}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private StructureSettings settings;
        private HeightProvider height = HeightProvider.uniform(VerticalAnchor.absolute(32), VerticalAnchor.belowTop(2));

        public Builder(StructureSettings settings) {
            this.settings = settings;
        }

        public Builder(NetherFossilStructure structure) {
            this.resourceKey = structure.resourceKey().orElse(null);
            this.settings = structure.settings();
            this.height = structure.height();
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
         * Sets the height the fossil starts searching downward from for solid ground.
         * @param height the starting height
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder height(HeightProvider height) {
            this.height = height;
            return this;
        }

        /**
         * Builds the structure.
         * @return the structure
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public NetherFossilStructure build() {
            return of(resourceKey, settings, height);
        }
    }
}
