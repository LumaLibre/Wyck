package dev.wyck.wrapper.worldgen.stateproviders;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record RuleBasedStateProviderImpl(
    @Override Optional<BlockStateProvider> fallback,
    @Override List<RuleBasedStateProvider.Rule> rules
) implements RuleBasedStateProvider {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider(
            fallback.map(BlockStateProvider::<net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider>asHandle).orElse(null),
            rules.stream().map(RuleBasedStateProviderImpl::asHandle).toList()
        );
    }

    private static net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider.Rule asHandle(RuleBasedStateProvider.Rule rule) {
        return new net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider.Rule(
            rule.ifTrue().asHandle(),
            rule.then().asHandle()
        );
    }
}
