package dev.wyck.worldgen.structure.types;

import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.structure.placement.StructurePlacement;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ComposedStructureSetImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override List<Entry> structures,
    @Override StructurePlacement placement
) implements ComposedStructureSet {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.STRUCTURE_SET);

    @Override
    public Object toMinecraft() {
        List<StructureSet.StructureSelectionEntry> entries = new ArrayList<>(this.structures.size());
        for (Entry entry : this.structures) {
            entries.add(new StructureSet.StructureSelectionEntry(
                entry.structure().<Holder<Structure>>asHandle(),
                entry.weight()
            ));
        }

        return Holder.direct(new StructureSet(entries, this.placement.asHandle()));
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public ComposedStructureSet register() {
        ResourceKey key = this.resourceKey.orElseThrow(() -> new NoSuchElementException("Cannot register a composed structure set without a resource key."));
        REGISTRY.get().register(key, this);
        return this;
    }
}
