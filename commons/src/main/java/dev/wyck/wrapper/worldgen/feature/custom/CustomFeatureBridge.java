package dev.wyck.wrapper.worldgen.feature.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import dev.wyck.annotations.AsOf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;

import java.util.function.Supplier;

@NullMarked
@ApiStatus.Internal
public final class CustomFeatureBridge<C> extends Feature<CustomFeatureBridge.Holder<C>> {

    private final CustomFeature<C> delegate;

    public CustomFeatureBridge(CustomFeature<C> delegate, Supplier<C> configSupplier) {
        super(codec(configSupplier));
        this.delegate = delegate;
    }

    private static <C> Codec<Holder<C>> codec(Supplier<C> configSupplier) {
        return MapCodec.unit(() -> new Holder<>(configSupplier.get())).codec();
    }

    @Override
    @Contract("_ -> true")
    public boolean place(FeaturePlaceContext<Holder<C>> context) {
        Holder<C> holder = context.config();
        PlacementContext<C> wrapped = new PlacementContextImpl<>(context, holder.config());
        return this.delegate.place(wrapped);
    }

    public record Holder<C>(C config) implements FeatureConfiguration {
    }
}