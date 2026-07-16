package dev.wyck.worldgen.feature.configurations;

import dev.wyck.util.WeightedList;
import dev.wyck.worldgen.Rotation;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.ArrayList;
import java.util.List;

@NullMarked
@ApiStatus.Internal
public record TemplateFeatureConfigurationImpl(
    @Override WeightedList<TemplateFeatureConfiguration.TemplateEntry> templates
) implements TemplateFeatureConfiguration {
    @Override
    public Object toMinecraft() {
        net.minecraft.util.random.WeightedList.Builder<net.minecraft.world.level.levelgen.feature.configurations.TemplateFeatureConfiguration.TemplateEntry> builder =
            net.minecraft.util.random.WeightedList.builder();

        for (WeightedList.Weighted<TemplateFeatureConfiguration.TemplateEntry> entry : templates.unwrap()) {
            TemplateFeatureConfiguration.TemplateEntry wrapped = entry.value();

            net.minecraft.resources.Identifier template = wrapped.template().asHandle();

            List<net.minecraft.world.level.block.Rotation> rotations = new ArrayList<>(wrapped.rotations().size());
            for (Rotation rotation : wrapped.rotations()) {
                rotations.add(rotation.toNms(net.minecraft.world.level.block.Rotation.class));
            }

            net.minecraft.world.level.levelgen.feature.configurations.TemplateFeatureConfiguration.TemplateEntry mcEntry =
                new net.minecraft.world.level.levelgen.feature.configurations.TemplateFeatureConfiguration.TemplateEntry(template, rotations);

            builder.add(mcEntry, entry.weight());
        }

        return new net.minecraft.world.level.levelgen.feature.configurations.TemplateFeatureConfiguration(builder.build());
    }
}
