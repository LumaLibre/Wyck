package dev.wyck.wrapper;

import dev.wyck.annotations.AsOf;

@AsOf("3.0.0")
public interface ContextWrapper<C> {
    Object toMinecraft(C context);
}
