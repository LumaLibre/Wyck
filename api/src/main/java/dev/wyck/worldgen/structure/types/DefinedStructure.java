package dev.wyck.worldgen.structure.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.Structure;
import dev.wyck.worldgen.structure.StructureSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * A structure whose definition is present, as opposed to a {@link ReferencedStructure}.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface DefinedStructure extends Structure {

    /**
     * The resource key of the structure.
     * @return the resource key of the structure, if present
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Optional<ResourceKey> resourceKey();

    /**
     * The settings every structure carries.
     * @return the settings of the structure
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    StructureSettings settings();

    /**
     * Returns a copy of this structure with the given settings.
     * @param settings the settings the copy carries
     * @return a copy of this structure with the given settings
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    DefinedStructure withSettings(StructureSettings settings);

    /**
     * Returns a copy of this structure whose settings are the result of applying {@code mutator} to
     * this structure's settings.
     * @param mutator the operator to apply to this structure's settings
     * @return a copy of this structure with the mutated settings
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    default DefinedStructure withSettings(UnaryOperator<StructureSettings> mutator) {
        return withSettings(mutator.apply(settings()));
    }

    /**
     * Returns a copy of this structure registered under the given key.
     * @param resourceKey the key the copy carries
     * @return a copy of this structure with the given key
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    DefinedStructure withResourceKey(ResourceKey resourceKey);

    /**
     * Registers this structure under its resource key.
     * @return this structure
     * @throws java.util.NoSuchElementException if this structure has no resource key
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    DefinedStructure register();
}
