package dev.wyck.worldgen.feature.configurations;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.structure.templatesystem.ProcessorList;
import dev.wyck.worldgen.structure.templatesystem.StructureTemplate;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A fossil is a rarely-occurring skeletal feature composed of bone blocks, coal ore above Y=0, or diamond ore below Y=-8.
 *
 * @see <a href="https://minecraft.wiki/w/Fossil">Fossil</a>
 * @since 3.3.0
 * @version 3.3.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.3.0")
public interface FossilFeature extends FeatureConfiguration {

    /**
     * Gets the templates the fossil skeleton is stamped from.
     * @return the fossil templates
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    List<StructureTemplate> fossilStructures();

    /**
     * Gets the templates layered over the fossil, one per fossil template.
     * @return the overlay templates
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    List<StructureTemplate> overlayStructures();

    /**
     * Gets the processors the fossil templates are run through.
     * @return the fossil processor list
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    ProcessorList fossilProcessors();

    /**
     * Gets the processors the overlay templates are run through.
     * @return the overlay processor list
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    ProcessorList overlayProcessors();

    /**
     * Gets the maximum number of empty corners allowed in the fossil feature configuration.
     * @return The maximum number of empty corners allowed.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    int maxEmptyCornersAllowed();

    /**
     * Converts this object back to a builder.
     * @return A new builder with these values
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new instance of {@link FossilFeature} with the specified parameters.
     * @param fossilStructures the templates the fossil skeleton is stamped from.
     * @param overlayStructures the templates layered over the fossil.
     * @param fossilProcessors the processors the fossil templates are run through.
     * @param overlayProcessors the processors the overlay templates are run through.
     * @param maxEmptyCornersAllowed The maximum number of empty corners allowed.
     * @return A new instance of {@link FossilFeature}.
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static FossilFeature of(List<StructureTemplate> fossilStructures, List<StructureTemplate> overlayStructures, ProcessorList fossilProcessors, ProcessorList overlayProcessors, int maxEmptyCornersAllowed) {
        record Holder() {
            static final ConstructWireProvider<FossilFeature> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.feature.configurations.FossilFeatureImpl");
        }
        return Holder.WIRE.construct(List.copyOf(fossilStructures), List.copyOf(overlayStructures), fossilProcessors, overlayProcessors, maxEmptyCornersAllowed);
    }

    /**
     * Creates a new builder.
     * @return A new builder
     * @since 3.3.0
     */
    @AsOf("3.3.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link FossilFeature}.
     * @since 3.3.0
     * @version 3.3.0
     * @author Jsinco
     */
    @AsOf("3.3.0")
    final class Builder {
        private List<StructureTemplate> fossilStructures = new ArrayList<>();
        private List<StructureTemplate> overlayStructures = new ArrayList<>();
        private @Nullable ProcessorList fossilProcessors;
        private @Nullable ProcessorList overlayProcessors;
        private int maxEmptyCornersAllowed;

        public Builder() {}

        public Builder(FossilFeature config) {
            this.fossilStructures.addAll(config.fossilStructures());
            this.overlayStructures.addAll(config.overlayStructures());
            this.fossilProcessors = config.fossilProcessors();
            this.overlayProcessors = config.overlayProcessors();
            this.maxEmptyCornersAllowed = config.maxEmptyCornersAllowed();
        }

        /**
         * Sets the fossil structures for the fossil feature configuration.
         * @param fossilStructures the fossil structures
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder fossilStructures(List<StructureTemplate> fossilStructures) {
            this.fossilStructures = fossilStructures;
            return this;
        }

        /**
         * Sets the overlay structures for the fossil feature configuration.
         * @param overlayStructures the overlay structures
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder overlayStructures(List<StructureTemplate> overlayStructures) {
            this.overlayStructures = overlayStructures;
            return this;
        }

        /**
         * Sets the fossil processors for the fossil feature configuration.
         * @param fossilProcessors the fossil processors
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder fossilProcessors(ProcessorList fossilProcessors) {
            this.fossilProcessors = fossilProcessors;
            return this;
        }

        /**
         * Sets the overlay processors for the fossil feature configuration.
         * @param overlayProcessors the overlay processors
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder overlayProcessors(ProcessorList overlayProcessors) {
            this.overlayProcessors = overlayProcessors;
            return this;
        }

        /**
         * Sets the maximum number of empty corners allowed.
         * @param maxEmptyCornersAllowed the maximum number of empty corners allowed
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder maxEmptyCornersAllowed(int maxEmptyCornersAllowed) {
            this.maxEmptyCornersAllowed = maxEmptyCornersAllowed;
            return this;
        }

        // Friendly builder methods

        /**
         * Adds a fossil structure to the list of fossil structures.
         * @param fossilStructure the fossil structure to add
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder fossilStructure(StructureTemplate fossilStructure) {
            this.fossilStructures.add(fossilStructure);
            return this;
        }

        /**
         * Adds an overlay structure to the list of overlay structures.
         * @param overlayStructure the overlay structure to add
         * @return this builder
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public Builder overlayStructure(StructureTemplate overlayStructure) {
            this.overlayStructures.add(overlayStructure);
            return this;
        }

        /**
         * Builds the {@link FossilFeature} instance.
         * @return A new instance of {@link FossilFeature} with the specified parameters.
         * @since 3.3.0
         */
        @AsOf("3.3.0")
        public FossilFeature build() {
            Preconditions.checkNotNull(fossilProcessors, "fossilProcessors must be set");
            Preconditions.checkNotNull(overlayProcessors, "overlayProcessors must be set");
            return of(fossilStructures, overlayStructures, fossilProcessors, overlayProcessors, maxEmptyCornersAllowed);
        }
    }
}
