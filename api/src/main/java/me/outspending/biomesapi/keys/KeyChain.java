package me.outspending.biomesapi.keys;

import com.google.common.base.Preconditions;
import me.outspending.biomesapi.annotations.AsOf;
import me.outspending.biomesapi.exceptions.UnknownBiomeException;
import me.outspending.biomesapi.util.Lazy;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class KeyChain<T extends Keyed> {

    private final Set<T> backing;

    public KeyChain(Set<T> backing) {
        this.backing = backing;
    }

    @AsOf("2.4.0")
    public @Nullable T get(Key key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return this.backing.stream()
            .filter(obj -> key.equals(obj.key()))
            .findFirst()
            .orElse(null);
    }

    @AsOf("2.4.0")
    public T getOrThrow(Key key) {
        T obj = get(key);
        if (obj == null) {
            throw new UnknownBiomeException("Unknown biome: " + key);
        }
        return obj;
    }


    @AsOf("2.4.0")
    public Lazy<@Nullable T> getLazily(Key key) {
        return Lazy.of(() -> get(key));
    }

    @AsOf("2.4.0")
    public Lazy<T> getOrThrowLazily(Key key) {
        return Lazy.of(() -> getOrThrow(key));
    }

    @AsOf("2.4.0")
    public boolean isRegistered(Key key) {
        Preconditions.checkNotNull(key, "key cannot be null");
        return this.backing.stream().anyMatch(obj -> key.equals(obj.key()));
    }

    @AsOf("2.4.0")
    public Lazy<Boolean> isRegisteredLazily(Key key) {
        return Lazy.of(() -> isRegistered(key));
    }

    @AsOf("2.4.0")
    public Iterator<T> iterator() {
        return this.backing.iterator();
    }

    @AsOf("2.4.0")
    public int size() {
        return this.backing.size();
    }

    @AsOf("2.3.0")
    public boolean isEmpty() {
        return this.backing.isEmpty();
    }

    @AsOf("2.4.0")
    public Set<T> items() {
        return Set.copyOf(this.backing);
    }

    @ApiStatus.Internal
    public void append(T item) {
        Preconditions.checkNotNull(item, "item cannot be null");
        if (isRegistered(item.key())) {
            throw new IllegalArgumentException("Item is already appended: " + item.key() + " (" + item.getClass().getName() + ")");
        }
        this.backing.add(item);
    }

    @ApiStatus.Internal
    public boolean replace(Key key, T item) {
        Preconditions.checkNotNull(key, "key cannot be null");
        Preconditions.checkNotNull(item, "item cannot be null");

        T existing = get(key);
        if (existing == null) {
            return false;
        }
        this.backing.remove(existing);
        this.backing.add(item);
        return true;
    }

    public static <T extends Keyed> KeyChain<T> mutable() {
        return new KeyChain<>(new LinkedHashSet<>());
    }

    public static <T extends Keyed> KeyChain<T> immutable(Set<T> items) {
        return new KeyChain<>(Set.copyOf(items));
    }
}
