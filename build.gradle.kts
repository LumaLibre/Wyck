import java.nio.charset.Charset

plugins {
    java
    `maven-publish`
    alias(libs.plugins.paperweight.userdev)
    alias(libs.plugins.gradleup.shadow)
}

val isSnapshot: Boolean = project.hasProperty("snapshot") || System.getProperty("snapshot")?.toBoolean() == true
val stable = "1.1.0"

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.gradleup.shadow")
    apply(plugin = "io.papermc.paperweight.userdev")

    group = "me.outspending.biomesapi"
    version = if (isSnapshot) "$stable-${gitShortCommitHash()}" else stable


    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.codemc.io/repository/maven-releases/")
    }

    dependencies {
        val libs = rootProject.libs
        paperweight.paperDevBundle(libs.versions.minecraft.v1.m21.r7)
        implementation(libs.google.guava)
        compileOnly(libs.kyori.adventure)
        compileOnly(libs.kyori.minimessage)
    }

    java {
        toolchain.languageVersion = JavaLanguageVersion.of(21)
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

gradle.taskGraph.whenReady {
    if (isSnapshot) {
        println("📦 Building SNAPSHOT version: $version")
    } else {
        println("📦 Building STABLE version: $version")
    }
}

tasks.jar {
    archiveBaseName.set("${project.name}-Parent")
}

fun gitShortCommitHash(): String =
    try {
        ProcessBuilder("git", "rev-parse", "--short", "HEAD")
            .redirectErrorStream(true)
            .start()
            .inputStream
            .bufferedReader(Charset.defaultCharset())
            .readText()
            .trim()
            .ifBlank { "none" }
    } catch (e: Exception) {
        e.printStackTrace()
        "none"
    }
