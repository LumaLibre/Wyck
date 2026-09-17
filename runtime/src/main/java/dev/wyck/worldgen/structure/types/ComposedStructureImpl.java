package dev.wyck.worldgen.structure.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import dev.wyck.keys.ResourceKey;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import dev.wyck.worldgen.structure.StructureSettings;
import net.kyori.adventure.key.Key;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@NullMarked
@ApiStatus.Internal
public record ComposedStructureImpl(
    @Override Optional<ResourceKey> resourceKey,
    @Override StructureSettings settings,
    Object minecraftStructure
) implements ComposedStructure {

    @Override
    public Object toMinecraft() {
        Structure structure = (Structure) this.minecraftStructure;
        Structure.StructureSettings desired = this.settings.asHandle();
        return Holder.direct(carries(structure, desired) ? structure : reseat(structure, desired));
    }

    @Override
    public ComposedStructure withSettings(StructureSettings settings) {
        return new ComposedStructureImpl(this.resourceKey, settings, this.minecraftStructure);
    }

    @Override
    public Object unwrapType() {
        return this.minecraftStructure;
    }

    @Override
    public Key key() {
        return this.resourceKey.orElseThrow();
    }

    @Override
    public ComposedStructure register() {
        StructureRegistration.register(this.resourceKey, this);
        return this;
    }

    /** Whether the structure already carries these settings, making the round trip pointless. */
    private static boolean carries(Structure structure, Structure.StructureSettings settings) {
        return structure.step() == settings.step()
            && structure.terrainAdaptation() == settings.terrainAdaptation()
            && structure.spawnOverrides().equals(settings.spawnOverrides())
            && sameBiomes(structure.biomes(), settings.biomes());
    }

    /**
     * Holder sets are compared by what they name rather than by identity: re-resolving the same tag
     * through a different registry instance yields an equal set, not the same object.
     */
    private static boolean sameBiomes(HolderSet<Biome> left, HolderSet<Biome> right) {
        Optional<net.minecraft.tags.TagKey<Biome>> leftTag = left.unwrapKey();
        Optional<net.minecraft.tags.TagKey<Biome>> rightTag = right.unwrapKey();
        if (leftTag.isPresent() || rightTag.isPresent()) {
            return leftTag.equals(rightTag);
        }
        List<Optional<net.minecraft.resources.ResourceKey<Biome>>> leftKeys = keys(left);
        List<Optional<net.minecraft.resources.ResourceKey<Biome>>> rightKeys = keys(right);
        return leftKeys.stream().allMatch(Optional::isPresent) && leftKeys.equals(rightKeys);
    }

    private static List<Optional<net.minecraft.resources.ResourceKey<Biome>>> keys(HolderSet<Biome> set) {
        return set.stream().map(Holder::unwrapKey).toList();
    }

    private static Structure reseat(Structure structure, Structure.StructureSettings settings) {
        RegistryOps<JsonElement> owning = BootstrapSafeMinecraftRegistries.vanilla().createSerializationContext(JsonOps.INSTANCE);
        RegistryOps<JsonElement> resolving = BootstrapSafeMinecraftRegistries.serialization().createSerializationContext(JsonOps.INSTANCE);

        JsonObject encoded = Structure.DIRECT_CODEC.encodeStart(owning, structure)
            .getOrThrow(message -> new IllegalStateException("Structure encode failed: " + message))
            .getAsJsonObject();

        JsonObject encodedSettings = Structure.StructureSettings.CODEC.codec().encodeStart(resolving, settings)
            .getOrThrow(message -> new IllegalStateException("Structure settings encode failed: " + message))
            .getAsJsonObject();

        for (Map.Entry<String, JsonElement> field : encodedSettings.entrySet()) {
            encoded.add(field.getKey(), field.getValue());
        }

        return Structure.DIRECT_CODEC.parse(resolving, encoded)
            .getOrThrow(message -> new IllegalStateException("Structure decode failed: " + message));
    }
}
