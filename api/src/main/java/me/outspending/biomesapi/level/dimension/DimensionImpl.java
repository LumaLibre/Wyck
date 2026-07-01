package me.outspending.biomesapi.level.dimension;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.level.clock.WorldClock;
import me.outspending.biomesapi.wrapper.level.dimension.CardinalLightType;
import me.outspending.biomesapi.wrapper.level.dimension.Infiniburn;
import me.outspending.biomesapi.wrapper.level.dimension.Skybox;
import me.outspending.biomesapi.wrapper.level.dimension.TimelineSet;
import me.outspending.biomesapi.wrapper.entity.data.MonsterSettings;
import me.outspending.biomesapi.wrapper.environment.attribute.EnvironmentAttributeMap;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

/**
 * This class represents a custom dimension type implementation.
 *
 * @author Jsinco
 * @version 2.4.0
 * @since 2.4.0
 */
@NullMarked
@AsOf("2.4.0")
@ApiStatus.Internal
public final class DimensionImpl implements Dimension {

    private final ResourceKey resourceKey;

    private boolean hasFixedTime;
    private boolean hasSkyLight;
    private boolean hasCeiling;
    private boolean hasEnderDragonFight;
    private double coordinateScale;
    private int minY;
    private int height;
    private int logicalHeight;
    private Infiniburn infiniburn;
    private float ambientLight;
    private MonsterSettings monsterSettings;
    private Skybox skybox;
    private CardinalLightType cardinalLightType;
    private EnvironmentAttributeMap attributes;
    private TimelineSet timelines;
    private Optional<WorldClock> defaultClock;

    @AsOf("2.4.0")
    public DimensionImpl(
            ResourceKey resourceKey,
            boolean hasFixedTime,
            boolean hasSkyLight,
            boolean hasCeiling,
            boolean hasEnderDragonFight,
            double coordinateScale,
            int minY,
            int height,
            int logicalHeight,
            Infiniburn infiniburn,
            float ambientLight,
            MonsterSettings monsterSettings,
            Skybox skybox,
            CardinalLightType cardinalLightType,
            EnvironmentAttributeMap attributes,
            TimelineSet timelines,
            Optional<WorldClock> defaultClock
    ) {
        this.resourceKey = resourceKey;
        this.hasFixedTime = hasFixedTime;
        this.hasSkyLight = hasSkyLight;
        this.hasCeiling = hasCeiling;
        this.hasEnderDragonFight = hasEnderDragonFight;
        this.coordinateScale = coordinateScale;
        this.minY = minY;
        this.height = height;
        this.logicalHeight = logicalHeight;
        this.infiniburn = infiniburn;
        this.ambientLight = ambientLight;
        this.monsterSettings = monsterSettings;
        this.skybox = skybox;
        this.cardinalLightType = cardinalLightType;
        this.attributes = attributes;
        this.timelines = timelines;
        this.defaultClock = defaultClock;
    }

    @Override
    public ResourceKey getResourceKey() {
        return resourceKey;
    }

    @Override
    public boolean hasFixedTime() {
        return hasFixedTime;
    }

    @Override
    public boolean hasSkyLight() {
        return hasSkyLight;
    }

    @Override
    public boolean hasCeiling() {
        return hasCeiling;
    }

    @Override
    public boolean hasEnderDragonFight() {
        return hasEnderDragonFight;
    }

    @Override
    public double getCoordinateScale() {
        return coordinateScale;
    }

    @Override
    public int getMinY() {
        return minY;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getLogicalHeight() {
        return logicalHeight;
    }

    @Override
    public Infiniburn getInfiniburn() {
        return infiniburn;
    }

    @Override
    public float getAmbientLight() {
        return ambientLight;
    }

    @Override
    public MonsterSettings getMonsterSettings() {
        return monsterSettings;
    }

    @Override
    public Skybox getSkybox() {
        return skybox;
    }

    @Override
    public CardinalLightType getCardinalLightType() {
        return cardinalLightType;
    }

    @Override
    public EnvironmentAttributeMap getAttributes() {
        return attributes;
    }

    @Override
    public TimelineSet getTimelines() {
        return timelines;
    }

    @Override
    public Optional<WorldClock> getDefaultClock() {
        return defaultClock;
    }

    @Override
    public Dimension setFixedTime(boolean hasFixedTime) {
        this.hasFixedTime = hasFixedTime;
        return this;
    }

    @Override
    public Dimension setSkyLight(boolean hasSkyLight) {
        this.hasSkyLight = hasSkyLight;
        return this;
    }

    @Override
    public Dimension setCeiling(boolean hasCeiling) {
        this.hasCeiling = hasCeiling;
        return this;
    }

    @Override
    public Dimension setEnderDragonFight(boolean hasEnderDragonFight) {
        this.hasEnderDragonFight = hasEnderDragonFight;
        return this;
    }

    @Override
    public Dimension setCoordinateScale(double coordinateScale) {
        this.coordinateScale = coordinateScale;
        return this;
    }

    @Override
    public Dimension setMinY(int minY) {
        this.minY = minY;
        return this;
    }

    @Override
    public Dimension setHeight(int height) {
        this.height = height;
        return this;
    }

    @Override
    public Dimension setLogicalHeight(int logicalHeight) {
        this.logicalHeight = logicalHeight;
        return this;
    }

    @Override
    public Dimension setInfiniburn(Infiniburn infiniburn) {
        this.infiniburn = infiniburn;
        return this;
    }

    @Override
    public Dimension setAmbientLight(float ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    @Override
    public Dimension setMonsterSettings(MonsterSettings monsterSettings) {
        this.monsterSettings = monsterSettings;
        return this;
    }

    @Override
    public Dimension setSkybox(Skybox skybox) {
        this.skybox = skybox;
        return this;
    }

    @Override
    public Dimension setCardinalLightType(CardinalLightType cardinalLightType) {
        this.cardinalLightType = cardinalLightType;
        return this;
    }

    @Override
    public Dimension setAttributes(EnvironmentAttributeMap attributes) {
        this.attributes = attributes;
        return this;
    }

    @Override
    public Dimension setTimelines(TimelineSet timelines) {
        this.timelines = timelines;
        return this;
    }

    @Override
    public Dimension setDefaultClock(@Nullable WorldClock defaultClock) {
        this.defaultClock = Optional.ofNullable(defaultClock);
        return this;
    }

    @AsOf("2.4.0")
    public boolean isSimilar(Dimension other) {
        if (this == other) return true;
        return hasFixedTime == other.hasFixedTime()
                && hasSkyLight == other.hasSkyLight()
                && hasCeiling == other.hasCeiling()
                && Double.compare(coordinateScale, other.getCoordinateScale()) == 0
                && minY == other.getMinY()
                && height == other.getHeight()
                && logicalHeight == other.getLogicalHeight()
                && Float.compare(ambientLight, other.getAmbientLight()) == 0
                && resourceKey.equals(other.getResourceKey())
                && infiniburn.equals(other.getInfiniburn())
                && monsterSettings.equals(other.getMonsterSettings())
                && skybox == other.getSkybox()
                && cardinalLightType == other.getCardinalLightType()
                && attributes.equals(other.getAttributes())
                && timelines.equals(other.getTimelines());
    }

    @Override
    public Key key() {
        return this.resourceKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Dimension other)) return false;
        return isSimilar(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                resourceKey, hasFixedTime, hasSkyLight, hasCeiling, coordinateScale,
                minY, height, logicalHeight, infiniburn, ambientLight, monsterSettings,
                skybox, cardinalLightType, attributes, timelines
        );
    }

    @Override
    public String toString() {
        return "CustomDimensionImpl[" + resourceKey + "]";
    }
}