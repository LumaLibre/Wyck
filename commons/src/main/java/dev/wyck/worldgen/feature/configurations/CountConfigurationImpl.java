package dev.wyck.worldgen.feature.configurations;

import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record CountConfigurationImpl(@Override IntProvider count) implements CountConfiguration {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration(count.asHandle());
    }
}