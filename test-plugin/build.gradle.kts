plugins {
    alias(libs.plugins.run.paper)
    alias(libs.plugins.plugin.yml.bukkit)
}

dependencies {
    implementation(project(":main"))
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
        minecraftVersion("1.21.11")
        downloadPlugins {
            modrinth("packetevents", "2.11.1+spigot")
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