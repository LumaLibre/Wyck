package dev.wyck.decode.worldgen.structure.pools;

import dev.wyck.decode.Decoders;
import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.structure.pools.TemplatePool;
import dev.wyck.worldgen.structure.pools.alias.PoolAliasBinding;
import dev.wyck.wrapper.decode.DecoderRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.DirectPoolAlias;
import net.minecraft.world.level.levelgen.structure.pools.alias.RandomGroupPoolAlias;
import net.minecraft.world.level.levelgen.structure.pools.alias.RandomPoolAlias;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public final class PoolAliasBindingDecoders extends DecoderRegistry<PoolAliasBinding, net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding> {

    public PoolAliasBindingDecoders() {
        register("direct", binding -> {
            DirectPoolAlias direct = (DirectPoolAlias) binding;
            return PoolAliasBinding.direct(pool(direct.alias()), pool(direct.target()));
        });
        register("random", binding -> {
            RandomPoolAlias random = (RandomPoolAlias) binding;
            return PoolAliasBinding.random(pool(random.alias()), Decoders.weighted(random.targets(), PoolAliasBindingDecoders::pool));
        });
        register("random_group", binding -> PoolAliasBinding.randomGroup(Decoders.weighted(
            ((RandomGroupPoolAlias) binding).groups(),
            group -> group.stream().map(PoolAliasBinding::decode).toList()
        )));
    }

    @Override
    protected ResourceKey discriminate(net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding minecraftObject) {
        return Decoders.registryKey(BuiltInRegistries.POOL_ALIAS_BINDING_TYPE, minecraftObject.codec());
    }

    private static TemplatePool pool(net.minecraft.resources.ResourceKey<StructureTemplatePool> key) {
        return TemplatePool.reference(Decoders.key(key.identifier()));
    }
}
