package dev.wyck.worldgen.structure.pools.types;

import com.mojang.datafixers.util.Pair;
import dev.wyck.keys.ResourceKey;
import dev.wyck.registry.internal.RegistryId;
import dev.wyck.registry.internal.WyckRegistry;
import dev.wyck.util.Lazy;
import dev.wyck.worldgen.structure.pools.TemplatePool;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ComposedTemplatePoolImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override TemplatePool fallback,
    @Override List<Entry> elements
) implements ComposedTemplatePool {

    private static final Lazy<WyckRegistry> REGISTRY = WyckRegistry.lazy(RegistryId.TEMPLATE_POOL);

    @Override
    public Object toMinecraft() {
        List<Pair<StructurePoolElement, Integer>> templates = new ArrayList<>(this.elements.size());
        for (Entry entry : this.elements) {
            templates.add(Pair.of(entry.element().<StructurePoolElement>asHandle(), entry.weight()));
        }
        return Holder.direct(new StructureTemplatePool(this.fallback.<Holder<StructureTemplatePool>>asHandle(), templates));
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public ComposedTemplatePool register() {
        ResourceKey key = this.resourceKey.orElseThrow(() -> new NoSuchElementException("Cannot register a composed template pool without a resource key."));
        REGISTRY.get().register(key, this);
        return this;
    }
}
