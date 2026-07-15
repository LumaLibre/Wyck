package dev.wyck.worldgen.ruletest;

import dev.wyck.keys.ResourceKey;
import dev.wyck.worldgen.ruletest.TagMatchTest;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record TagMatchTestImpl(@Override ResourceKey tag) implements TagMatchTest {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest(
            net.minecraft.tags.TagKey.create(
                net.minecraft.core.registries.Registries.BLOCK,
                tag.asHandle()
            )
        );
    }
}
