package dev.wyck.worldgen.structure.templatesystem;

import dev.wyck.annotations.AsOf;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * Whether blocks placed from a template are waterlogged when they land in water.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public enum LiquidSettings implements WrappedEnumerator<LiquidSettings> {
    /** Blocks keep the waterlogged state they were saved with. */
    IGNORE_WATERLOGGING("IGNORE_WATERLOGGING"),
    /** Waterloggable blocks placed into water become waterlogged. (Vanilla's default.) */
    APPLY_WATERLOGGING("APPLY_WATERLOGGING");

    public static final KeyedEnumTranslator<LiquidSettings> TRANSLATOR = KeyedEnumTranslator.byKey(LiquidSettings::getKey, LiquidSettings.values());

    private final String key;

    @AsOf("3.4.0")
    LiquidSettings(String key) {
        this.key = key;
    }

    @AsOf("3.4.0")
    @Override
    public KeyedEnumTranslator<LiquidSettings> translator() {
        return TRANSLATOR;
    }

    /**
     * The vanilla name for this LiquidSettings
     * @return the vanilla key for this enum value
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    public String getKey() {
        return this.key;
    }
}
