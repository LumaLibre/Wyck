package dev.wyck.wrapper.worldgen.carver.types;

import dev.wyck.util.DatapackPromotion;
import dev.wyck.wrapper.worldgen.carver.custom.CustomCarver;
import dev.wyck.wrapper.worldgen.carver.custom.CustomCarverBridge;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

// TODO: fixup imports
@NullMarked
@ApiStatus.Internal
public record ComposedCustomCarverImpl<C>(
    @Override CustomCarver<C> carver,
    @Override C config
) implements ComposedCustomCarver<C> {

    @Override
    @SuppressWarnings("unchecked")
    public Object toMinecraft() {
        Identifier carverId = this.carver.key().identifier();
        WorldCarver<?> carver = BuiltInRegistries.CARVER.getValue(carverId);

        if (carver == null) {
            throw new IllegalArgumentException("Custom carver not registered: " + carverId + " (did you call #register()?)");
        }

        CustomCarverBridge<Object> bridge = (CustomCarverBridge<Object>) carver;
        net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?> configured =
            new net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<>(bridge, bridge.config(this.config));

        if (DatapackPromotion.isCollectMode()) {
            DatapackPromotion.current().collectCarver(this, configured);
        }

        return Holder.direct(configured);
    }

    @Override
    public Key key() {
        return this.carver.key();
    }
}
