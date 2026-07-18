package dev.wyck.test.bootstrap;

import dev.wyck.util.BukkitBootstrapUtil;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MinecraftBootstrap.class)
class BukkitBootstrapUtilTest {

    @Test
    void ensureBlockDataIsCreated() {
        BlockData data = BukkitBootstrapUtil.util().createBlockData(Material.STONE);
        assertNotNull(data, "BlockData should be created");
        assertEquals(Material.STONE, data.getMaterial(), "BlockData should have the correct material");
    }
}
