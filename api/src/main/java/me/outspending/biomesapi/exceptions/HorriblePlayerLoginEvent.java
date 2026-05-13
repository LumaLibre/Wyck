package me.outspending.biomesapi.exceptions;

import me.outspending.biomesapi.annotations.AsOf;

@AsOf("2.2.0")
public class HorriblePlayerLoginEvent extends Exception {
    public HorriblePlayerLoginEvent(String message) {
        super(message);
    }
}
