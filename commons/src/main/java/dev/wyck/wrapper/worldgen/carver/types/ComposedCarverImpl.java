package dev.wyck.wrapper.worldgen.carver.types;

import dev.wyck.util.DatapackPromotion;
import dev.wyck.wrapper.worldgen.carver.CarverConfiguration;
import dev.wyck.wrapper.worldgen.carver.WorldCarverType;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record ComposedCarverImpl(
    @Override WorldCarverType type,
    @Override CarverConfiguration config
) implements ComposedCarver {

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Object toMinecraft() {
        if (DatapackPromotion.isReferenceMode()) {
            return DatapackPromotion.current().reference(this, net.minecraft.core.registries.Registries.CONFIGURED_CARVER);
        }

        net.minecraft.world.level.levelgen.carver.WorldCarver worldCarver = this.type.toNms();
        net.minecraft.world.level.levelgen.carver.CarverConfiguration nmsConfig = this.config.asHandle();

        net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?> configured =
            new net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<>(worldCarver, nmsConfig);


        if (DatapackPromotion.isCollectMode()) {
            DatapackPromotion.current().collectCarver(this, configured);
        }
        return net.minecraft.core.Holder.direct(configured);
    }

    @Override
    public Key key() {
        return this.type.resourceKey();
    }
}
