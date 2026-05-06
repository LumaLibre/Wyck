package me.outspending.biomesapi.paper.configs;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

public class BiomesAPIPluginConfig extends OkaeriConfig {
    @Comment("Whether to enable FastStats metrics collection for this plugin.")
    public boolean metrics = true;

    @Comment({
            "BiomesAPI may force a specific packet handler implementation to be used by all plugins that hook into it.",
            "If a plugin attempts to use another injector, BiomesAPI will silently redirect it to the injector defined below.",
            "",
            "* PROTOCOLLIB: Requires ProtocolLib to be installed alongside BiomesAPI.",
            "* PACKETEVENTS: Requires PacketEvents to be installed alongside BiomesAPI. PacketEvents does not re-sync registries,",
            "* plugins that register custom biomes after the server has started will not have their biomes rendered unless you enable '-Dpacketevents.force-per-user-registries=true'.",
            "* NETTY: Standalone implementation that does not require any external dependencies.",
            "* NONE: No forced injector. Plugins that have chosen a specific injector will have a single instance of their injector used for sending packet-based biomes."
    })
    public ForcedInjector forcedInjector = ForcedInjector.NONE;

}
