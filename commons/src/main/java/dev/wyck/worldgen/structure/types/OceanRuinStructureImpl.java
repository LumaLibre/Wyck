package dev.wyck.worldgen.structure.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.StructureSettings;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record OceanRuinStructureImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override StructureSettings settings,
    @Override BiomeTemperature biomeTemperature,
    @Override float largeProbability,
    @Override float clusterProbability
) implements OceanRuinStructure {

    @Override
    public Object toMinecraft() {
        return Holder.direct(new net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure(
            this.settings.asHandle(),
            this.biomeTemperature.toNms(net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure.Type.class),
            this.largeProbability,
            this.clusterProbability
        ));
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public OceanRuinStructure register() {
        StructureRegistration.register(this.resourceKey, this);
        return this;
    }
}
