plugins {
    alias(libs.plugins.run.paper)
    //alias(libs.plugins.plugin.yml.bukkit)
    alias(libs.plugins.paperweight.userdev)
}

dependencies {
    implementation(project(":bundle"))
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
        minecraftVersion("26.2")
//        downloadPlugins {
//            modrinth("gBIw3Gvy", "4.2.2")
//        }
        //args("--safeMode")
        //commandLine.add("--safeMode")
    }
}

//bukkit {
//    name = "BiomesAPITest"
//    main = "me.outspending.biomesapi.BiomesAPITest"
//    version = project.version.toString()
//    apiVersion = "1.21"
//    softDepend = listOf("ProtocolLib", "PacketEvents")
//}