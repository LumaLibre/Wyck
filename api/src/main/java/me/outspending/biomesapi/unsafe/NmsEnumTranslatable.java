package me.outspending.biomesapi.unsafe;

public interface NmsEnumTranslatable<W extends NmsEnumTranslatable<W>> {

    KeyedEnumTranslator<W> translator();

    @SuppressWarnings("unchecked")
    default <N extends Enum<N>> N toNms(Class<N> nmsEnumClass) {
        return translator().toNms((W) this, nmsEnumClass);
    }
}
