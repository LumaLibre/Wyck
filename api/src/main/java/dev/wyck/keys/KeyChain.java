package dev.wyck.keys;

import com.google.common.base.Preconditions;
import dev.wyck.annotations.AsOf;
import dev.wyck.util.Lazy;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A collection of items that can be accessed by their respective keys.
 *
 * @param <T> the type of the items in the chain
 * @since 2.4.0
 * @version 2.4.0
 * @author Jsinco
 */
@AsOf("2.4.0")
public final class KeyChain<T extends Keyed> {

    private final Set<T> backing;

    @AsOf("2.4.0")
    private KeyChain(Set<T> backing) {
        this.backing = backing;
    }

    /**
     * Gets the item associated with the given key.
     * @param key the key to get the item for
     * @return the item associated with the given key, or null if not found
     * @throws IllegalArgumentException if the item is not found
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public @Nullable T get(Key key) {
        Preconditions.checkNotNull(key, "key cannot be null");

        return this.backing.stream()
            .filter(obj -> key.equals(obj.key()))
            .findFirst()
            .orElse(null);
    }

    /**
     * Gets the item associated with the given key, or throws an exception if not found.
     * @param key the key to get the item for
     * @return the item associated with the given key
     * @throws IllegalArgumentException if the item is not found
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public T getOrThrow(Key key) {
        T obj = get(key);
        if (obj == null) {
            throw new IllegalArgumentException("Unknown item: " + key);
        }
        return obj;
    }

    /**
     * Gets the item associated with the given key, or returns a lazy that will return null if not found.
     * @param key the key to get the item for
     * @return a lazy that will return the item associated with the given key, or null if not found
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public Lazy<@Nullable T> getLazily(Key key) {
        return Lazy.of(() -> get(key));
    }

    /**
     * Gets the item associated with the given key, or throws an exception if not found.
     * @param key the key to get the item for
     * @return a lazy that will return the item associated with the given key
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public Lazy<T> getOrThrowLazily(Key key) {
        return Lazy.of(() -> getOrThrow(key));
    }

    /**
     * Checks if the given key is registered.
     * @param key the key to check
     * @return true if the key is registered, false otherwise
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public boolean isRegistered(Key key) {
        Preconditions.checkNotNull(key, "key cannot be null");
        return this.backing.stream().anyMatch(obj -> key.equals(obj.key()));
    }

    /**
     * Returns a lazy that will return true if the given key is registered, false otherwise.
     * @param key the key to check
     * @return a lazy that will return true if the given key is registered, false otherwise
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public Lazy<Boolean> isRegisteredLazily(Key key) {
        return Lazy.of(() -> isRegistered(key));
    }

    /**
     * Returns an iterator over the items in the chain.
     * @return an iterator over the items in the chain
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public Iterator<T> iterator() {
        return this.backing.iterator();
    }

    /**
     * Returns the number of items in the chain.
     * @return the number of items in the chain
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public int size() {
        return this.backing.size();
    }

    /**
     * Returns true if the chain is empty.
     * @return true if the chain is empty
     * @since 2.4.0
     */
    @AsOf("2.3.0")
    public boolean isEmpty() {
        return this.backing.isEmpty();
    }

    /**
     * Returns a copy of the items in the chain.
     * @return a copy of the items in the chain
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public Set<T> items() {
        return Set.copyOf(this.backing);
    }

    /**
     * Appends an item to the chain.
     * @param item the item to append
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @ApiStatus.Internal
    public void append(T item) {
        Preconditions.checkNotNull(item, "item cannot be null");
        if (isRegistered(item.key())) {
            throw new IllegalArgumentException("Item is already appended: " + item.key() + " (" + item.getClass().getName() + ")");
        }
        this.backing.add(item);
    }

    /**
     * Replaces an existing key in the chain with a new item.
     * @param key the key of the item to replace
     * @param item the new item to replace the old one with
     * @return true if the item was replaced, false otherwise
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    @ApiStatus.Internal
    public boolean replace(Key key, T item) {
        Preconditions.checkNotNull(key, "key cannot be null");
        Preconditions.checkNotNull(item, "item cannot be null");

        T existing = get(key);
        if (existing == null) {
            this.append(item);
            return false;
        }
        this.backing.remove(existing);
        this.backing.add(item);
        return true;
    }

    /**
     * Creates a new mutable KeyChain.
     * @return a new mutable KeyChain
     * @param <T> the type of the items in the chain
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public static <T extends Keyed> KeyChain<T> mutable() {
        return new KeyChain<>(new LinkedHashSet<>());
    }

    /**
     * Creates a new immutable KeyChain.
     * @param items the items to add to the chain
     * @return a new immutable KeyChain
     * @param <T> the type of the items in the chain
     * @since 2.4.0
     */
    @AsOf("2.4.0")
    public static <T extends Keyed> KeyChain<T> immutable(Set<T> items) {
        return new KeyChain<>(Set.copyOf(items));
    }
}
