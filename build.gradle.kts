plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.0.0-beta9"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
    id("me.champeau.jmh") version "0.7.2"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "com.gradleup.shadow")

    group = "me.outspending.biomesapi"
    version = "0.0.19"

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    repositories {
        mavenCentral()
        maven {
            name = "papermc-repo"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/groups/public/")
        }
        maven {
            name = "codemc-releases"
            url = uri("https://repo.codemc.io/repository/maven-releases/")
        }
    }

}

val nmsVersions = listOf("1.19_R2", "1.19_R3", "1.20_R1", "1.20_R2", "1.20_R3", "1.21_R3", "1.21_R9")
dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")
    implementation("com.google.guava:guava:11.0.2")


    // NMS Implementations
    implementation(project(":NMS:Wrapper"))
    for (version in nmsVersions) {
        implementation(project(path = ":NMS:${version}"))
    }

    // JMH Implementations
    jmh("org.openjdk.jmh:jmh-core:0.9")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:0.9")

}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks.jar {
    archiveBaseName.set("BiomesAPI-Parent")
}