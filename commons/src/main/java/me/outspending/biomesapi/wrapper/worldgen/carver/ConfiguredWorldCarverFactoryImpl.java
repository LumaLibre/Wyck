package me.outspending.biomesapi.wrapper.worldgen.carver;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class ConfiguredWorldCarverFactoryImpl implements ConfiguredWorldCarver.Factory {


    @Override
    public @NotNull Object toNms(@NotNull ConfiguredWorldCarver carver) {
        return switch (carver) {
            case ConfiguredWorldCarver.Reference reference -> resolveReference(reference);
            case ConfiguredWorldCarver.Custom custom -> buildCustom(custom);
        };
    }

    private Object resolveReference(@NotNull ConfiguredWorldCarver.Reference reference) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        RegistryAccess access = server.registryAccess();

        var registry = access.lookupOrThrow(Registries.CONFIGURED_CARVER);

        Identifier location =
            (Identifier) reference.key().resourceLocation();
        ResourceKey<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> resourceKey =
            ResourceKey.create(Registries.CONFIGURED_CARVER, location);

        return registry.getOrThrow(resourceKey);
    }

    private Object buildCustom(@NotNull ConfiguredWorldCarver.Custom custom) {
        net.minecraft.world.level.levelgen.carver.CarverConfiguration nmsConfig =
            (net.minecraft.world.level.levelgen.carver.CarverConfiguration) custom.config().toMinecraft();

        return switch (custom.type()) {
            case CAVE -> directHolder(WorldCarver.CAVE, nmsConfig);
            case NETHER_CAVE -> directHolder(WorldCarver.NETHER_CAVE, nmsConfig);
            case CANYON -> directHolder(WorldCarver.CANYON, nmsConfig);
        };
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    private net.minecraft.core.Holder<net.minecraft.world.level.levelgen.carver.@NotNull ConfiguredWorldCarver<?>> directHolder(
        net.minecraft.world.level.levelgen.carver.WorldCarver carver,
        net.minecraft.world.level.levelgen.carver.CarverConfiguration config) {

        net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver configured =
            new net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver(carver, config);
        return net.minecraft.core.Holder.direct(configured);
    }
}