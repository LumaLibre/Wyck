package dev.wyck.model.level.dimension;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.level.dimension.DimensionRegistry;
import dev.wyck.wrapper.environment.attribute.FriendlyColorSupplier;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributeSupplier;
import dev.wyck.wrapper.level.clock.WorldClock;
import dev.wyck.wrapper.level.dimension.CardinalLightType;
import dev.wyck.wrapper.level.dimension.Infiniburn;
import dev.wyck.wrapper.level.dimension.Skybox;
import dev.wyck.wrapper.level.dimension.TimelineSet;
import dev.wyck.wrapper.entity.data.MonsterSettings;
import dev.wyck.wrapper.environment.attribute.EnvironmentAttributeMap;
import net.kyori.adventure.key.Keyed;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;


/**
 * Wraps a minecraft dimension type.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public interface Dimension extends Keyed {

    /**
     * The key of this dimension type.
     * @return the key of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    ResourceKey getResourceKey();

    /**
     * Whether this dimension type has a fixed time.
     * @return whether this dimension type has a fixed time.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean hasFixedTime();

    /**
     * Whether this dimension type has sky light.
     * @return whether this dimension type has sky light.
     */
    @AsOf("2.4.0")
    boolean hasSkyLight();

    /**
     * Whether this dimension type has a ceiling.
     * @return whether this dimension type has a ceiling.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean hasCeiling();

    /**
     * Whether this dimension type has an ender dragon fight.
     * @return whether this dimension type has an ender dragon fight.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    boolean hasEnderDragonFight();

    /**
     * The coordinate scale of this dimension type.
     * @return the coordinate scale of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    double getCoordinateScale();

    /**
     * The minimum Y of this dimension type.
     * @return the minimum Y of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int getMinY();

    /**
     * The height of this dimension type.
     * @return the height of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int getHeight();

    /**
     * The logical height of this dimension type.
     * @return the logical height of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    int getLogicalHeight();

    /**
     * A tag of blocks that will burn infinitely in this dimension type.
     * @return the infiniburn of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Infiniburn getInfiniburn();

    /**
     * The ambient light of this dimension type.
     * @return the ambient light level of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    float getAmbientLight();

    /**
     * The monster settings of this dimension type.
     * @return the monster settings of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    MonsterSettings getMonsterSettings();

    /**
     * The skybox of this dimension type.
     * @return the skybox of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Skybox getSkybox();

    /**
     * The cardinal light type of this dimension type.
     * @return the cardinal light type of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    CardinalLightType getCardinalLightType();

    /**
     * The attributes of this dimension type.
     * @return the attributes of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    EnvironmentAttributeMap getAttributes();

    /**
     * The timelines of this dimension type.
     * @return the timelines of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    TimelineSet getTimelines();

    /**
     * The default clock of this dimension type.
     * @return the default clock of this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Optional<WorldClock> getDefaultClock();

    /**
     * Sets the fixed time of this dimension type.
     * @param fixedTime the fixed time of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setFixedTime(boolean fixedTime);

    /**
     * Sets whether this dimension type has sky light.
     * @param hasSkyLight whether this dimension type has sky light.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setSkyLight(boolean hasSkyLight);

    /**
     * Sets whether this dimension type has a ceiling.
     * @param hasCeiling whether this dimension type has a ceiling.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setCeiling(boolean hasCeiling);

    /**
     * Sets whether this dimension type has an ender dragon fight.
     * @param hasEnderDragonFight whether this dimension type has an ender dragon fight.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setEnderDragonFight(boolean hasEnderDragonFight);

    /**
     * Sets the coordinate scale of this dimension type.
     * @param coordinateScale the coordinate scale of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setCoordinateScale(double coordinateScale);

    /**
     * Sets the minimum Y of this dimension type.
     * @param minY the minimum Y of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setMinY(int minY);

    /**
     * Sets the height of this dimension type.
     * @param height the height of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setHeight(int height);

    /**
     * Sets the logical height of this dimension type.
     * @param logicalHeight the logical height of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setLogicalHeight(int logicalHeight);

    /**
     * Sets the infiniburn of this dimension type.
     * @param infiniburn the infiniburn of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setInfiniburn(Infiniburn infiniburn);

    /**
     * Sets the ambient light of this dimension type.
     * @param ambientLight the ambient light level of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setAmbientLight(float ambientLight);

    /**
     * Sets the monster settings of this dimension type.
     * @param monsterSettings the monster settings of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setMonsterSettings(MonsterSettings monsterSettings);

    /**
     * Sets the skybox of this dimension type.
     * @param skybox the skybox of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setSkybox(Skybox skybox);

    /**
     * Sets the cardinal light type of this dimension type.
     * @param cardinalLightType the cardinal light type of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setCardinalLightType(CardinalLightType cardinalLightType);

    /**
     * Sets the attributes of this dimension type.
     * @param attributes the attributes of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setAttributes(EnvironmentAttributeMap attributes);

    /**
     * Sets the timelines of this dimension type.
     * @param timelines the timelines of this dimension type.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setTimelines(TimelineSet timelines);

    /**
     * Sets the default clock of this dimension type.
     * @param defaultClock the default clock of this dimension type, or null to clear it.
     * @return this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    Dimension setDefaultClock(@Nullable WorldClock defaultClock);

    /**
     * Checks if this dimension type is similar to another.
     * @param other the other dimension type to compare to
     * @return whether this dimension type is similar to the other
     * @since 2.4.0
     */
    boolean isSimilar(Dimension other);

    /**
     * Registers this dimension type to the dimension registry.
     * @return this dimension type
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default Dimension register() {
        DimensionRegistry.registry().register(this);
        return this;
    }

    /**
     * Modifies this dimension type in the dimension registry.
     * @return this dimension type
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default Dimension modify() {
        DimensionRegistry.registry().modify(this);
        return this;
    }

    /**
     * Creates a new {@link Builder} with the same properties as this dimension type.
     * @return a new {@link Builder} with the same properties as this dimension type.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    default Builder toBuilder() {
        return new Builder(this);
    }

    /**
     * Creates a new {@link Builder} with the given resource key.
     * @return a new {@link Builder} with the given resource key.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a new {@link Builder} with the given resource key.
     * @param resourceKey the resource key to use
     * @return a new {@link Builder} with the given resource key.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    static Builder builder(ResourceKey resourceKey) {
        Preconditions.checkNotNull(resourceKey, "Resource key cannot be null.");
        return new Builder().resourceKey(resourceKey);
    }
    
    
    /**
     * Builder for {@link Dimension}. Defaults approximate the vanilla overworld
     * so a minimally-configured build is valid.
     *
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    class Builder {

        private @Nullable ResourceKey resourceKey = null;

        private boolean hasFixedTime = false;
        private boolean hasSkyLight = true;
        private boolean hasCeiling = false;
        private boolean hasEnderDragonFight = false;
        private double coordinateScale = 1.0D;
        private int minY = -64;
        private int height = 384;
        private int logicalHeight = 384;
        private Infiniburn infiniburn = Infiniburn.OVERWORLD;
        private float ambientLight = 0.0F;
        private MonsterSettings monsterSettings = MonsterSettings.overworld();
        private Skybox skybox = Skybox.OVERWORLD;
        private CardinalLightType cardinalLightType = CardinalLightType.DEFAULT;
        private EnvironmentAttributeMap attributes = EnvironmentAttributeMap.EMPTY;
        private TimelineSet timelines = TimelineSet.EMPTY;
        private @Nullable WorldClock defaultClock = null;

        @AsOf("2.4.0")
        public Builder() {}

        @AsOf("2.4.0")
        public Builder(Dimension dimension) {
            this.resourceKey = dimension.getResourceKey();
            this.hasFixedTime = dimension.hasFixedTime();
            this.hasSkyLight = dimension.hasSkyLight();
            this.hasCeiling = dimension.hasCeiling();
            this.coordinateScale = dimension.getCoordinateScale();
            this.minY = dimension.getMinY();
            this.height = dimension.getHeight();
            this.logicalHeight = dimension.getLogicalHeight();
            this.infiniburn = dimension.getInfiniburn();
            this.ambientLight = dimension.getAmbientLight();
            this.monsterSettings = dimension.getMonsterSettings();
            this.skybox = dimension.getSkybox();
            this.cardinalLightType = dimension.getCardinalLightType();
            this.attributes = dimension.getAttributes();
            this.timelines = dimension.getTimelines();
            this.defaultClock = dimension.getDefaultClock().orElse(null);
        }

        @AsOf("2.4.0")
        public Builder resourceKey(ResourceKey resourceKey) {
            this.resourceKey = resourceKey;
            return this;
        }

        @AsOf("2.4.0")
        public Builder hasFixedTime(boolean hasFixedTime) {
            this.hasFixedTime = hasFixedTime;
            return this;
        }

        @AsOf("2.4.0")
        public Builder hasSkyLight(boolean hasSkyLight) {
            this.hasSkyLight = hasSkyLight;
            return this;
        }

        @AsOf("2.4.0")
        public Builder hasCeiling(boolean hasCeiling) {
            this.hasCeiling = hasCeiling;
            return this;
        }

        @AsOf("2.4.0")
        public Builder hasEnderDragonFight(boolean hasEnderDragonFight) {
            this.hasEnderDragonFight = hasEnderDragonFight;
            return this;
        }

        @AsOf("2.4.0")
        public Builder coordinateScale(double coordinateScale) {
            this.coordinateScale = coordinateScale;
            return this;
        }

        @AsOf("2.4.0")
        public Builder minY(int minY) {
            this.minY = minY;
            return this;
        }

        @AsOf("2.4.0")
        public Builder height(int height) {
            this.height = height;
            return this;
        }

        @AsOf("2.4.0")
        public Builder logicalHeight(int logicalHeight) {
            this.logicalHeight = logicalHeight;
            return this;
        }

        @AsOf("2.4.0")
        public Builder infiniburn(Infiniburn infiniburn) {
            this.infiniburn = infiniburn;
            return this;
        }

        @AsOf("2.4.0")
        public Builder ambientLight(float ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        @AsOf("2.4.0")
        public Builder monsterSettings(MonsterSettings monsterSettings) {
            this.monsterSettings = monsterSettings;
            return this;
        }

        @AsOf("2.4.0")
        public Builder skybox(Skybox skybox) {
            this.skybox = skybox;
            return this;
        }

        @AsOf("2.4.0")
        public Builder cardinalLightType(CardinalLightType cardinalLightType) {
            this.cardinalLightType = cardinalLightType;
            return this;
        }

        @AsOf("2.4.0")
        public Builder attributes(EnvironmentAttributeMap attributes) {
            this.attributes = attributes;
            return this;
        }

        @AsOf("2.4.0")
        public <V> Builder attribute(EnvironmentAttributeSupplier<V> supplier, V value) {
            this.attributes = this.attributes.with(supplier, value);
            return this;
        }

        @AsOf("2.4.0")
        public Builder attribute(FriendlyColorSupplier supplier, String hex) {
            this.attributes = this.attributes.with(supplier, hex);
            return this;
        }



        @AsOf("2.4.0")
        public Builder timelines(TimelineSet timelines) {
            this.timelines = timelines;
            return this;
        }

        @AsOf("2.4.0")
        public Builder defaultClock(@Nullable WorldClock defaultClock) {
            this.defaultClock = defaultClock;
            return this;
        }

        @AsOf("2.4.0")
        public Dimension build() {
            Preconditions.checkArgument(resourceKey != null, "Resource key must be set");
            Preconditions.checkArgument(height >= 16, "height has to be at least 16");
            Preconditions.checkArgument(minY + height <= 2032, "min_y + height cannot be higher than 2032");
            Preconditions.checkArgument(logicalHeight <= height, "logical_height cannot be higher than height");
            Preconditions.checkArgument(height % 16 == 0, "height has to be a multiple of 16");
            Preconditions.checkArgument(minY % 16 == 0, "min_y has to be a multiple of 16");

            return new DimensionImpl(
                resourceKey,
                hasFixedTime,
                hasSkyLight,
                hasCeiling,
                hasEnderDragonFight,
                coordinateScale,
                minY,
                height,
                logicalHeight,
                infiniburn,
                ambientLight,
                monsterSettings,
                skybox,
                cardinalLightType,
                attributes,
                timelines,
                defaultClock
            );
        }

        @AsOf("2.4.0")
        public Dimension register() {
            return this.build().register();
        }
    }
}