package me.outspending.biomesapi.level.dimension;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.keys.ResourceKey;
import me.outspending.biomesapi.wrapper.level.dimension.CardinalLightType;
import me.outspending.biomesapi.wrapper.level.dimension.Infiniburn;
import me.outspending.biomesapi.wrapper.level.dimension.Skybox;
import me.outspending.biomesapi.wrapper.level.dimension.TimelineSet;
import me.outspending.biomesapi.wrapper.entity.data.MonsterSettings;
import me.outspending.biomesapi.wrapper.environment.attribute.WrappedEnvironmentAttributeMap;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

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

    private final boolean hasFixedTime;
    private final boolean hasSkyLight;
    private final boolean hasCeiling;
    private final boolean hasEnderDragonFight;
    private final double coordinateScale;
    private final int minY;
    private final int height;
    private final int logicalHeight;
    private final Infiniburn infiniburn;
    private final float ambientLight;
    private final MonsterSettings monsterSettings;
    private final Skybox skybox;
    private final CardinalLightType cardinalLightType;
    private final WrappedEnvironmentAttributeMap attributes;
    private final TimelineSet timelines;
    private final ResourceKey defaultClock;

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
            WrappedEnvironmentAttributeMap attributes,
            TimelineSet timelines,
            @Nullable ResourceKey defaultClock
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
    public WrappedEnvironmentAttributeMap getAttributes() {
        return attributes;
    }

    @Override
    public TimelineSet getTimelines() {
        return timelines;
    }

    @Override
    public @Nullable ResourceKey getDefaultClock() {
        return defaultClock;
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