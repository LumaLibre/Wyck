package dev.wyck.worldgen.structure.templatesystem.types;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.templatesystem.ProcessorList;
import dev.wyck.worldgen.structure.templatesystem.processor.StructureProcessor;
import dev.wyck.wrapper.Registerable;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A processor list authored from or decoded into its processors.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface ComposedProcessorList extends ProcessorList, Registerable<ComposedProcessorList> {

    /**
     * The resource key of the processor list.
     * @return the resource key of the processor list, if present
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Optional<ResourceKey> resourceKey();

    /**
     * The processors run, in order.
     * @return the processors
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    List<StructureProcessor> processors();

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
     * Creates a new composed processor list.
     * @param resourceKey the resource key of the list, or null if not present
     * @param processors the processors run, in order
     * @return a new composed processor list
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ComposedProcessorList of(@Nullable ResourceKey resourceKey, List<StructureProcessor> processors) {
        record Holder() {
            static final ConstructWireProvider<ComposedProcessorList> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.templatesystem.types.ComposedProcessorListImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), List.copyOf(processors));
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
     * Builder for {@link ComposedProcessorList}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private List<StructureProcessor> processors = new ArrayList<>();

        public Builder() {}

        public Builder(ComposedProcessorList list) {
            this.resourceKey = list.resourceKey().orElse(null);
            this.processors = new ArrayList<>(list.processors());
        }

        /**
         * Sets the resource key of the processor list.
         * @param resourceKey the resource key of the processor list
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        /**
         * Sets the processors run, in order.
         * @param processors the processors
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder processors(List<StructureProcessor> processors) {
            this.processors = new ArrayList<>(processors);
            return this;
        }

        // Friendly

        /**
         * Adds a processor to the end of the list.
         * @param processor the processor to add
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder processor(StructureProcessor processor) {
            this.processors.add(processor);
            return this;
        }

        /**
         * Builds the composed processor list.
         * @return the composed processor list
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public ComposedProcessorList build() {
            return of(resourceKey, processors);
        }
    }
}
