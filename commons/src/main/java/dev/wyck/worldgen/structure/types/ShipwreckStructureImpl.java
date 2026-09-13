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
public record ShipwreckStructureImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override StructureSettings settings,
    @Override boolean beached
) implements ShipwreckStructure {

    @Override
    public Object toMinecraft() {
        return Holder.direct(new net.minecraft.world.level.levelgen.structure.structures.ShipwreckStructure(
            this.settings.asHandle(), this.beached
        ));
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public ShipwreckStructure register() {
        StructureRegistration.register(this.resourceKey, this);
        return this;
    }
}
