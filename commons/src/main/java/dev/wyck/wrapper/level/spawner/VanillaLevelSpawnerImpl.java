package dev.wyck.wrapper.level.spawner;

import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.CatSpawner;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTraderSpawner;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.storage.SavedDataStorage;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record VanillaLevelSpawnerImpl(Kind kind) implements LevelSpawner {

    public enum Kind {
        PHANTOM,
        PATROL,
        CAT,
        VILLAGE_SIEGE,
        WANDERING_TRADER
    }

    @Override
    public Object toMinecraft(Object savedDataStorage) {
        return switch (kind) {
            case PHANTOM -> new PhantomSpawner();
            case PATROL -> new PatrolSpawner();
            case CAT -> new CatSpawner();
            case VILLAGE_SIEGE -> new VillageSiege();
            case WANDERING_TRADER -> new WanderingTraderSpawner((SavedDataStorage) savedDataStorage);
        };
    }
}