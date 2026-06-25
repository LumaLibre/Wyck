package me.outspending.biomesapi.wrapper.level.spawner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.factory.WireProvider;
import me.outspending.biomesapi.keys.KeyChains;
import me.outspending.biomesapi.serialization.ConstantRepresentable;
import me.outspending.biomesapi.wrapper.internal.NmsHandle;
import net.kyori.adventure.key.InvalidKeyException;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

@NullMarked
@AsOf("2.4.0")
@ApiStatus.Experimental
public interface LevelSpawner extends NmsHandle.Context<Object> {

    @SuppressWarnings("PatternValidation")
    Codec<LevelSpawner> CODEC = Codec.STRING.dispatch(
        "type",
        spawner -> spawner.keyedTick() != null ? "custom" : "vanilla",
        type -> switch (type) {
            case "vanilla" -> Kind.CODEC.fieldOf("kind").xmap(
                LevelSpawner::fromKind,
                spawner -> Objects.requireNonNull(spawner.kind(), "kind"));
            case "custom" -> Codec.STRING.comapFlatMap(
                string -> {
                    try {
                        KeyedSpawnTick tick = KeyChains.CUSTOM_LEVEL_SPAWNERS.get(Key.key(string));
                        return tick == null
                            ? DataResult.error(() -> "no custom level spawner registered under '" + string + "'")
                            : DataResult.success(custom(tick));
                    } catch (InvalidKeyException e) {
                        return DataResult.error(() -> "invalid key '" + string + "': " + e.getMessage());
                    }
                },
                spawner -> Objects.requireNonNull(spawner.keyedTick(), "keyedTick").key().asString()
            ).fieldOf("key");
            default -> throw new IllegalStateException("unknown level spawner: " + type);
        }
    );

    @ApiStatus.Internal
    WireProvider<Factory> WIRE = WireProvider.create("me.outspending.biomesapi.wrapper.level.spawner.LevelSpawnerFactoryImpl");

    @ApiStatus.Internal
    interface Factory {
        LevelSpawner phantom();
        LevelSpawner patrol();
        LevelSpawner cat();
        LevelSpawner villageSiege();
        LevelSpawner wanderingTrader();
        LevelSpawner custom(SpawnTick tick);
    }

    @AsOf("2.4.0")
    enum Kind implements ConstantRepresentable {
        PHANTOM("minecraft:phantom"),
        PATROL("minecraft:patrol"),
        CAT("minecraft:cat"),
        VILLAGE_SIEGE("minecraft:village_siege"),
        WANDERING_TRADER("minecraft:wandering_trader");

        public static final Codec<Kind> CODEC = ConstantRepresentable.codec(Kind.values());

        private final String key;

        Kind(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }
    }

    /**
     * @param savedDataStorage the world's {@code net.minecraft.world.level.storage.SavedDataStorage}
     * @return the underlying {@code net.minecraft.world.level.CustomSpawner}
     * @since 2.4.0
     */
    @Override
    @AsOf("2.4.0")
    Object toMinecraft(Object savedDataStorage);

    /**
     * The vanilla kind of this spawner, or null if this is a custom spawner.
     * @return the vanilla kind, or null
     * @since 2.4.0
     */
    @ApiStatus.Internal
    @AsOf("2.4.0")
    @Nullable Kind kind();

    /**
     * The keyed tick backing this spawner, or null if this is a vanilla spawner
     * or a custom spawner created with a plain {@link SpawnTick}.
     * @return the keyed tick, or null
     * @since 2.4.0
     */
    @ApiStatus.Internal
    @AsOf("2.4.0")
    @Nullable KeyedSpawnTick keyedTick();

    /**
     * Resolves a vanilla spawner from its kind.
     * @param kind the vanilla kind
     * @return the corresponding vanilla spawner
     * @since 2.4.0
     */
    @ApiStatus.Internal
    @AsOf("2.4.0")
    static LevelSpawner fromKind(Kind kind) {
        return switch (kind) {
            case PHANTOM -> phantom();
            case PATROL -> patrol();
            case CAT -> cat();
            case VILLAGE_SIEGE -> villageSiege();
            case WANDERING_TRADER -> wanderingTrader();
        };
    }

    @AsOf("2.4.0")
    static LevelSpawner phantom() {
        return WIRE.get().phantom();
    }

    @AsOf("2.4.0")
    static LevelSpawner patrol() {
        return WIRE.get().patrol();
    }

    @AsOf("2.4.0")
    static LevelSpawner cat() {
        return WIRE.get().cat();
    }

    @AsOf("2.4.0")
    static LevelSpawner villageSiege() {
        return WIRE.get().villageSiege();
    }

    @AsOf("2.4.0")
    static LevelSpawner wanderingTrader() {
        return WIRE.get().wanderingTrader();
    }

    @AsOf("2.4.0")
    static LevelSpawner custom(SpawnTick tick) {
        return WIRE.get().custom(tick);
    }

    @AsOf("2.4.0")
    static LevelSpawner custom(KeyedSpawnTick tick) {
        return WIRE.get().custom(tick);
    }
}