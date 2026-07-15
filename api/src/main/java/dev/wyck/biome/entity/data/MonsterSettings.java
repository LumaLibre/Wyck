package dev.wyck.biome.entity.data;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.valueproviders.IntProvider;
import org.jspecify.annotations.NullMarked;

/**
 * Wrapper for a dimension's monster spawn settings.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public record MonsterSettings(IntProvider monsterSpawnLightTest, int monsterSpawnBlockLightLimit) {

    public static final int MIN_LIGHT = 0;
    public static final int MAX_LIGHT = 15;

    @AsOf("2.4.0")
    public MonsterSettings {
        Preconditions.checkNotNull(monsterSpawnLightTest, "monsterSpawnLightTest cannot be null");
        Preconditions.checkArgument(monsterSpawnLightTest.minInclusive() >= MIN_LIGHT, "monster_spawn_light_level too low: %s", monsterSpawnLightTest.minInclusive());
        Preconditions.checkArgument(monsterSpawnLightTest.maxInclusive() <= MAX_LIGHT, "monster_spawn_light_level too high: %s", monsterSpawnLightTest.maxInclusive());
        Preconditions.checkArgument(monsterSpawnBlockLightLimit >= MIN_LIGHT && monsterSpawnBlockLightLimit <= MAX_LIGHT, "monster_spawn_block_light_limit must be in [0, 15], was %s", monsterSpawnBlockLightLimit);
    }

    /**
     * Creates a new DimensionalMonsterSettings with the given spawn light level and block light limit.
     * @param spawnLightLevel the fixed spawn light level to use for monster spawning. Must be in the range [0, 15].
     * @param blockLightLimit the fixed block light limit to use for monster spawning. Must be in the range [0, 15].
     * @return a new DimensionalMonsterSettings with the given spawn light level and block light limit.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public static MonsterSettings of(int spawnLightLevel, int blockLightLimit) {
        return new MonsterSettings(IntProvider.constant(spawnLightLevel), blockLightLimit);
    }

    /**
     * Vanilla overworld defaults: uniform [0, 7] spawn light, block light limit 0.
     * @return a new DimensionalMonsterSettings with the vanilla overworld defaults.
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public static MonsterSettings overworld() {
        return new MonsterSettings(IntProvider.uniform(0, 7), 0);
    }
}