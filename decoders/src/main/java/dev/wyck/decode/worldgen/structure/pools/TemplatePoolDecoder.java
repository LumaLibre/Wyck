package dev.wyck.decode.worldgen.structure.pools;

import dev.wyck.decode.Decoders;
import dev.wyck.worldgen.structure.pools.PoolElement;
import dev.wyck.worldgen.structure.pools.TemplatePool;
import dev.wyck.worldgen.structure.pools.types.ComposedTemplatePool;
import dev.wyck.wrapper.decode.Decodable;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class TemplatePoolDecoder implements Decodable<TemplatePool, Object> {

    @Override
    public TemplatePool decode(Object minecraftObject) {
        if (minecraftObject instanceof Holder<?> holder && holder.unwrapKey().isPresent()) {
            return TemplatePool.reference(Decoders.referenceKey(holder));
        }

        StructureTemplatePool pool = Decoders.value(minecraftObject);
        return ComposedTemplatePool.of(
            null,
            TemplatePool.decode(pool.getFallback()),
            pool.getTemplates().stream()
                .map(pair -> ComposedTemplatePool.Entry.of(PoolElement.decode(pair.getFirst()), pair.getSecond()))
                .toList()
        );
    }
}
