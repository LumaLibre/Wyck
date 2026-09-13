package dev.wyck.worldgen.structure;

import dev.wyck.annotations.AsOf;
import dev.wyck.worldgen.structure.placement.RandomSpreadPlacement;
import dev.wyck.wrapper.KeyedEnumTranslator;
import dev.wyck.wrapper.WrappedEnumerator;
import org.jspecify.annotations.NullMarked;

/**
 * How a {@link RandomSpreadPlacement} distributes its structures inside each placement cell.
 *
 * @since 3.4.0
 * @version 3.4.0
 * @author Jsinco
 */
@NullMarked
@AsOf("3.4.0")
public enum RandomSpreadType implements WrappedEnumerator<RandomSpreadType> {
    LINEAR("LINEAR"),
    TRIANGULAR("TRIANGULAR");

    public static final KeyedEnumTranslator<RandomSpreadType> TRANSLATOR = KeyedEnumTranslator.byKey(RandomSpreadType::getKey, RandomSpreadType.values());

    private final String key;

    @AsOf("3.4.0")
    RandomSpreadType(String key) {
        this.key = key;
    }

    @AsOf("3.4.0")
    @Override
    public KeyedEnumTranslator<RandomSpreadType> translator() {
        return TRANSLATOR;
    }

    /**
     * The vanilla name for this RandomSpreadType
     * @return the vanilla key for this enum value
     * @since 3.4.0
     */
    @AsOf("3.4.0")
    public String getKey() {
        return this.key;
    }
}
