package dev.wyck.worldgen.structure.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.StructureSettings;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record RuinedPortalStructureImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override StructureSettings settings,
    @Override List<Setup> setups
) implements RuinedPortalStructure {

    @Override
    public Object toMinecraft() {
        return Holder.direct(new net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure(
            this.settings.asHandle(),
            this.setups.stream().map(RuinedPortalStructureImpl::setup).toList()
        ));
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public RuinedPortalStructure register() {
        StructureRegistration.register(this.resourceKey, this);
        return this;
    }

    private static net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure.Setup setup(Setup setup) {
        return new net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure.Setup(
            setup.placement().toNms(RuinedPortalPiece.VerticalPlacement.class),
            setup.airPocketProbability(),
            setup.mossiness(),
            setup.overgrown(),
            setup.vines(),
            setup.canBeCold(),
            setup.replaceWithBlackstone(),
            setup.weight()
        );
    }
}
