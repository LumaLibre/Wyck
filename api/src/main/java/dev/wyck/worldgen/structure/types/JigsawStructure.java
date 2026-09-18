package dev.wyck.worldgen.structure.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.HeightmapType;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.worldgen.structure.TerrainAdaptation;
import dev.wyck.worldgen.structure.pools.DimensionPadding;
import dev.wyck.worldgen.structure.pools.TemplatePool;
import dev.wyck.worldgen.structure.pools.alias.PoolAliasBinding;
import dev.wyck.worldgen.structure.templatesystem.LiquidSettings;
import dev.wyck.wrapper.Registerable;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * A structure that grows outward from a piece drawn from its {@link #startPool() start pool},
 * attaching a new piece at each jigsaw block until {@link #size()} steps from the start.
 *
 * @see <a href="https://learn.microsoft.com/en-us/minecraft/creator/documents/structures/introductiontojigsawstructures">Jigsaw Structures (Bedrock)</a>
 * @see <a href="https://minecraft.wiki/w/Jigsaw_structure">Jigsaw Structure (The structure block)</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public interface JigsawStructure extends DefinedStructure, Registerable<JigsawStructure> {

    int MAX_SIZE = 20;
    int MAX_TOTAL_STRUCTURE_RANGE = 128;

    /**
     * The resource key of the structure.
     * @return the resource key of the structure, if present
     * @since 3.4.0
     */
    @Override
    @AsOf("3.4.0")
    Optional<ResourceKey> resourceKey();

    /**
     * The settings every structure carries.
     * @return the settings of the structure
     * @since 3.4.0
     */
    @Override
    @AsOf("3.4.0")
    StructureSettings settings();

    /**
     * The pool the first piece is drawn from.
     * @return the start pool
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    TemplatePool startPool();

    /**
     * The name of the jigsaw block in the start piece that is anchored to the structure's position.
     * @return the start jigsaw name, if present
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Optional<ResourceKey> startJigsawName();

    /**
     * How many jigsaw steps the structure grows from its start piece, from 0 to {@value #MAX_SIZE}.
     * @return the maximum depth
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    int size();

    /**
     * The height the start piece is placed at, relative to the heightmap when
     * {@link #projectStartToHeightmap()} is set.
     * @return the start height
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    HeightProvider startHeight();

    /**
     * Vanilla's village expansion hack, which lets pieces extend past their bounding box.
     * @return whether the expansion hack is used
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    boolean useExpansionHack();

    /**
     * The heightmap the start height is measured from. Absent, the start height is absolute.
     * @return the heightmap, if present
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    Optional<HeightmapType> projectStartToHeightmap();

    /**
     * The furthest pieces may be placed from the start.
     * @return the maximum distance from the center
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    MaxDistance maxDistanceFromCenter();

    /**
     * The pool aliases resolved once per structure.
     * @return the pool alias bindings
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    List<PoolAliasBinding> poolAliases();

    /**
     * The clearance kept from the bottom and top of the dimension.
     * @return the dimension padding
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    DimensionPadding dimensionPadding();

    /**
     * Whether pieces landing in water are waterlogged.
     * @return the liquid settings
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    LiquidSettings liquidSettings();

    /**
     * Returns a copy of this structure with the given settings.
     * @param settings the settings the copy carries
     * @return a copy of this structure with the given settings
     * @since 3.4.0
     */
    @Override
    @AsOf("3.4.0")
    default JigsawStructure withSettings(StructureSettings settings) {
        return toBuilder().settings(settings).build();
    }

    @Override
    @AsOf("3.4.0")
    default JigsawStructure withResourceKey(ResourceKey resourceKey) {
        return toBuilder().resourceKey(resourceKey).build();
    }

    @Override
    @AsOf("3.4.0")
    JigsawStructure register();

    /**
     * Returns a copy of this structure whose settings are the result of applying {@code mutator}.
     * @param mutator the operator to apply to this structure's settings
     * @return a copy of this structure with the mutated settings
     * @since 3.4.0
     */
    @Override
    @AsOf("3.4.0")
    default JigsawStructure withSettings(UnaryOperator<StructureSettings> mutator) {
        return withSettings(mutator.apply(settings()));
    }

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
     * Creates a new jigsaw structure.
     * @param resourceKey the resource key of the structure, or null if not present
     * @param settings the settings every structure carries
     * @param startPool the pool the first piece is drawn from
     * @param startJigsawName the jigsaw block in the start piece anchored to the structure's position, or null
     * @param size how many jigsaw steps the structure grows from its start piece
     * @param startHeight the height the start piece is placed at
     * @param useExpansionHack whether vanilla's village expansion hack is used
     * @param projectStartToHeightmap the heightmap the start height is measured from, or null for an absolute height
     * @param maxDistanceFromCenter the furthest pieces may be placed from the start
     * @param poolAliases the pool aliases resolved once per structure
     * @param dimensionPadding the clearance kept from the bottom and top of the dimension
     * @param liquidSettings whether pieces landing in water are waterlogged
     * @return a new jigsaw structure
     * @throws IllegalArgumentException if the size is out of range, or the horizontal reach plus the
     *         terrain adaptation's margin exceeds {@value #MAX_TOTAL_STRUCTURE_RANGE}
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static JigsawStructure of(@Nullable ResourceKey resourceKey, StructureSettings settings, TemplatePool startPool, @Nullable ResourceKey startJigsawName, int size, HeightProvider startHeight, boolean useExpansionHack, @Nullable HeightmapType projectStartToHeightmap, MaxDistance maxDistanceFromCenter, List<PoolAliasBinding> poolAliases, DimensionPadding dimensionPadding, LiquidSettings liquidSettings) {
        Preconditions.checkArgument(size >= 0 && size <= MAX_SIZE, "size must be between 0 and %s, got %s", MAX_SIZE, size);
        int margin = settings.terrainAdaptation() == TerrainAdaptation.NONE ? 0 : 12;
        Preconditions.checkArgument(maxDistanceFromCenter.horizontal() + margin <= MAX_TOTAL_STRUCTURE_RANGE,
            "horizontal reach including terrain adaptation must not exceed %s, got %s", MAX_TOTAL_STRUCTURE_RANGE, maxDistanceFromCenter.horizontal() + margin);

        record Holder() {
            static final ConstructWireProvider<JigsawStructure> WIRE = ConstructWireProvider.create("dev.wyck.worldgen.structure.types.JigsawStructureImpl");
        }
        return Holder.WIRE.construct(
            Optional.ofNullable(resourceKey), settings, startPool, Optional.ofNullable(startJigsawName), size, startHeight,
            useExpansionHack, Optional.ofNullable(projectStartToHeightmap), maxDistanceFromCenter, List.copyOf(poolAliases),
            dimensionPadding, liquidSettings
        );
    }

    /**
     * Creates a new builder.
     * @param settings the settings every structure carries
     * @param startPool the pool the first piece is drawn from
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder(StructureSettings settings, TemplatePool startPool) {
        return new Builder(settings, startPool);
    }

    /**
     * How far from the start piece a jigsaw structure may place pieces.
     *
     * @param horizontal the horizontal reach in blocks, from 1 to {@value JigsawStructure#MAX_TOTAL_STRUCTURE_RANGE}
     * @param vertical the vertical reach in blocks
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    record MaxDistance(int horizontal, int vertical) {

        /** The vertical reach vanilla uses when none is given: the full height of a dimension. */
        @AsOf("3.4.0")
        public static final int DEFAULT_VERTICAL = 4064;

        public MaxDistance {
            Preconditions.checkArgument(horizontal >= 1 && horizontal <= MAX_TOTAL_STRUCTURE_RANGE,
                "horizontal must be between 1 and %s, got %s", MAX_TOTAL_STRUCTURE_RANGE, horizontal);
            Preconditions.checkArgument(vertical >= 1 && vertical <= DEFAULT_VERTICAL,
                "vertical must be between 1 and %s, got %s", DEFAULT_VERTICAL, vertical);
        }

        /**
         * Creates a maximum distance that is the same horizontally and vertically, as vanilla's
         * single-number form does.
         * @param distance the reach in blocks
         * @return the maximum distance
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public static MaxDistance of(int distance) {
            return new MaxDistance(distance, distance);
        }

        /**
         * Creates a maximum distance.
         * @param horizontal the horizontal reach in blocks
         * @param vertical the vertical reach in blocks
         * @return the maximum distance
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public static MaxDistance of(int horizontal, int vertical) {
            return new MaxDistance(horizontal, vertical);
        }
    }

    /**
     * Builder for {@link JigsawStructure}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private StructureSettings settings;
        private TemplatePool startPool;
        private @Nullable ResourceKey startJigsawName;
        private int size = 1;
        private HeightProvider startHeight = HeightProvider.constant(VerticalAnchor.absolute(0));
        private boolean useExpansionHack = false;
        private @Nullable HeightmapType projectStartToHeightmap;
        private MaxDistance maxDistanceFromCenter = MaxDistance.of(80);
        private List<PoolAliasBinding> poolAliases = new ArrayList<>();
        private DimensionPadding dimensionPadding = DimensionPadding.ZERO;
        private LiquidSettings liquidSettings = LiquidSettings.APPLY_WATERLOGGING;

        public Builder(StructureSettings settings, TemplatePool startPool) {
            this.settings = settings;
            this.startPool = startPool;
        }

        public Builder(JigsawStructure structure) {
            this.resourceKey = structure.resourceKey().orElse(null);
            this.settings = structure.settings();
            this.startPool = structure.startPool();
            this.startJigsawName = structure.startJigsawName().orElse(null);
            this.size = structure.size();
            this.startHeight = structure.startHeight();
            this.useExpansionHack = structure.useExpansionHack();
            this.projectStartToHeightmap = structure.projectStartToHeightmap().orElse(null);
            this.maxDistanceFromCenter = structure.maxDistanceFromCenter();
            this.poolAliases = new ArrayList<>(structure.poolAliases());
            this.dimensionPadding = structure.dimensionPadding();
            this.liquidSettings = structure.liquidSettings();
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
         * Sets the pool the first piece is drawn from.
         * @param startPool the start pool
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder startPool(TemplatePool startPool) {
            this.startPool = startPool;
            return this;
        }

        /**
         * Sets the jigsaw block in the start piece anchored to the structure's position.
         * @param startJigsawName the start jigsaw name
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder startJigsawName(ResourceKey startJigsawName) {
            this.startJigsawName = startJigsawName;
            return this;
        }

        /**
         * Sets how many jigsaw steps the structure grows from its start piece.
         * @param size the maximum depth
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder size(int size) {
            this.size = size;
            return this;
        }

        /**
         * Sets the height the start piece is placed at.
         * @param startHeight the start height
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder startHeight(HeightProvider startHeight) {
            this.startHeight = startHeight;
            return this;
        }

        /**
         * Sets whether vanilla's village expansion hack is used.
         * @param useExpansionHack whether the expansion hack is used
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder useExpansionHack(boolean useExpansionHack) {
            this.useExpansionHack = useExpansionHack;
            return this;
        }

        /**
         * Sets the heightmap the start height is measured from.
         * @param projectStartToHeightmap the heightmap
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder projectStartToHeightmap(HeightmapType projectStartToHeightmap) {
            this.projectStartToHeightmap = projectStartToHeightmap;
            return this;
        }

        /**
         * Sets the furthest pieces may be placed from the start.
         * @param maxDistanceFromCenter the maximum distance from the center
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder maxDistanceFromCenter(MaxDistance maxDistanceFromCenter) {
            this.maxDistanceFromCenter = maxDistanceFromCenter;
            return this;
        }

        /**
         * Sets the pool aliases resolved once per structure.
         * @param poolAliases the pool alias bindings
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder poolAliases(List<PoolAliasBinding> poolAliases) {
            this.poolAliases = new ArrayList<>(poolAliases);
            return this;
        }

        /**
         * Sets the clearance kept from the bottom and top of the dimension.
         * @param dimensionPadding the dimension padding
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder dimensionPadding(DimensionPadding dimensionPadding) {
            this.dimensionPadding = dimensionPadding;
            return this;
        }

        /**
         * Sets whether pieces landing in water are waterlogged.
         * @param liquidSettings the liquid settings
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder liquidSettings(LiquidSettings liquidSettings) {
            this.liquidSettings = liquidSettings;
            return this;
        }

        // Friendly

        /**
         * Adds a pool alias binding.
         * @param poolAlias the binding to add
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder poolAlias(PoolAliasBinding poolAlias) {
            this.poolAliases.add(poolAlias);
            return this;
        }

        /**
         * Builds the jigsaw structure.
         * @return the jigsaw structure
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public JigsawStructure build() {
            return of(resourceKey, settings, startPool, startJigsawName, size, startHeight, useExpansionHack,
                projectStartToHeightmap, maxDistanceFromCenter, poolAliases, dimensionPadding, liquidSettings);
        }
    }
}
