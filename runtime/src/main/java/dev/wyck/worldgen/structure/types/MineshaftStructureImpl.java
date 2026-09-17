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
public record MineshaftStructureImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override StructureSettings settings,
    @Override Type mineshaftType
) implements MineshaftStructure {

    @Override
    public Object toMinecraft() {
        return Holder.direct(new net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure(
            this.settings.asHandle(),
            this.mineshaftType.toNms(net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure.Type.class)
        ));
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public MineshaftStructure register() {
        StructureRegistration.register(this.resourceKey, this);
        return this;
    }
}
