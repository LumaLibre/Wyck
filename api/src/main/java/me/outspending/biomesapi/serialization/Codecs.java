package me.outspending.biomesapi.serialization;

import com.destroystokyo.paper.MaterialSetTag;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.worldgen.FluidType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.Vibration;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class Codecs {

    @AsOf("2.4.0")
    public static final Codec<UUID> UUID_CODEC = Codec.STRING.comapFlatMap(
        string -> {
            try {
                return DataResult.success(UUID.fromString(string));
            } catch (IllegalArgumentException e) {
                return DataResult.error(() -> "invalid uuid: " + string);
            }
        },
        UUID::toString
    );

    @AsOf("2.4.0")
    public static final Codec<Component> COMPONENT_CODEC = Codec.STRING.xmap(
        MiniMessage.miniMessage()::deserialize,
        MiniMessage.miniMessage()::serialize
    );

    @AsOf("2.4.0")
    public static final Codec<Material> MATERIAL_CODEC = Codec.stringResolver(
        material -> material.getKey().toString(),
        Material::matchMaterial
    );

    @AsOf("2.4.0")
    public static final Codec<ItemStack> ITEM_STACK_CODEC = Codec.BYTE_BUFFER.xmap(
        buffer -> ItemStack.deserializeBytes(toArray(buffer)),
        stack -> ByteBuffer.wrap(stack.serializeAsBytes())
    );

    @AsOf("2.4.0")
    public static final Codec<Location> LOCATION_CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.optionalFieldOf("world").forGetter(Codecs::worldName),
        Codec.DOUBLE.fieldOf("x").forGetter(Location::getX),
        Codec.DOUBLE.fieldOf("y").forGetter(Location::getY),
        Codec.DOUBLE.fieldOf("z").forGetter(Location::getZ),
        Codec.FLOAT.optionalFieldOf("yaw", 0.0f).forGetter(Location::getYaw),
        Codec.FLOAT.optionalFieldOf("pitch", 0.0f).forGetter(Location::getPitch)
    ).apply(instance, Codecs::makeLocation));

    @AsOf("2.4.0")
    public static final Codec<Vibration.Destination.BlockDestination> BLOCK_DESTINATION_CODEC =
        LOCATION_CODEC.xmap(Vibration.Destination.BlockDestination::new, Vibration.Destination.BlockDestination::getLocation);

    @AsOf("2.4.0")
    public static final Codec<Vibration.Destination.EntityDestination> ENTITY_DESTINATION_CODEC =
        UUID_CODEC.comapFlatMap(
            uuid -> {
                Entity entity = Bukkit.getEntity(uuid);
                return entity == null
                    ? DataResult.error(() -> "no entity with uuid " + uuid + " (unloaded or dead)")
                    : DataResult.success(new Vibration.Destination.EntityDestination(entity));
            },
            destination -> destination.getEntity().getUniqueId()
        );

    @AsOf("2.4.0")
    public static final Codec<Vibration.Destination> DESTINATION_CODEC = Codec.STRING
        .dispatch(
            "kind",
            destination -> destination instanceof Vibration.Destination.EntityDestination ? "entity" : "block",
            kind -> switch (kind) {
                case "entity" -> ENTITY_DESTINATION_CODEC.fieldOf("entity");
                case "block" -> BLOCK_DESTINATION_CODEC.fieldOf("location");
                default -> MapCodec.unit(() -> { throw new IllegalStateException("unknown destination kind: " + kind); });
            }
        );

    @AsOf("2.4.0")
    public static final Codec<EntityType> ENTITY_TYPE_CODEC = Codec.STRING.xmap(EntityType::valueOf, EntityType::name);

    @AsOf("2.4.0")
    public static final Codec<BlockFace> BLOCK_FACE_CODEC = Codec.STRING.xmap(BlockFace::valueOf, BlockFace::name);

    @AsOf("2.4.0")
    public static final Codec<BlockVector> BLOCK_VECTOR_CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("x").forGetter(Vector::getBlockX),
        Codec.INT.fieldOf("y").forGetter(Vector::getBlockY),
        Codec.INT.fieldOf("z").forGetter(Vector::getBlockZ)
    ).apply(instance, BlockVector::new));

    @AsOf("2.4.0")
    public static final Codec<Biome> BIOME_CODEC = Codec.stringResolver(
        biome -> biome.getKey().toString(),
        key -> RegistryAccess.registryAccess().getRegistry(RegistryKey.BIOME).get(NamespacedKey.fromString(key))
    );

    @AsOf("2.4.0")
    public static final Codec<Tag<Material>> INLINE_TAG_CODEC = Codec.list(MATERIAL_CODEC).xmap(
        materials -> new MaterialSetTag(NamespacedKey.randomKey(), materials),
        tag -> List.copyOf(tag.getValues())
    );

    @AsOf("2.4.0")
    public static final Codec<Tag<Material>> REGISTRY_TAG_CODEC = Codec.STRING.comapFlatMap(
        key -> {
            Tag<Material> tag = Bukkit.getTag("blocks", NamespacedKey.fromString(key), Material.class);
            return tag == null ? DataResult.error(() -> "unknown block tag: " + key) : DataResult.success(tag);
        },
        tag -> tag.key().toString()
    );

    @AsOf("2.4.0")
    public static final Codec<Tag<Material>> MATERIAL_TAG_CODEC = Codec.STRING.dispatch(
        "type",
        tag -> tag instanceof MaterialSetTag ? "inline" : "registry",
        type -> switch (type) {
            case "inline" -> INLINE_TAG_CODEC.fieldOf("values");
            case "registry" -> REGISTRY_TAG_CODEC.fieldOf("key");
            default -> throw new IllegalStateException("unknown tag type: " + type);
        }
    );

    @AsOf("2.4.0")
    public static final Codec<FluidType> FLUID_TYPE_CODEC = Codec.STRING.xmap(FluidType::valueOf, FluidType::name);

    @AsOf("2.4.0")
    public static final Codec<BlockData> BLOCK_DATA_CODEC = Codec.STRING.comapFlatMap(
        string -> {
            try {
                return DataResult.success(Bukkit.createBlockData(string));
            } catch (IllegalArgumentException e) {
                return DataResult.error(() -> "invalid block data '" + string + "': " + e.getMessage());
            }
        },
        BlockData::getAsString
    );

    @AsOf("2.4.0")
    public static final Codec<World.Environment> WORLD_ENVIRONMENT_CODEC = Codec.STRING.xmap(World.Environment::valueOf, World.Environment::name);

    private static byte[] toArray(ByteBuffer buffer) {
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return bytes;
    }

    private static Optional<String> worldName(Location location) {
        return location.isWorldLoaded() ? Optional.of(location.getWorld().getName()) : Optional.empty();
    }

    private static Location makeLocation(Optional<String> world, double x, double y, double z, float yaw, float pitch) {
        World resolved = world.map(Bukkit::getWorld).orElse(null);
        return new Location(resolved, x, y, z, yaw, pitch);
    }
}
