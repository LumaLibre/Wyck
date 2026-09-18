package dev.wyck.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

// TODO: remove me
@NullMarked
@ApiStatus.Internal
public final class MinecraftEntityTypes {

    public static net.minecraft.world.entity.EntityType<?> toMinecraft(EntityType bukkitType) {
        NamespacedKey key = bukkitType.getKey();
        Identifier identifier = Identifier.fromNamespaceAndPath(key.getNamespace(), key.getKey());
        if (!BuiltInRegistries.ENTITY_TYPE.containsKey(identifier)) {
            throw new IllegalArgumentException("No Minecraft entity type is registered for " + key);
        }
        return BuiltInRegistries.ENTITY_TYPE.getValue(identifier);
    }
}
