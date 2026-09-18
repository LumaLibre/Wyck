package dev.wyck.worldgen.structure.pools;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.placement.PlacedFeature;
import dev.wyck.worldgen.structure.pools.elements.EmptyPoolElement;
import dev.wyck.worldgen.structure.pools.elements.FeaturePoolElement;
import dev.wyck.worldgen.structure.pools.elements.LegacySinglePoolElement;
import dev.wyck.worldgen.structure.pools.elements.ListPoolElement;
import dev.wyck.worldgen.structure.pools.elements.SinglePoolElement;
import dev.wyck.worldgen.structure.templatesystem.StructureTemplate;
import dev.wyck.wrapper.Wrapper;
import dev.wyck.wrapper.decode.Decoder;
import org.jspecify.annotations.NullMarked;

import java.util.List;

/**
 * One piece a {@link TemplatePool} can place.
 *
 * @see <a href="https://minecraft.wiki/w/Template_pool">Template pool</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface PoolElement extends Wrapper {

    /**
     * How this element is fitted to the terrain.
     * @return the projection of this element
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Projection projection();

    /**
     * Places a structure template, rigidly.
     * @param template the template to place
     * @return a single-template element
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static SinglePoolElement single(StructureTemplate template) {
        return SinglePoolElement.builder(template).build();
    }

    /**
     * Places a structure template, keeping the air blocks it was saved with instead of skipping them.
     * @param template the template to place
     * @return a legacy single-template element
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static LegacySinglePoolElement legacy(StructureTemplate template) {
        return LegacySinglePoolElement.builder(template).build();
    }

    /**
     * Places several elements at the same spot, in order.
     * @param projection how the elements are fitted to the terrain
     * @param elements the elements to place
     * @return a list element
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static ListPoolElement list(Projection projection, List<PoolElement> elements) {
        return ListPoolElement.of(projection, elements);
    }

    /**
     * Places a placed feature rather than a template.
     * @param projection how the feature is fitted to the terrain
     * @param feature the feature to place
     * @return a feature element
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static FeaturePoolElement feature(Projection projection, PlacedFeature feature) {
        return FeaturePoolElement.of(projection, feature);
    }

    /**
     * Places nothing. Weighting it against other elements makes a jigsaw block sometimes stay empty.
     * @return the empty element
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static EmptyPoolElement empty() {
        return EmptyPoolElement.INSTANCE;
    }

    /**
     * Reads a Minecraft pool element.
     * @param minecraftElement the pool element to read
     * @return the decoded pool element
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static PoolElement decode(Object minecraftElement) {
        record Holder() {
            static final Decoder<PoolElement> DECODER = Decoder.create("dev.wyck.decode.worldgen.structure.pools.PoolElementDecoders");
        }
        return Holder.DECODER.decode(minecraftElement);
    }
}
