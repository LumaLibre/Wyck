plugins {
    alias(libs.plugins.paperweight.userdev)
}

group = "dev.wyck.tests"

dependencies {
    val libs = rootProject.libs
    implementation(project(":commons")) // should be bundle
    paperweight.paperDevBundle(libs.versions.minecraft.v26.m2)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.google.guava)
    testImplementation(libs.mockito.core)
    testRuntimeOnly(libs.junit.platform.launcher)
}

// cleanup
val testServerDir: File = layout.buildDirectory.dir("test-server").get().asFile

val deleteTestWorlds by tasks.registering(Delete::class) {
    delete(provider {
        testServerDir.listFiles { file -> file.isDirectory && file.name.startsWith("universe-") }
            ?.toList() ?: emptyList<File>()
    })
}

tasks.test {
    useJUnitPlatform()
    forkEvery = 1

    jvmArgs(
        "-XX:+EnableDynamicAgentLoading",
        "--enable-native-access=ALL-UNNAMED",
        "--sun-misc-unsafe-memory-access=allow",
        "-Xshare:off",
    )

    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = false
    }

    workingDir = testServerDir
    doFirst { testServerDir.mkdirs() }

    if (project.hasProperty("acceptEula")) {
        systemProperty("com.mojang.eula.agree", "true")
    }

    finalizedBy(deleteTestWorlds)
}

tasks.jar {
    enabled = false
}
