package dev.wyck.worldgen.structure.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.wrapper.Registerable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * A composable structure.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface ComposedStructure extends DefinedStructure, Registerable<ComposedStructure> {

    /**
     * The resource key of the structure.
     * @return the resource key of the structure, if present
     * @since 3.4.0
     */
    @Override
    @AsOf("3.4.0")
    Optional<ResourceKey> resourceKey();

    /**
     * The settings of the structure.
     * @return the settings of the structure
     * @since 3.4.0
     */
    @Override
    @AsOf("3.4.0")
    StructureSettings settings();

    /**
     * Returns a copy of this structure with the given settings, keeping its type-specific half.
     * @param settings the settings the copy carries
     * @return a copy of this structure with the given settings
     * @since 3.4.0
     */
    @Override
    @AsOf("3.4.0")
    ComposedStructure withSettings(StructureSettings settings);

    @Override
    @AsOf("3.4.0")
    default ComposedStructure withResourceKey(ResourceKey resourceKey) {
        return toBuilder().resourceKey(resourceKey).build();
    }

    @Override
    @AsOf("3.4.0")
    ComposedStructure register();

    /**
     * Returns a copy of this structure whose settings are the result of applying {@code mutator} to
     * this structure's settings.
     * @param mutator the operator to apply to this structure's settings
     * @return a copy of this structure with the mutated settings
     * @since 3.4.0
     */
    @Override
    @AsOf("3.4.0")
    default ComposedStructure withSettings(UnaryOperator<StructureSettings> mutator) {
        return withSettings(mutator.apply(settings()));
    }

    /**
     * The Minecraft structure carrying this structure's type-specific half, with this wrapper's
     * settings not yet applied.
     * @return the Minecraft structure this wrapper was decoded from
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    @ApiStatus.Internal
    Object unwrapType();

    /**
     * Converts this composed structure to a builder.
     * @return a builder with the same values as this composed structure
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new composed structure around an already-built Minecraft structure.
     * @param resourceKey the resource key of the structure, or null if not present
     * @param settings the settings of the structure
     * @param minecraftStructure the Minecraft structure carrying the type-specific half
     * @return a new composed structure
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    @ApiStatus.Internal
    static ComposedStructure of(@Nullable ResourceKey resourceKey, StructureSettings settings, Object minecraftStructure) {
        record Holder() {
            static final ConstructWireProvider<ComposedStructure> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.types.ComposedStructureImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), settings, minecraftStructure);
    }

    /**
     * Builder for {@link ComposedStructure}. A builder can only be obtained from an existing
     * composed structure, since the type-specific half cannot be authored.
     *
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private final Object minecraftStructure;
        private @Nullable ResourceKey resourceKey;
        private StructureSettings settings;

        public Builder(ComposedStructure structure) {
            this.minecraftStructure = structure.unwrapType();
            this.resourceKey = structure.resourceKey().orElse(null);
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
         * Sets the settings of the structure.
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
         * Builds the composed structure.
         * @return the composed structure
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public ComposedStructure build() {
            return of(resourceKey, settings, minecraftStructure);
        }
    }
}
