package me.outspending.biomesapi.connection;

import me.outspending.biomesapi.annotations.WireFactory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@WireFactory
public class RegistryReconfigurerFactoryImpl implements RegistryReconfigurer.Factory {
    @Override
    public RegistryReconfigurer create(@NotNull Plugin provider) {
        return new RegistryReconfigurerImpl(provider);
    }
}
