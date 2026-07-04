package dev.wyck.connection;

import dev.wyck.annotations.WireFactory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

@NullMarked
@WireFactory
@ApiStatus.Internal
public class RegistryReconfigurerFactoryImpl implements RegistryReconfigurer.Factory {
    @Override
    public RegistryReconfigurer create(Plugin provider) {
        return new RegistryReconfigurerImpl(provider);
    }
}
