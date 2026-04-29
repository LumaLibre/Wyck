package me.outspending.biomesapi.api.factory;

public final class WireFactories {

    private WireFactories() {}

    @FunctionalInterface
    public interface Of0<T> {
        T create();
    }

    @FunctionalInterface
    public interface Of1<T, A> {
        T create(A a);
    }

    @FunctionalInterface
    public interface Of2<T, A, B> {
        T create(A a, B b);
    }

    @FunctionalInterface
    public interface OfII<T> {
        T create(int a, int b);
    } // two ints

    @FunctionalInterface
    public interface OfIII<T> {
        T create(int a, int b, int c);
    } // three ints
}
