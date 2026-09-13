package dev.wyck.decode.worldgen.structure;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.structure.Structure;
import dev.wyck.worldgen.structure.placement.StructurePlacement;
import dev.wyck.worldgen.structure.StructureSet;
import dev.wyck.worldgen.structure.types.ComposedStructureSet;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class StructureSetDecoder implements Decodable<StructureSet, Object> {

    @Override
    public StructureSet decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return StructureSet.reference(Decoders.referenceKey(holder));
        }

        net.minecraft.world.level.levelgen.structure.StructureSet set = Decoders.value(minecraftObject);
        return ComposedStructureSet.of(
            null,
            set.structures().stream()
                .map(entry -> StructureSet.Entry.of(Structure.decode(entry.structure()), entry.weight()))
                .toList(),
            StructurePlacement.decode(set.placement())
        );
    }
}
