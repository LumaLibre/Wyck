import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    alias(libs.plugins.plugin.yml.paper)
    alias(libs.plugins.paperweight.userdev)
}

group = "me.outspending.biomesapi.paper"

dependencies {
    implementation(project(":commons"))
    implementation(libs.okaeri.configs.yml)
    implementation(libs.faststats.metrics)
    paperweight.paperDevBundle(libs.versions.minecraft.v1.m21.r11)
}

tasks {
    shadowJar {
        relocate("eu.okaeri", "me.outspending.biomesapi.paper.okaeri")
        relocate("dev.faststats", "me.outspending.biomesapi.paper.metrics")
        archiveBaseName.set("BiomesAPI")
        archiveClassifier.set("")
    }

    jar {
        enabled = false
    }

    build {
        dependsOn(shadowJar)
    }
}

paper {
    name = "BiomesAPI"
    main = "me.outspending.biomesapi.paper.BiomesAPIPlugin"
    bootstrapper = "me.outspending.biomesapi.paper.BiomesAPIPluginBootstrap"
    version = project.version.toString()
    authors = listOf("Jsinco", "Outspending")
    apiVersion = "1.21"
    description = "Standalone BiomesAPI plugin for Paper servers."
    website = "https://github.com/LumaLibre/BiomesAPI"
    hasOpenClassloader = true
    foliaSupported = true
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP

    serverDependencies {
        register("ProtocolLib") {
            required = false
        }
        register("PacketEvents") {
            required = false
        }
    }
}