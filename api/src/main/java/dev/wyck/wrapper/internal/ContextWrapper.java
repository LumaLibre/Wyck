package dev.wyck.wrapper.internal;

import dev.wyck.annotations.AsOf;

@AsOf("3.0.0")
public interface ContextWrapper<C> {
    Object toMinecraft(C context);
}
