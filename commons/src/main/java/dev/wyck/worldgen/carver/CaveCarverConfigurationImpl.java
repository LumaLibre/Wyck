package dev.wyck.worldgen.carver;

import dev.wyck.util.WorldgenConversions;
import dev.wyck.worldgen.valueproviders.FloatProvider;
import dev.wyck.worldgen.heightproviders.HeightProvider;
import dev.wyck.worldgen.heightproviders.VerticalAnchor;
import org.bukkit.Material;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

@NullMarked
@ApiStatus.Internal
public final class CaveCarverConfigurationImpl extends CarverConfigurationImpl implements CaveCarverConfiguration {

    private final FloatProvider horizontalRadiusMultiplier;
    private final FloatProvider verticalRadiusMultiplier;
    private final FloatProvider floorLevel;

    public CaveCarverConfigurationImpl(
        float probability,
        HeightProvider y,
        FloatProvider yScale,
        VerticalAnchor lavaLevel,
        CarverDebugSettings debugSettings,
        Set<Material> replaceable,
        FloatProvider horizontalRadiusMultiplier,
        FloatProvider verticalRadiusMultiplier,
        FloatProvider floorLevel
    ) {
        super(probability, y, yScale, lavaLevel, debugSettings, replaceable);
        this.horizontalRadiusMultiplier = horizontalRadiusMultiplier;
        this.verticalRadiusMultiplier = verticalRadiusMultiplier;
        this.floorLevel = floorLevel;
    }

    @Override
    public FloatProvider horizontalRadiusMultiplier() {
        return this.horizontalRadiusMultiplier;
    }

    @Override
    public FloatProvider verticalRadiusMultiplier() {
        return this.verticalRadiusMultiplier;
    }

    @Override
    public FloatProvider floorLevel() {
        return this.floorLevel;
    }

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration(
            probability,
            y.asHandle(),
            yScale.asHandle(),
            lavaLevel.asHandle(),
            debugSettings.asHandle(),
            WorldgenConversions.toBlockHolderSet(replaceable),
            horizontalRadiusMultiplier.asHandle(),
            verticalRadiusMultiplier.asHandle(),
            floorLevel.asHandle()
        );
    }
}
