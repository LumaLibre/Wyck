package dev.wyck.wrapper.environment;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.RegistryOps;
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
            this.errorMessage.map(this::toComponentNms)
        );
    }

    public net.minecraft.network.chat.Component toComponentNms(Component component) {
        JsonElement json = GsonComponentSerializer.gson().serializeToTree(component);
        RegistryOps<JsonElement> ops = BootstrapSafeMinecraftRegistries.serialization().createSerializationContext(JsonOps.INSTANCE);
        return ComponentSerialization.CODEC.parse(ops, json)
            .getOrThrow(message -> new IllegalStateException("Component conversion failed: " + message));
    }
}
