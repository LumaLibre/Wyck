package dev.wyck.wrapper.worldgen.carver;

import dev.wyck.wrapper.worldgen.feature.configurations.ProbabilityFeatureConfigurationImpl;
import dev.wyck.wrapper.worldgen.valueproviders.FloatProvider;
import dev.wyck.wrapper.worldgen.valueproviders.HeightProvider;
import dev.wyck.wrapper.worldgen.valueproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public abstract class CarverConfigurationImpl extends ProbabilityFeatureConfigurationImpl implements CarverConfiguration {

    protected final HeightProvider y;
    protected final FloatProvider yScale;
    protected final VerticalAnchor lavaLevel;
    protected final CarverDebugSettings debugSettings;
    protected final Set<Material> replaceable;

    public CarverConfigurationImpl(float probability, HeightProvider y, FloatProvider yScale, VerticalAnchor lavaLevel, CarverDebugSettings debugSettings, Set<Material> replaceable) {
        super(probability);
        this.y = y;
        this.yScale = yScale;
        this.lavaLevel = lavaLevel;
        this.debugSettings = debugSettings;
        this.replaceable = replaceable;
    }

    @Override
    public HeightProvider y() {
        return this.y;
    }

    @Override
    public FloatProvider yScale() {
        return this.yScale;
    }

    @Override
    public VerticalAnchor lavaLevel() {
        return this.lavaLevel;
    }

    @Override
    public CarverDebugSettings debugSettings() {
        return this.debugSettings;
    }

    @Override
    public Set<Material> replaceable() {
        return this.replaceable;
    }
}
