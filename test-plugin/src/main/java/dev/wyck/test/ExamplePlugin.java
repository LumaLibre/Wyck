package dev.wyck.test;

import dev.wyck.keys.ResourceKey;
import dev.wyck.model.level.LevelCreator;
import dev.wyck.wrapper.level.spawner.LevelSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        // Referencing an existing LevelSpawner
        LevelSpawner phantomSpawner = LevelSpawner.phantom();

        // Creating a custom LevelSpawner
        LevelSpawner customSpawner = LevelSpawner.custom((world, spawnEnemies) -> {
            for (Player player : world.getPlayers()) {
                player.getScheduler().run(this, _ -> {
                    EntityType type = spawnEnemies ? EntityType.ZOMBIE : EntityType.PIG;
                    world.spawnEntity(player.getLocation(), type);
                }, null);
            }
        });

        // Adding the spawners to a LevelCreator
        LevelCreator level = LevelCreator.builder()
            .levelKey(ResourceKey.of("test", "example"))
            .addSpawner(phantomSpawner)
            .addSpawner(customSpawner)
            .build();
    }
}
