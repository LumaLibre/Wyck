package dev.wyck.worldgen.structure.types;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.factory.ConstructWireProvider;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.StructureSettings;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.Registerable;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A ruined portal.
 *
 * @see <a href="https://minecraft.wiki/w/Ruined_Portal">Ruined Portal</a>
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
@ApiStatus.Experimental
public interface RuinedPortalStructure extends DefinedStructure, Registerable<RuinedPortalStructure> {

    /**
     * The setups a portal picks between, by weight.
     * @return the setups
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    List<Setup> setups();

    @Override
    @AsOf("3.4.0")
    default RuinedPortalStructure withSettings(StructureSettings settings) {
        return toBuilder().settings(settings).build();
    }

    @Override
    @AsOf("3.4.0")
    default RuinedPortalStructure withResourceKey(ResourceKey resourceKey) {
        return toBuilder().resourceKey(resourceKey).build();
    }

    @Override
    @AsOf("3.4.0")
    RuinedPortalStructure register();

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
     * Creates a new ruined portal.
     * @param resourceKey the resource key of the structure, or null if not present
     * @param settings the settings every structure carries
     * @param setups the setups a portal picks between, which must not be empty
     * @return a new ruined portal
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static RuinedPortalStructure of(@Nullable ResourceKey resourceKey, StructureSettings settings, List<Setup> setups) {
        Preconditions.checkArgument(!setups.isEmpty(), "setups must not be empty");
        record Holder() {
            static final ConstructWireProvider<RuinedPortalStructure> WIRE =
                ConstructWireProvider.create("dev.wyck.worldgen.structure.types.RuinedPortalStructureImpl");
        }
        return Holder.WIRE.construct(Optional.ofNullable(resourceKey), settings, List.copyOf(setups));
    }

    /**
     * Creates a new builder.
     * @param settings the settings every structure carries
     * @return a new builder
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    static Builder builder(StructureSettings settings) {
        return new Builder(settings);
    }

    /**
     * Where a ruined portal is placed relative to the terrain.
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    enum Placement implements WrappedEnumerator<Placement> {
        /** On top of the land surface. */
        ON_LAND_SURFACE("ON_LAND_SURFACE"),

        /** Sunk part way into the land surface. */
        PARTLY_BURIED("PARTLY_BURIED"),

        /** On the ocean floor. */
        ON_OCEAN_FLOOR("ON_OCEAN_FLOOR"),

        /** Inside a mountain, below its surface. */
        IN_MOUNTAIN("IN_MOUNTAIN"),

        /** Deep underground. */
        UNDERGROUND("UNDERGROUND"),

        /** Between the nether's lava sea and ceiling. */
        IN_NETHER("IN_NETHER");

        public static final KeyedEnumTranslator<Placement> TRANSLATOR = KeyedEnumTranslator.byKey(Placement::getKey, Placement.values());

        private final String key;

        @AsOf("3.4.0")
        Placement(String key) {
            this.key = key;
        }

        @AsOf("3.4.0")
        @Override
        public KeyedEnumTranslator<Placement> translator() {
            return TRANSLATOR;
        }

        /**
         * The vanilla name for this Placement
         * @return the vanilla key for this enum value
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public String getKey() {
            return this.key;
        }
    }

    /**
     * One way a ruined portal can generate.
     *
     * @param placement where the portal is placed relative to the terrain
     * @param airPocketProbability the chance, from 0 to 1, that the portal is surrounded by an air pocket
     * @param mossiness the fraction, from 0 to 1, of stone bricks turned mossy
     * @param overgrown whether leaves grow over the portal
     * @param vines whether vines hang from the portal
     * @param canBeCold whether the portal may use its cold variant in cold biomes
     * @param replaceWithBlackstone whether stone is replaced with blackstone
     * @param weight the weight this setup is picked with, above 0
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    record Setup(Placement placement, float airPocketProbability, float mossiness, boolean overgrown, boolean vines,
                 boolean canBeCold, boolean replaceWithBlackstone, float weight) {

        public Setup {
            Preconditions.checkArgument(airPocketProbability >= 0 && airPocketProbability <= 1, "airPocketProbability must be between 0 and 1, got %s", airPocketProbability);
            Preconditions.checkArgument(mossiness >= 0 && mossiness <= 1, "mossiness must be between 0 and 1, got %s", mossiness);
            Preconditions.checkArgument(weight > 0, "weight must be positive, got %s", weight);
        }

        /**
         * Creates a setup with a weight of 1.
         * @param placement where the portal is placed relative to the terrain
         * @param airPocketProbability the chance that the portal is surrounded by an air pocket
         * @param mossiness the fraction of stone bricks turned mossy
         * @param overgrown whether leaves grow over the portal
         * @param vines whether vines hang from the portal
         * @param canBeCold whether the portal may use its cold variant in cold biomes
         * @param replaceWithBlackstone whether stone is replaced with blackstone
         * @return a new setup
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public static Setup of(Placement placement, float airPocketProbability, float mossiness, boolean overgrown,
                               boolean vines, boolean canBeCold, boolean replaceWithBlackstone) {
            return new Setup(placement, airPocketProbability, mossiness, overgrown, vines, canBeCold, replaceWithBlackstone, 1.0F);
        }
    }

    /**
     * Builder for {@link RuinedPortalStructure}.
     * @since 3.4.0
     * @version 3.4.0
     * @author Jsinco
     */
    @AsOf("3.4.0")
    final class Builder {
        private @Nullable ResourceKey resourceKey;
        private StructureSettings settings;
        private List<Setup> setups = new ArrayList<>();

        public Builder(StructureSettings settings) {
            this.settings = settings;
        }

        public Builder(RuinedPortalStructure structure) {
            this.resourceKey = structure.resourceKey().orElse(null);
            this.settings = structure.settings();
            this.setups = new ArrayList<>(structure.setups());
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
         * Sets the setups a portal picks between.
         * @param setups the setups
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder setups(List<Setup> setups) {
            this.setups = new ArrayList<>(setups);
            return this;
        }

        // Friendly

        /**
         * Adds a setup.
         * @param setup the setup to add
         * @return this builder
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public Builder setup(Setup setup) {
            this.setups.add(setup);
            return this;
        }

        /**
         * Builds the ruined portal.
         * @return the ruined portal
         * @since 3.4.0
         */
        @AsOf("3.4.0")
        public RuinedPortalStructure build() {
            return of(resourceKey, settings, setups);
        }
    }
}
