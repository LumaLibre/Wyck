package dev.wyck.test;

import dev.wyck.util.BukkitBootstrapUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
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

    @Test
    @SuppressWarnings("removal")
    void ensureSoundCanBeUsedDuringBootstrap() {
        Sound sound = Sound.BLOCK_NOTE_BLOCK_PLING;
        assertNotNull(sound, "Sound should be created");
        assertEquals("minecraft:block.note_block.pling", sound.key().asString(), "Sound should have the correct key");
    }
}
