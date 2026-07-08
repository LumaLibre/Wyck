package dev.wyck.wrapper.level;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@NullMarked
@ApiStatus.Internal
public record PresetBiomeSourceImpl(@Override ResourceKey preset) implements BiomeSource {

    @Override
    public Object toMinecraft() {
        Identifier id = (Identifier) this.preset.resourceLocation();
        net.minecraft.resources.ResourceKey<MultiNoiseBiomeSourceParameterList> key =
            net.minecraft.resources.ResourceKey.create(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, id);

        Holder<MultiNoiseBiomeSourceParameterList> holder = BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST)
            .getOrThrow(key);

        return MultiNoiseBiomeSource.createFromPreset(holder);
    }

    @Override
    public @Nullable ResourceKey fixedBiome() {
        return null;
    }

    @Override
    public @Nullable List<BiomeSource.MultiNoiseEntry> entries() {
        return null;
    }
}