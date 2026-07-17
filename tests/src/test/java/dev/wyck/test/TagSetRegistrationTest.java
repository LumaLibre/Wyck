package dev.wyck.test;

import dev.wyck.keys.ResourceKey;
import dev.wyck.tags.TagSet;
import dev.wyck.util.BootstrapSafeMinecraftRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Material;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@NullMarked
@ExtendWith(MinecraftBootstrap.class)
class TagSetRegistrationTest {

    private static Registry<Block> blocks() {
        return BootstrapSafeMinecraftRegistries.mappedRegistry(Registries.BLOCK);
    }

    private static TagKey<Block> nmsTag(String id) {
        return TagKey.create(Registries.BLOCK, Identifier.parse(id));
    }

    private static HolderSet.Named<Block> boundTag(String id) {
        TagKey<Block> key = nmsTag(id);
        return blocks().getTags()
                .filter(named -> named.key().equals(key))
                .findFirst()
                .orElseThrow(() -> new AssertionError("no tag bound for " + id + "; bound tags: "
                        + blocks().getTags().map(t -> t.key().location().toString()).toList()));
    }

    private static Set<Block> contents(String id) {
        return boundTag(id).stream().map(Holder::value).collect(Collectors.toSet());
    }

    private static Set<TagKey<Block>> boundTagKeys() {
        return blocks().getTags().map(HolderSet.Named::key).collect(Collectors.toSet());
    }

    @Test
    void registerBindsTheTagInTheBlockRegistry() {
        TagSet.ofBlocks(ResourceKey.of("wyck:test_tag"), Material.STONE, Material.DIRT).register();

        assertEquals(Set.of(Blocks.STONE, Blocks.DIRT), contents("wyck:test_tag"));
    }

    @Test
    void registerLeavesVanillaTagsBound() {
        Set<TagKey<Block>> before = boundTagKeys();
        assertTrue(before.contains(nmsTag("minecraft:mineable/pickaxe")),
                "expected vanilla to have bound its bootstrap tags; found " + before);

        TagSet.ofBlocks(ResourceKey.of("wyck:vanilla_guard_tag"), Material.STONE).register();

        Set<TagKey<Block>> dropped = new HashSet<>(before);
        dropped.removeAll(boundTagKeys());
        assertEquals(Set.of(), dropped, "register() unbound tags that were already there");
    }

    @Test
    void registerLeavesEarlierTagsBound() {
        TagSet.ofBlocks(ResourceKey.of("wyck:first_tag"), Material.STONE).register();
        TagSet.ofBlocks(ResourceKey.of("wyck:second_tag"), Material.DIRT).register();

        assertEquals(Set.of(Blocks.STONE), contents("wyck:first_tag"));
        assertEquals(Set.of(Blocks.DIRT), contents("wyck:second_tag"));
    }

    @Test
    void registeredBlocksReportTheTagThroughTheirHolder() {
        TagSet.ofBlocks(ResourceKey.of("wyck:holder_tag"), Material.GRASS_BLOCK).register();

        assertTrue(blocks().wrapAsHolder(Blocks.GRASS_BLOCK).is(nmsTag("wyck:holder_tag")));
    }

    @Test
    void asTagKeyResolvesToTheRegisteredTag() {
        TagSet<Material> tag = TagSet.ofBlocks(ResourceKey.of("wyck:key_tag"), Material.SAND, Material.GRAVEL);
        tag.register();

        assertEquals(nmsTag("wyck:key_tag"), tag.<TagKey<Block>>asTagKey());
    }

    @Test
    void registeringTheSameKeyTwiceIsRejected() {
        TagSet.ofBlocks(ResourceKey.of("wyck:duplicate_tag"), Material.STONE).register();

        assertThrows(IllegalArgumentException.class,
                () -> TagSet.ofBlocks(ResourceKey.of("wyck:duplicate_tag"), Material.DIRT).register());
    }

    @Test
    void registeringWithoutAResourceKeyIsRejected() {
        assertThrows(IllegalStateException.class, () -> TagSet.ofBlocks(Material.STONE).register());
    }

    @Test
    void registeringATagReferenceIsRejected() {
        TagSet<Material> reference = TagSet.ofBlockTag(ResourceKey.of("minecraft:dirt"));

        assertThrows(IllegalStateException.class, reference::register);
    }
}
