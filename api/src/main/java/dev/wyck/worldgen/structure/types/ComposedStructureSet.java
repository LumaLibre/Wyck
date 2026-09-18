package dev.wyck.worldgen.structure.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.Structure;
import dev.wyck.worldgen.structure.placement.StructurePlacement;
import dev.wyck.worldgen.structure.StructureSet;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A structure set authored from, or decoded into, its entries and placement.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface ComposedStructureSet extends StructureSet, Registerable<ComposedStructureSet> {

    /**
     * The resource key of the structure set.
     * @return the resource key of the structure set, if present
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Optional<ResourceKey> resourceKey();

    /**
     * The weighted structures in this set.
     * @return the entries of this set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    List<Entry> structures();

    /**
     * How this set spreads its structures.
     * @return the placement of this set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    StructurePlacement placement();

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
     * Creates a new composed structure set.
     * @param resourceKey the resource key of the set, or null if not present
     * @param structures the weighted structures in the set
     * @param placement how the set spreads its structures
     * @return a new composed structure set
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ComposedStructureSet of(@Nullable ResourceKey resourceKey, List<Entry> structures, StructurePlacement placement) {
        record Holder() {
            static final ConstructWireProvider<ComposedStructureSet> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.types.ComposedStructureSetImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), List.copyOf(structures), placement);
    }

    /**
     * Creates a new builder.
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ComposedStructureSet}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private List<Entry> structures = new ArrayList<>();
        private @Nullable StructurePlacement placement;

        public Builder() {}

        public Builder(ComposedStructureSet set) {
            this.resourceKey = set.resourceKey().orElse(null);
            this.structures = new ArrayList<>(set.structures());
            this.placement = set.placement();
        }

        /**
         * Sets the resource key of the structure set.
         * @param resourceKey the resource key of the structure set
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the weighted structures in this set.
         * @param structures the entries of this set
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder structures(List<Entry> structures) {
            this.structures = new ArrayList<>(structures);
            return this;
        }

        /**
         * Sets how this set spreads its structures.
         * @param placement the placement of this set
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder placement(StructurePlacement placement) {
            this.placement = placement;
            return this;
        }

        // Friendly

        /**
         * Adds a structure to this set with a weight of 1.
         * @param structure the structure to add
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder structure(Structure structure) {
            this.structures.add(Entry.of(structure));
            return this;
        }

        /**
         * Adds a structure to this set with the given weight.
         * @param structure the structure to add
         * @param weight the weight the structure is chosen by
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder structure(Structure structure, int weight) {
            this.structures.add(Entry.of(structure, weight));
            return this;
        }

        /**
         * Builds the composed structure set.
         * @return the composed structure set
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public ComposedStructureSet build() {
            Preconditions.checkNotNull(placement, "placement must be set");
            Preconditions.checkArgument(!structures.isEmpty(), "structures must not be empty");
            return of(resourceKey, structures, placement);
        }
    }
}
