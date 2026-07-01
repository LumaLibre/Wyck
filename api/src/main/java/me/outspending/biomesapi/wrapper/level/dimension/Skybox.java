package me.outspending.biomesapi.wrapper.level.dimension;

import com.mojang.serialization.Codec;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.wrapper.internal.KeyedEnumTranslator;
import me.outspending.biomesapi.wrapper.internal.NmsEnumTranslatable;
import org.jspecify.annotations.NullMarked;

/**
 * Skybox type, as it appears in Minecraft.
 *
 * @version 2.4.0
 * @since 2.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("2.4.0")
public enum Skybox implements NmsEnumTranslatable<Skybox> {
    NONE("none"),
    OVERWORLD("overworld"),
    END("end");

    public static final KeyedEnumTranslator<Skybox> TRANSLATOR = KeyedEnumTranslator.byKey(Skybox::getKey, Skybox.values());
    public static final Codec<Skybox> CODEC = TRANSLATOR.codec();

    private final String key;

    @AsOf("2.4.0")
    Skybox(String key) {
        this.key = key;
    }

    @Override
    @AsOf("2.4.0")
    public String getKey() {
        return key;
    }

    @Override
    @AsOf("2.4.0")
    public KeyedEnumTranslator<Skybox> translator() {
        return TRANSLATOR;
    }
}
