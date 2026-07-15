package dev.wyck.worldgen.carver.types;

import dev.wyck.util.DatapackPromotion;
import dev.wyck.worldgen.carver.custom.CustomCarver;
import dev.wyck.worldgen.carver.custom.CustomCarverBridge;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CustomComposedCarverImpl<C>(
    @Override CustomCarver<C> carver,
    @Override C config
) implements CustomComposedCarver<C> {

    @Override
    @SuppressWarnings("unchecked")
    public Object toMinecraft() {
        net.minecraft.resources.Identifier carverId = this.carver.key().identifier();
        net.minecraft.world.level.levelgen.carver.WorldCarver<?> carver = net.minecraft.core.registries.BuiltInRegistries.CARVER.getValue(carverId);

        if (carver == null) {
            throw new IllegalArgumentException("Custom carver not registered: " + carverId + " (did you call #register()?)");
        }

        CustomCarverBridge<Object> bridge = (CustomCarverBridge<@NonNull Object>) carver;
        net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?> configured =
            new net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<>(bridge, bridge.config(this.config));

        if (DatapackPromotion.isCollectMode()) {
            DatapackPromotion.current().collectCarver(this, configured);
        }

        return net.minecraft.core.Holder.direct(configured);
    }

    @Override
    public Key key() {
        return this.carver.key();
    }
}
