import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    alias(libs.plugins.plugin.yml.paper)
    alias(libs.plugins.paperweight.userdev)
    alias(libs.plugins.lombok)
    alias(libs.plugins.modrinth.minotaur)
}

val supported = listOf("26.1.2", "1.21.11", "1.21.10")

group = "me.outspending.biomesapi.paper"

dependencies {
    paperweight.paperDevBundle(libs.versions.minecraft.v1.m21.r11)
    compileOnly(libs.spongepowered.configurate.yaml)
    implementation(libs.faststats.metrics)
    implementation(project(":commons"))
}

tasks {
    shadowJar {
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
    website = "https://biomes.lumas.dev"
    hasOpenClassloader = true
    foliaSupported = true
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP

    serverDependencies {
        register("ProtocolLib") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = false
            joinClasspath = true
        }
        register("PacketEvents") {
            load = PaperPluginDescription.RelativeLoadOrder.BEFORE
            required = false
            joinClasspath = true
        }
    }
}

modrinth {
    token.set(System.getenv("MODRINTH_TOKEN"))
    projectId.set("biomesapi")

    val version = project.version.toString()

    versionNumber.set(version)
    versionType.set(if (version.contains("-")) "beta" else "release")
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll(supported)
    loaders.addAll("paper", "purpur", "folia")
}