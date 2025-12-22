plugins {
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
    implementation(project(":main"))
}

tasks.shadowJar {
    dependencies {

    }
    archiveClassifier.set("")
}

tasks.jar {
    enabled = false
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.runServer {
    minecraftVersion("1.21.11")
    downloadPlugins {
        url("https://github.com/retrooper/packetevents/releases/download/v2.11.1/packetevents-spigot-2.11.1.jar")
    }
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}