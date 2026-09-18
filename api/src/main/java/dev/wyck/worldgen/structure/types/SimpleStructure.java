package dev.wyck.worldgen.structure.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.worldgen.structure.StructureType;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

/**
 * A structure whose layout is written in Java and takes no configuration beyond its
 * {@link StructureSettings settings}.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface SimpleStructure extends DefinedStructure, Registerable<SimpleStructure> {

    Set<StructureType> TYPES = Set.of(
        StructureType.BURIED_TREASURE,
        StructureType.DESERT_PYRAMID,
        StructureType.END_CITY,
        StructureType.FORTRESS,
        StructureType.IGLOO,
        StructureType.JUNGLE_TEMPLE,
        StructureType.OCEAN_MONUMENT,
        StructureType.STRONGHOLD,
        StructureType.SWAMP_HUT,
        StructureType.WOODLAND_MANSION
    );

    /**
     * The structure type, one of {@link #TYPES}.
     * @return the structure type
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    StructureType type();

    @Override
    @AsOf("3.4.0")
    default SimpleStructure withSettings(StructureSettings settings) {
        return of(resourceKey().orElse(null), type(), settings);
    }

    @Override
    @AsOf("3.4.0")
    default SimpleStructure withResourceKey(ResourceKey resourceKey) {
        return of(resourceKey, type(), settings());
    }

    @Override
    @AsOf("3.4.0")
    SimpleStructure register();

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
     * Creates a new structure of a type that takes no configuration.
     * @param resourceKey the resource key of the structure, or null if not present
     * @param type the structure type, one of {@link #TYPES}
     * @param settings the settings every structure carries
     * @return a new structure
     * @throws IllegalArgumentException if the type takes configuration of its own
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure of(@Nullable ResourceKey resourceKey, StructureType type, StructureSettings settings) {
        Preconditions.checkArgument(TYPES.contains(type), "%s takes configuration of its own; use its own wrapper", type);
        record Holder() {
            static final ConstructWireProvider<SimpleStructure> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.types.SimpleStructureImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), type, settings);
    }

    /**
     * Creates a new structure of a type that takes no configuration.
     * @param type the structure type, one of {@link #TYPES}
     * @param settings the settings every structure carries
     * @return a new structure
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SimpleStructure of(StructureType type, StructureSettings settings) {
        return of(null, type, settings);
    }

    /**
     * Creates a new builder.
     * @param type the structure type, one of {@link #TYPES}
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder(StructureType type, StructureSettings settings) {
        return new Builder(type, settings);
    }

    /**
     * Builder for {@link SimpleStructure}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private StructureType type;
        private StructureSettings settings;

        public Builder(StructureType type, StructureSettings settings) {
            this.type = type;
            this.settings = settings;
        }

        public Builder(SimpleStructure structure) {
            this.resourceKey = structure.resourceKey().orElse(null);
            this.type = structure.type();
            this.settings = structure.settings();
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
         * Sets the structure type.
         * @param type the structure type, one of {@link #TYPES}
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder type(StructureType type) {
            this.type = type;
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
         * Builds the structure.
         * @return the structure
         * @throws IllegalArgumentException if the type takes configuration of its own
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public SimpleStructure build() {
            return of(resourceKey, type, settings);
        }
    }
}
