package me.outspending.biomesapi.api.factory;

public final class WireProvider<F> {

    private final String name;
    private volatile F factory;

    private WireProvider(String name) {
        this.name = name;
    }

    public static <F> WireProvider<F> create(String name) {
        return new WireProvider<>(name);
    }

    public synchronized void set(F factory) {
        if (this.factory != null) {
            throw new IllegalStateException(name + " factory already registered");
        }
        this.factory = factory;
    }

    public F get() {
        F f = factory;
        if (f == null) {
            throw new IllegalStateException("No " + name + " factory registered. Did the impl module load?");
        }
        return f;
    }

    public boolean isRegistered() {
        return factory != null;
    }
}