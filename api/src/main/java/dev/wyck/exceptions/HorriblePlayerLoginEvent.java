package dev.wyck.exceptions;

import dev.wyck.annotations.AsOf;
import dev.wyck.connection.RegistryReconfigurer;

/**
 * Thrown when {@link RegistryReconfigurer} API is used while
 * another plugin on the server is listening to a {@link org.bukkit.event.player.PlayerLoginEvent}.
 *
 * @author Jsinco
 * @version 2.2.0
 * @since 2.2.0
 */
@AsOf("2.2.0")
public class HorriblePlayerLoginEvent extends Exception {
    public HorriblePlayerLoginEvent(String message) {
        super(message);
    }
}
