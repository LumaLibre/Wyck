package dev.wyck.wrapper.environment;

import io.papermc.paper.adventure.AdventureComponent;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record BedRuleImpl(@Override Rule canSleep, @Override Rule canSetSpawn, @Override boolean explodes, @Override Optional<Component> errorMessage) implements BedRule {
    @Override
    public Object toMinecraft() {
        return new net.minecraft.world.attribute.BedRule(
            this.canSleep.toNms(net.minecraft.world.attribute.BedRule.Rule.class),
            this.canSetSpawn.toNms(net.minecraft.world.attribute.BedRule.Rule.class),
            this.explodes,
            this.errorMessage.map(it -> new AdventureComponent(it).deepConverted())
        );
    }
}
