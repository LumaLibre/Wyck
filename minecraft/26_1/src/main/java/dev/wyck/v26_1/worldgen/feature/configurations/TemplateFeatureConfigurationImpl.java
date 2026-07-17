package dev.wyck.v26_1.worldgen.feature.configurations;

import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.feature.configurations.TemplateFeatureConfiguration;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TemplateFeatureConfigurationImpl(
    @Override WeightedList<TemplateFeatureConfiguration.TemplateEntry> templates
) implements TemplateFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        throw new UnsupportedOperationException("Doesn't exist in this version of Minecraft");
    }
}
