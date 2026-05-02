plugins {
    alias(libs.plugins.run.paper)
    alias(libs.plugins.plugin.yml.bukkit)
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    implementation(project(":commons"))
    paperweight.paperDevBundle(libs.versions.minecraft.v1.m21.r11)
}

tasks {
    shadowJar {
        dependencies {
        }
        archiveClassifier.set("")
    }

    jar {
        enabled = false
    }

    build {
        dependsOn(shadowJar)
    }

    runServer {
        minecraftVersion("26.1.2")
        downloadPlugins {
            modrinth("packetevents", "2.12.1+spigot")
            // ProtocolLib has no direct downloads, maybe figure out how to unzip then add locally?
        }
    }
}

bukkit {
    name = "BiomesAPITest"
    main = "me.outspending.biomesapi.BiomesAPITest"
    version = project.version.toString()
    apiVersion = "1.21"
    softDepend = listOf("ProtocolLib", "PacketEvents")
}