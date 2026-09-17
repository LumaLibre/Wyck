package dev.wyck.worldgen.structure.templatesystem.processor;

import dev.wyck.wrapper.Wrapper;

import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@ApiStatus.Internal
public record RuleProcessorImpl(
    @Override List<ProcessorRule> rules
) implements RuleProcessor {

    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor(
            this.rules.stream().map(Wrapper::<net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule>asHandle).toList()
        );
    }
}
