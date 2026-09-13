package dev.wyck.decode.worldgen.structure;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.structure.Structure;
import dev.wyck.worldgen.structure.types.ComposedStructure;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Reads a keyed holder as a reference, and a structure value through {@link StructureTypeDecoders}. A
 * type that registry does not claim — one added by a mod or another plugin — is carried as-is inside a
 * {@link ComposedStructure}, so any structure reads even when Wyck does not model its type.
 */
@NullMarked
@ApiStatus.Internal
public final class StructureDecoder implements Decodable<Structure, Object> {

    private static final StructureTypeDecoders TYPES = new StructureTypeDecoders();

    @Override
    public Structure decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return Structure.reference(Decoders.referenceKey(holder));
        }

        net.minecraft.world.level.levelgen.structure.Structure structure = Decoders.value(minecraftObject);
        if (TYPES.handles(TYPES.typeOf(structure))) {
            return TYPES.decode(structure);
        }
        return ComposedStructure.of(null, StructureTypeDecoders.settings(structure), structure);
    }
}
