package dev.wyck.wrapper.worldgen.biome;

import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record MultiNoisePresetBiomeSourceImpl(
    @Override ResourceKey preset
) implements MultiNoisePresetBiomeSource {
    @Override
    public Object toMinecraft() {
        net.minecraft.resources.Identifier id = this.preset.identifier();
        net.minecraft.resources.ResourceKey<net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList> key =
            net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST, id);

        net.minecraft.core.Holder<net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList> holder = BootstrapSafeMinecraftRegistries.mappedRegistry(net.minecraft.core.registries.Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST)
            .getOrThrow(key);

        return net.minecraft.world.level.biome.MultiNoiseBiomeSource.createFromPreset(holder);
    }

    @Override
    public Key key() {
        return this.preset;
    }
}
