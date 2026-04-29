package me.outspending.biomesapi.api.factory;

@FunctionalInterface
public interface WireFactory<T, A> {
    T create(A args);
}
