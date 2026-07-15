package dev.wyck.level.dimension;

import dev.wyck.annotations.AsOf;
import dev.wyck.biome.entity.data.MonsterSettings;
import dev.wyck.environment.attribute.EnvironmentAttributeMap;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.DimensionRegistry;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * This implementation is here because it does not compile
 * against Minecraft internals as of right now.
 * Do not directly use this class, use {@link Dimension}.
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
    private final EnvironmentAttributeMap attributes;
    private final TimelineSet timelines;
    private final @Nullable WorldClock defaultClock;

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
            @Nullable WorldClock defaultClock
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
    public ResourceKey resourceKey() {
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
    public double coordinateScale() {
        return coordinateScale;
    }

    @Override
    public int minY() {
        return minY;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int logicalHeight() {
        return logicalHeight;
    }

    @Override
    public Infiniburn infiniburn() {
        return infiniburn;
    }

    @Override
    public float ambientLight() {
        return ambientLight;
    }

    @Override
    public MonsterSettings monsterSettings() {
        return monsterSettings;
    }

    @Override
    public Skybox skybox() {
        return skybox;
    }

    @Override
    public CardinalLightType cardinalLightType() {
        return cardinalLightType;
    }

    @Override
    public EnvironmentAttributeMap attributes() {
        return attributes;
    }

    @Override
    public TimelineSet timelines() {
        return timelines;
    }

    @Override
    public Optional<WorldClock> defaultClock() {
        return Optional.ofNullable(defaultClock);
    }

    @AsOf("2.4.0")
    public boolean isSimilar(Dimension other) {
        if (this == other) return true;
        return hasFixedTime == other.hasFixedTime()
                && hasSkyLight == other.hasSkyLight()
                && hasCeiling == other.hasCeiling()
                && Double.compare(coordinateScale, other.coordinateScale()) == 0
                && minY == other.minY()
                && height == other.height()
                && logicalHeight == other.logicalHeight()
                && Float.compare(ambientLight, other.ambientLight()) == 0
                && resourceKey.equals(other.resourceKey())
                && infiniburn.equals(other.infiniburn())
                && monsterSettings.equals(other.monsterSettings())
                && skybox == other.skybox()
                && cardinalLightType == other.cardinalLightType()
                && attributes.equals(other.attributes())
                && timelines.equals(other.timelines());
    }

    @Override
    public Key key() {
        return this.resourceKey;
    }

    @Override
    public Object toMinecraft() {
        return DimensionRegistry.registry().buildDelegate(this);
    }

    @Override
    public String toString() {
        return "DimensionImpl{" +
            "resourceKey=" + resourceKey +
            ", hasFixedTime=" + hasFixedTime +
            ", hasSkyLight=" + hasSkyLight +
            ", hasCeiling=" + hasCeiling +
            ", hasEnderDragonFight=" + hasEnderDragonFight +
            ", coordinateScale=" + coordinateScale +
            ", minY=" + minY +
            ", height=" + height +
            ", logicalHeight=" + logicalHeight +
            ", infiniburn=" + infiniburn +
            ", ambientLight=" + ambientLight +
            ", monsterSettings=" + monsterSettings +
            ", skybox=" + skybox +
            ", cardinalLightType=" + cardinalLightType +
            ", attributes=" + attributes +
            ", timelines=" + timelines +
            ", defaultClock=" + defaultClock +
            '}';
    }
}