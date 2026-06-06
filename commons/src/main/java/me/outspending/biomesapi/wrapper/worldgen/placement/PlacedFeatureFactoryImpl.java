package me.outspending.biomesapi.wrapper.worldgen.placement;

import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.annotations.WireFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@WireFactory
@AsOf("2.3.0")
@ApiStatus.Internal
public final class PlacedFeatureFactoryImpl implements PlacedFeature.Factory {

    @Override
    public @NotNull Object toNms(@NotNull PlacedFeature feature) {
        return switch (feature) {
            case PlacedFeature.Reference reference -> resolveReference(reference);
            case PlacedFeature.Custom custom -> buildCustom(custom);
        };
    }

    private Object resolveReference(@NotNull PlacedFeature.Reference reference) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        RegistryAccess access = server.registryAccess();

        var registry = access.lookupOrThrow(Registries.PLACED_FEATURE);

        Identifier location =
                (Identifier) reference.key().resourceLocation();
        ResourceKey<net.minecraft.world.level.levelgen.placement.PlacedFeature> resourceKey =
                ResourceKey.create(Registries.PLACED_FEATURE, location);

        return registry.getOrThrow(resourceKey);
    }

    @SuppressWarnings("unchecked")
    private Object buildCustom(@NotNull PlacedFeature.Custom custom) {
        Holder<@NotNull ConfiguredFeature<?, ?>> featureHolder = (Holder<@NotNull ConfiguredFeature<?, ?>>) custom.feature().toMinecraft();

        List<net.minecraft.world.level.levelgen.placement.PlacementModifier> placement = new ArrayList<>(custom.placement().size());
        for (PlacementModifier modifier : custom.placement()) {
            placement.add((net.minecraft.world.level.levelgen.placement.PlacementModifier) modifier.toMinecraft());
        }

        net.minecraft.world.level.levelgen.placement.PlacedFeature placed =
                new net.minecraft.world.level.levelgen.placement.PlacedFeature(featureHolder, placement);
        return net.minecraft.core.Holder.direct(placed);
    }
}