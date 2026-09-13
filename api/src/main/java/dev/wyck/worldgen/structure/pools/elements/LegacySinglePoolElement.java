package dev.wyck.worldgen.structure.pools.elements;

import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.worldgen.structure.pools.PoolElement;
import dev.wyck.worldgen.structure.pools.Projection;
import dev.wyck.worldgen.structure.templatesystem.LiquidSettings;
import dev.wyck.worldgen.structure.templatesystem.ProcessorList;
import dev.wyck.worldgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Places a structure template the way pre-1.14 villages did. Any air saved in the template is placed too, clearing what was there.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface LegacySinglePoolElement extends PoolElement {

    /**
     * The template this element places.
     * @return the template
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    StructureTemplate template();

    /**
     * The processors the template is run through as it is placed.
     * @return the processor list
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    ProcessorList processors();

    /**
     * Overrides the structure's own waterlogging behaviour for this element.
     * @return the liquid settings override, if present
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Optional<LiquidSettings> overrideLiquidSettings();

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
     * Creates a new element.
     * @param template the template to place
     * @param processors the processors the template is run through
     * @param projection how the template is fitted to the terrain
     * @param overrideLiquidSettings the waterlogging override, or null to use the structure's
     * @return a new element
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static LegacySinglePoolElement of(StructureTemplate template, ProcessorList processors, Projection projection, @Nullable LiquidSettings overrideLiquidSettings) {
        record Holder() {
            static final ConstructWireProvider<LegacySinglePoolElement> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.pools.elements.LegacySinglePoolElementImpl");
        }
        return Holder.WIRE.construct(template, processors, projection, Optional.ofNullable(overrideLiquidSettings));
    }

    /**
     * Creates a new builder.
     * @param template the template to place
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder(StructureTemplate template) {
        return new Builder(template);
    }

    /**
     * Builder for {@link LegacySinglePoolElement}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private StructureTemplate template;
        private ProcessorList processors = ProcessorList.EMPTY;
        private Projection projection = Projection.RIGID;
        private @Nullable LiquidSettings overrideLiquidSettings;

        public Builder(StructureTemplate template) {
            this.template = template;
        }

        public Builder(LegacySinglePoolElement element) {
            this.template = element.template();
            this.processors = element.processors();
            this.projection = element.projection();
            this.overrideLiquidSettings = element.overrideLiquidSettings().orElse(null);
        }

        /**
         * Sets the template this element places.
         * @param template the template
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder template(StructureTemplate template) {
            this.template = template;
            return this;
        }

        /**
         * Sets the processors the template is run through.
         * @param processors the processor list
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder processors(ProcessorList processors) {
            this.processors = processors;
            return this;
        }

        /**
         * Sets how the template is fitted to the terrain.
         * @param projection the projection
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder projection(Projection projection) {
            this.projection = projection;
            return this;
        }

        /**
         * Overrides the structure's own waterlogging behaviour for this element.
         * @param overrideLiquidSettings the liquid settings override
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder overrideLiquidSettings(LiquidSettings overrideLiquidSettings) {
            this.overrideLiquidSettings = overrideLiquidSettings;
            return this;
        }

        /**
         * Builds the element.
         * @return the element
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public LegacySinglePoolElement build() {
            return of(template, processors, projection, overrideLiquidSettings);
        }
    }
}
