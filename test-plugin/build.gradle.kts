plugins {
    alias(libs.plugins.run.paper)
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
            url("https://github.com/retrooper/packetevents/releases/download/v2.11.1/packetevents-spigot-2.11.1.jar")
        }
    }
}