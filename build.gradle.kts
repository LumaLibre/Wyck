import java.nio.charset.Charset

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.gradleup.shadow)
    alias(libs.plugins.paperweight.userdev) apply false
}

val isSnapshot: Boolean = project.hasProperty("snapshot") || System.getProperty("snapshot")?.toBoolean() == true
val stable = "3.0.0"

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "com.gradleup.shadow")

    group = "dev.wyck"
    version = if (isSnapshot) "$stable-${gitShortCommitHash()}" else stable


    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://repo.codemc.io/repository/maven-releases/")
        maven("https://repo.faststats.dev/releases")
    }

    dependencies {
        val libs = rootProject.libs
        compileOnly(libs.google.guava)
        compileOnly(libs.kyori.adventure)
        compileOnly(libs.kyori.minimessage)
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(25))
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
