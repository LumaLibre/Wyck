import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription
import org.gradle.kotlin.dsl.project

plugins {
    alias(libs.plugins.plugin.yml.paper)
    alias(libs.plugins.paperweight.userdev)
    alias(libs.plugins.modrinth.minotaur)
}

val supported = listOf("26.3")

group = "dev.wyck.paper"

dependencies {
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m3)
    compileOnly(libs.spongepowered.configurate.yaml)
    implementation(libs.faststats.metrics)
    implementation(project(":runtime"))
    implementation(project(":decoders"))
}

tasks {
    shadowJar {
        relocate("dev.faststats", "dev.wyck.metrics")
        exclude("com/google/**")

        minimize {
            exclude(project(":api"))
            exclude(project(":runtime"))
            exclude(project(":decoders"))
            exclude("META-INF/maven/**")
            exclude("META-INF/proguard/**")
        }
        archiveBaseName.set("Wyck")
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
    name = "Wyck"
    main = "dev.wyck.paper.WyckPlugin"
    bootstrapper = "dev.wyck.paper.WyckPluginBootstrap"
    version = project.version.toString()
    authors = listOf("Jsinco", "Outspending")
    apiVersion = "26.3"
    description = "Standalone Wyck plugin for Paper servers."
    website = "https://wyck.dev"
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
    projectId.set("wyck")

    val version = project.version.toString()

    versionNumber.set(version)
    versionType.set(if (version.contains("-")) "beta" else "release")
    uploadFile.set(tasks.shadowJar)
    gameVersions.addAll(supported)
    loaders.addAll("paper", "purpur", "folia")
}


tasks.named("modrinth") {
    dependsOn(":tests:test") // no live for rn
}
